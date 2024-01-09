package servicios;

import encapsulaciones.Acceso;
import encapsulaciones.Url;
import encapsulaciones.Usuario;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import main.Main;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.eclipse.jetty.server.handler.InetAccessSet;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import javax.imageio.ImageIO;

public class MainServices {
    public static List<Session> usuariosConectados = new ArrayList<>();
    private static MainServices instance;
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<Acceso> listaAccesos = new ArrayList<>();
    private List<Url> listaUrl = new ArrayList<>();
    public Usuario loggedUser;
    public static Usuario guest = new Usuario("guest", "guest", "guest", "guest@guest.com", false, false);

    public List<Url> guestUrl = new ArrayList<>();
    public static MainServices getInstance(){
        if (instance == null) {
            instance = new MainServices();
            instance.listaUsuarios = new ArrayList<Usuario>();
            instance.loggedUser = guest;
            instance.addUsuario(guest);
        }
        return instance;
    }

    public List<Url> getGuestUrl() {
        return guestUrl;
    }

    public void setGuestUrl(List<Url> guestUrl) {
        this.guestUrl = guestUrl;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    public Usuario getLoggedUser() {
        return loggedUser;
    }
    public void setLoggedUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void addUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
        GestionDbUsuario.getInstance().crear(usuario);
    }

    public List<Acceso> getListaAccesos() {
        return listaAccesos;
    }

    public void setListaAccesos(List<Acceso> listaAccesos) {
        this.listaAccesos = listaAccesos;
    }

    public List<Url> getListaUrl() {
        listaUrl = GestionDbUrl.getInstance().findAll();
        return listaUrl;
    }

    public void setListaUrl(List<Url> listaUrl) {
        this.listaUrl = listaUrl;
    }

    public Usuario getUserByUsername(String username) {
//        for (Usuario auxUsuario : listaUsuarios) {
//            if (auxUsuario.getUsername().equalsIgnoreCase(username)) {
//                return auxUsuario;
//            }
//        }
//        return null;
        Usuario auxUsuario = GestionDbUsuario.getInstance().findUsuarioByUsername(username);
        return auxUsuario;
    }
    public Usuario getUserById(Long id) {
//        for (Usuario auxUsuario : listaUsuarios) {
//            if (auxUsuario.getUsername().equalsIgnoreCase(username)) {
//                return auxUsuario;
//            }
//        }
//        return null;
        Usuario auxUsuario = GestionDbUsuario.getInstance().findUsuario(id);
        return auxUsuario;
    }

    public Usuario userVerification(String username, String password) {
//        for (Usuario auxUsuario : listaUsuarios) {
//            if (auxUsuario.getUsername().equals(username) && auxUsuario.getPassword().equals(password)) {
//                return auxUsuario;
//            }
//        }
//        return null;
        Usuario u1 = GestionDbUsuario.getInstance().findUsuarioByUsername(username);
        if (u1 == null || !(u1.getPassword().equals(password)))
            return null;
        return u1;
    }

    public boolean eliminarUsuario(Usuario user) {
        if (user.isSuperAdmin()){
            return false;
        }
        listaUsuarios.remove(user);
        GestionDbUsuario.getInstance().eliminar(user.getId());
        return true;
    }

    public void addUrl(Url url) {
        listaUrl.add(url);
        GestionDbUrl.getInstance().crear(url);
    }

    public String generarUrl(String longUrl) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(longUrl.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String acortarUrl(String longUrl) {
        String url = generarUrl(longUrl);
        String shortHash = url.substring(0, 6);
        String shortenedURL = Main.HOST + shortHash;

        return shortenedURL;
    }

    public Url findUrl(String urlCorta) {

        // TODO: Implementarlo con base de datos
        String url = Main.HOST + urlCorta;
        Url longUrl = listaUrl.stream().filter(corta -> corta.getUrlAcortada().equals(url)).findFirst().orElse(null);
        if (longUrl == null)
            return null;

        return longUrl;
    }

    public void addAcceso(Acceso acceso) {
        listaAccesos.add(acceso);
        GestionDbAcceso.getInstance().crear(acceso);
    }

    public List<Url> getUrlsByUsuario(long idUser) {
        List<Url> lista = new ArrayList<>();
        Usuario user = GestionDbUsuario.getInstance().findUsuario(idUser);

        for (Url url: listaUrl) {
            if(url.getUsuario().getId() == user.getId()){
                lista.add(url);
            }
        }
        return lista;
    }

    public List<Url> getUrlsByUsuarioConAcceso(long idUser) {
        List<Url> lista = new ArrayList<>();
        Usuario user = GestionDbUsuario.getInstance().findUsuario(idUser);

        for (Url url: listaUrl) {
            if(url.getUsuario().getId() == user.getId()){
                List<Acceso> accesos = new ArrayList<>();
                accesos = GestionDbAcceso.getInstance().findAllByUrl(url.getId());
                url.setAccesos(accesos);
                lista.add(url);
            }
        }
        return lista;
    }

    public Url getUrlById(long idUrl) {
        Url url = GestionDbUrl.getInstance().findOne(idUrl);
        return url;
    }
    public Url getUrlByIdInt(int idUrl) {
        Url url = GestionDbUrl.getInstance().findOne(idUrl);
        return url;
    }
    public void eliminarUrl(Url url) {
        GestionDbUrl.getInstance().eliminar(url.getId());
    }

    public int calcTotalVisits(List<Url> listaUrl) {
        int suma = 0;

        for(Url url: listaUrl){
            suma += url.getCantAccesos();
        }
        return suma;
    }

    public List<Acceso> listaAccesoByUsuario(long idUsuario){
        List<Acceso> lista = new ArrayList<>();
        lista = GestionDbAcceso.getInstance().findAllByUser(idUsuario);
        return lista;
    }

    public List<Acceso> listaAccesoByUrl(long idUrl){
        List<Acceso> lista = new ArrayList<>();
        lista = GestionDbAcceso.getInstance().findAllByUrl(idUrl);
        return lista;
    }
    public String vistaPrevia(Url url) throws IOException {

        String miMD5 = getMD5SecretKey(url.getUrlLarga());
        String source = "http://api.screenshotlayer.com/api/capture?access_key="+Main.API_KEY+"&url="+url.getUrlLarga()+"&secret_key="+miMD5;

        return source;
    }

    private String getMD5SecretKey(String url) {
        String miUrl = url+Main.SECRET_KEY;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(miUrl.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejo de excepciones en caso de que el algoritmo MD5 no esté disponible
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getUserAgentParsed(String userAgent) {

        JSONObject json = new JSONObject();
        String userAgentWithoutSpaces = userAgent.replaceAll(" ", "%20");

        try {
            // Crear la URL de la petición GET con la clave de API
            String requestUrl = "https://api.apicagent.com/?ua="+userAgentWithoutSpaces;

            // Crear la conexión HTTP
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Obtener la respuesta de la API
            int responseCode = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            json = new JSONObject(response.toString());

//            System.out.println("Respuesta de la API: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
