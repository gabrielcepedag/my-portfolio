package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controladores.HomeControlador;
import controladores.UrlControlador;
import controladores.UsuarioControlador;
import encapsulaciones.Acceso;
import encapsulaciones.Url;
import encapsulaciones.Usuario;
import grpc.GrpcControlador;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinThymeleaf;
import rest.ApiControlador;
import rest.ApiClient;
import servicios.*;
import soap.SoapControlador;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Main {
    public static String API_KEY = "xHxiJmeD69NVtr8HT9C7MnO9yaYq6onZ";
//    public static String API_KEY = "68bd44cbe9cc43b26ef602afc00a6de4";
    public static String SECRET_KEY = "gabrielgdcg";

    public static String HOST;
    public enum Constantes{
        LOGGED_USER,
        LISTA_URL;
    }
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, URISyntaxException, InterruptedException {
        /*  Configuracion inicial de Servidor Javalin   */
        Javalin app = Javalin.create();
        app.cfg.staticFiles.add(staticFileConfig -> {
            staticFileConfig.hostedPath = "/";
            staticFileConfig.directory = "/public";
            staticFileConfig.location = Location.CLASSPATH;
        });
        BootStrapServices.getInstancia().init();

        Main.HOST = getHostForUrl(app);
        System.out.println("La dirección del server es: " + Main.HOST);

        new ApiControlador(app).aplicarRutas();
        new SoapControlador(app).aplicarRutas();

        Dotenv dotenv = Dotenv.configure().directory("./app").load();
        String port = dotenv.get("SHORTERURL_PORT");
        if (port == null){
            port = "8002";
        }

        app.start(Integer.parseInt(port));

        /*  Denotando rutas especificas  */
//        app.get("/", ctx -> ctx.redirect("/home"));
        app.get("/home.html", ctx -> ctx.redirect("/home"));
        app.get("/registration.html", ctx -> ctx.redirect("/login"));
        app.get("/index.html", ctx -> ctx.redirect("/menu"));
        app.get("/userLinks.html", ctx -> ctx.redirect("/menu/links"));
        app.get("/dashboard.html", ctx -> ctx.redirect("/menu/dashboard"));
//        app.get("/statistics.html", ctx -> ctx.redirect("/menu/stats"));
        app.get("/adminLinks.html", ctx -> ctx.redirect("/gestion/links"));
        app.get("/adminUsers.html", ctx -> ctx.redirect("/gestion/users"));
        app.get("/converter.html", ctx -> ctx.redirect("/home/converter"));

        app.before(ctx -> {
            List<Url> listaUrl = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
            if (listaUrl == null){
                listaUrl = new ArrayList<>();
                ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name(), listaUrl);
                System.out.println("La lista de url en sesion es null");
            }
        });

        /*  Inicialización de servicios y controladores */
        MainServices mainServices = MainServices.getInstance();
        new UsuarioControlador(app).aplicarRutas();
        new UrlControlador(app).aplicarRutas();
        new HomeControlador(app).aplicarRutas();

        JavalinRenderer.register(new JavalinThymeleaf(), ".html");

        /*  Inicializando usuario de administrador  */
        Usuario admin = new Usuario("admin", "admin", "admin", "admin@admin.com", true, true);
        Usuario user = new Usuario("user", "user", "user", "user@mail.com", false, false);
        Usuario gabriel = new Usuario("gdcg", "gabriel", "Gabriel", "gabriel@mail.com", true, false);
        mainServices.addUsuario(admin);
        mainServices.addUsuario(user);
        mainServices.addUsuario(gabriel);

        Url url = new Url("https://github.com/gabrielcepedag");
        Url url2 = new Url("https://github.com/gabrielcepedag/my-portfolio");
        Url url3 = new Url("https://gabrielcepeda.cloudfoliohub.com");
        url.setUsuario(user);
        url2.setUsuario(user);
        url3.setUsuario(gabriel);
        mainServices.addUrl(url);
        mainServices.addUrl(url2);
        mainServices.addUrl(url3);

        Acceso acceso = new Acceso("CHROME", "192.168.0.1", "MacOS");
        acceso.setUrl(url);
        mainServices.addAcceso(acceso);

        System.out.println("cantidad de url: "+ GestionDbUrl.getInstance().cantUrl());

        System.out.println("Usuarios registrados: ");
        for (Usuario u1: mainServices.getListaUsuarios()){
            System.out.println(u1.getNombre()+" - "+u1.getUsername());
        }
        System.out.println("URL: "+url.getUrlAcortada());

        System.out.println("USUARIO LOGUEADO: "+mainServices.getLoggedUser().getUsername());

        /*************************************************************************/
        /*  Probando Cliente REST en JAVA */
//        Long idUrl = 2L;
//        Long idUser = 3L;
//
//        ApiClient client = new ApiClient();
//        String getUrl2 = client.getUrlById(idUrl);
//
//        String getUrlsUser = client.getUrlsByUsuario(idUser, "userTest");
//
//        String clientUrlLarga = "https://www.example.com";
//        String newUrl = client.addUrl(nicolas.getId(), clientUrlLarga);
//
//        System.out.println("URL 2 desde Cliente: "+getUrl2);
//        System.out.println("URLs de eemr0001 desde Cliente: "+getUrlsUser);
//        System.out.println("Nueva URL desde Cliente: "+newUrl);
        /**************************************************************************/

        new GrpcControlador(app).aplicarRutas();

    }

    private static String getHostForUrl(Javalin app) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (((Enumeration<?>) networkInterfaces).hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        System.out.println("IP: "+inetAddress.getHostAddress());
                        if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                            return "http://" + inetAddress.getHostAddress()+ ":" + app.port() +"/";
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}