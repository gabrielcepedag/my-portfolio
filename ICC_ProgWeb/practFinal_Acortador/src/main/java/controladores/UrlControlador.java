package controladores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import encapsulaciones.Acceso;
import encapsulaciones.CodigoQR;
import encapsulaciones.Url;
import encapsulaciones.Usuario;
import io.javalin.Javalin;
import main.Main;
import servicios.GestionDbAcceso;
import servicios.GestionDbUrl;
import servicios.GestionDbUsuario;
import servicios.MainServices;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

public class UrlControlador extends BaseControlador{

    MainServices mainServices = MainServices.getInstance();

    public UrlControlador(Javalin app) throws SQLException, IOException, ClassNotFoundException{
        super(app);
    }

    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/menu", () -> {
                get("/", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest")){
                        ctx.redirect("/home");
                    }
                    Usuario loguedUser = mainServices.getLoggedUser();
                    List<Url> listaUrl = mainServices.getUrlsByUsuario(loguedUser.getId());
                    ObjectMapper mapper = new ObjectMapper();
                    String listaUrlJson = "";
                    try {
                        listaUrlJson = mapper.writeValueAsString(listaUrl);
                        System.out.println("ResultingJSONallUrl = " + listaUrlJson);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    Map<String, Object> model = new HashMap<>();
                    model.put("loguedUser", loguedUser);
                    model.put("listaUrl", listaUrl);
                    model.put("listaUrlJson", listaUrlJson);
                    model.put("preview", false);
                    ctx.render("/public/index.html", model);

                });
                post("/", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest")){
                        ctx.redirect("/home");
                    }
                    Usuario loguedUser = mainServices.getLoggedUser();
                    List<Url> listaUrl = mainServices.getUrlsByUsuario(loguedUser.getId());
                    String longUrl = ctx.formParam("longUrl");
                    Url url = new Url(longUrl);
                    String imagen = url.getVistaPrevia();
                    ObjectMapper mapper = new ObjectMapper();
                    String listaUrlJson = "";
                    try {
                        listaUrlJson = mapper.writeValueAsString(listaUrl);
                        System.out.println("ResultingJSONallUrl = " + listaUrlJson);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    Map<String, Object> model = new HashMap<>();
                    model.put("loguedUser", loguedUser);
                    model.put("listaUrl", listaUrl);
                    model.put("listaUrlJson", listaUrlJson);
                    model.put("previewImage", imagen);
                    model.put("longUrl", longUrl);
                    model.put("preview", true);
                    ctx.render("/public/index.html", model);
                });
                get("/404", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    ctx.render("/public/404_page.html", model);
                });
                get("/links", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest")) {
                        ctx.redirect("/home");
                    }
                    Usuario loguedUser = mainServices.getLoggedUser();
                    List<Url> listaUrl = mainServices.getUrlsByUsuario(loguedUser.getId());
                    Boolean modal = false;

                    Map<String, Object> model = new HashMap<>();
                    model.put("loguedUser", loguedUser);
                    model.put("listaUrl", listaUrl);
                    model.put("modal", modal);
                    ctx.render("/public/userLinks.html", model);
                });
                get("/links/getQr/{idUrl}", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest")) {
                        ctx.redirect("/home");
                    }
                    Usuario loguedUser = mainServices.getLoggedUser();
                    List<Url> listaUrl = mainServices.getUrlsByUsuario(loguedUser.getId());
                    long idUrl = ctx.pathParamAsClass("idUrl", Long.class).get();
                    Url url = mainServices.getUrlById(idUrl);
                    if(url == null){
                        ctx.render("/public/404_page.html");
                    }else {
                        if (url.getUsuario().getId() != mainServices.loggedUser.getId()) {
                            System.out.println("Acceso no autorizado...");
                            ctx.redirect("/menu");
                        }
                        CodigoQR qrCode = url.getQr();

                        Map<String, Object> model = new HashMap<>();
                        model.put("loguedUser", loguedUser);
                        model.put("listaUrl", listaUrl);
                        model.put("selectedUrl", url);
                        model.put("qrCode", qrCode);
                        model.put("modal", true);
                        ctx.render("/public/userLinks.html", model);
                    }
                });
                get("/dashboard", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest")) {
                        ctx.redirect("/home");
                    }
                    Usuario loguedUser = mainServices.getLoggedUser();
                    List<Url> listaUrl = mainServices.getUrlsByUsuario(loguedUser.getId());
                    List<Acceso> listaAccesos = mainServices.listaAccesoByUsuario(loguedUser.getId());
                    ObjectMapper mapper = new ObjectMapper();
                    String listaUrlJson = "";
                    String listaAccesosJson = "";
                    try {
                        listaUrlJson = mapper.writeValueAsString(listaUrl);
                        listaAccesosJson = mapper.writeValueAsString(listaAccesos);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
//                        Long totalVisits = GestionDbAcceso.getInstance().getTotalVisits();
//                        Long linksCreated = GestionDbUrl.getInstance().cantUrl();
                    int totalVisits = mainServices.calcTotalVisits(listaUrl);
                    int linksCreated = listaUrl.size();
                    float visitorsAverage = (float) totalVisits / linksCreated;
                    int visitsUser = mainServices.calcTotalVisits(listaUrl);
                    float averageLink = 0;
                    int linksUser = listaUrl.size();
                    if (linksUser > 0){
                        averageLink = (float) visitsUser / linksUser;
                    }

                    Map<String, Object> model = new HashMap<>();
                    model.put("totalVisits", totalVisits);
                    model.put("linksCreated", linksCreated);
                    model.put("visitorsAverage", visitorsAverage);
                    model.put("visitsUser", visitsUser);
                    model.put("averageLink", averageLink);
                    model.put("linksUser", linksUser);
                    model.put("listaUrl", listaUrl);
                    model.put("listaUrlJson", listaUrlJson);
                    model.put("listaAccesosJson", listaAccesosJson);
                    model.put("loguedUser", loguedUser);
                    ctx.render("/public/dashboard.html", model);
                });
                get("/stats/{idUrl}", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest")) {
                        ctx.redirect("/home");
                    }
                    Usuario loguedUser = mainServices.getLoggedUser();
                    long idUrl = ctx.pathParamAsClass("idUrl", Long.class).get();
                    Url url = mainServices.getUrlById(idUrl);

                    if(url == null){
                        ctx.redirect("/menu/404");
                    }else{
                        if (url.getUsuario().getId() != mainServices.loggedUser.getId()) {
                            System.out.println("Acceso no autorizado...");
                            ctx.redirect("/menu");
                        }
                        List<Acceso> listaAccesos = url.getAccesos();
                        Map<String, Object> model = new HashMap<>();
                        ObjectMapper mapper = new ObjectMapper();
                        String listaAccesosJson = "";
                        try {
                            listaAccesosJson = mapper.writeValueAsString(listaAccesos);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        model.put("loguedUser", loguedUser);
                        model.put("url", url);
                        model.put("listaAccesos", listaAccesos);
                        model.put("listaAccesosJson", listaAccesosJson);
                        ctx.render("/public/statistics.html", model);
                    }
                });

                /*PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA */
                //TODO: Modificar implementación endpoint para seguir protocolo REST (DELETE)
                get("/links/delete/{idUrl}", ctx -> {
                    long idUrl = ctx.pathParamAsClass("idUrl", Long.class).get();
                    Url url = mainServices.getUrlById(idUrl);
                    if(url.getUsuario().getId() == mainServices.getLoggedUser().getId() || mainServices.getLoggedUser().isAdmin()) {
                        mainServices.eliminarUrl(url);
                        mainServices.getListaUrl().remove(url);
                        ctx.redirect("/menu/links");
                    }else{
                        ctx.redirect("/menu");
                    }
                });
                /*PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA */
            });

           path("/gestion", () -> {
               get("/url", ctx -> {
                   if (!(mainServices.loggedUser.isAdmin())) {
                       System.out.println("Acceso no autorizado... ");
                       ctx.redirect("/home");
                   }
                   ObjectMapper mapper = new ObjectMapper();
                   String message = null;
                   List<Url> listaUrl = new ArrayList<>();
                   listaUrl = mainServices.getListaUrl();
                   try {
                       message = mapper.writeValueAsString(listaUrl);
                       ctx.result(message);
                   } catch (JsonProcessingException e) {
                       e.printStackTrace();
                   }
               });
               post("/url", ctx -> {
                   String longUrl = ctx.formParam("longUrl");
                   Url url = new Url(longUrl);
                   url.setUsuario(mainServices.getLoggedUser());
                   mainServices.addUrl(url);
                   ctx.redirect("/home");
               });
               delete("/url", ctx -> {
                   long idUrl = ctx.formParamAsClass("idUrl", Long.class).get();
                   Url url = mainServices.getUrlById(idUrl);
                   if (url.getUsuario().getId() == mainServices.loggedUser.getId() || mainServices.loggedUser.isAdmin()) {
                       mainServices.eliminarUrl(url);
                   }
               });

               get("/links", ctx -> {
                   if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest")) {
                       ctx.redirect("/home");
                   }
                   if(!(mainServices.getLoggedUser().isAdmin())) {
                       ctx.redirect("/menu");
                   }

                   Usuario loguedUser = mainServices.getLoggedUser();
                   List<Url> listaUrl = GestionDbUrl.getInstance().findAll();
                   Long totalVisits = GestionDbAcceso.getInstance().getTotalVisits();
                   Long linksCreated = GestionDbUrl.getInstance().cantUrl();
                   float visitorsAverage = (float) totalVisits / linksCreated;

                   Map<String, Object> model = new HashMap<>();
                   model.put("totalVisits", totalVisits);
                   model.put("linksCreated", linksCreated);
                   model.put("visitorsAverage", visitorsAverage);
                   model.put("loguedUser", loguedUser);
                   model.put("listaUrl", listaUrl);
                   ctx.render("/public/adminLinks.html", model);
               });

               /* PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA */
               //TODO: Modificar implementación endpoint para seguir protocolo REST (DELETE)
               get("/links/delete/{idUrl}", ctx -> {
                   long idUrl = ctx.pathParamAsClass("idUrl", Long.class).get();
                   Url url = mainServices.getUrlById(idUrl);
                   if(url.getUsuario().getId() == mainServices.getLoggedUser().getId() || mainServices.getLoggedUser().isAdmin()) {
                       if(url.getUsuario().getUsername().equalsIgnoreCase("guest")){
                           List<Url> listaUrl = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                           listaUrl.removeIf(urlObj -> Objects.equals(urlObj.getId(), url.getId()));
                           ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name(), listaUrl);
                       }
                       mainServices.eliminarUrl(url);
                       mainServices.getListaUrl().remove(url);
                       ctx.redirect("/gestion/links");
                   }else{
                       ctx.redirect("/menu");
                   }
               });
               /*PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA*/

               get("/users", ctx -> {
                   if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest")) {
                       System.out.println("Acceso no autorizado...");
                       ctx.redirect("/home");
                   }
                   if(!(mainServices.getLoggedUser().isSuperAdmin())) {
                       System.out.println("Acceso no autorizado...");
                       ctx.redirect("/menu");
                   }
                   Long cantUsers = GestionDbUsuario.getInstance().contUsuario();
                   Long userActiveLinks = GestionDbUrl.getInstance().cantUserLink();
                   Long cantAdmin = GestionDbUsuario.getInstance().cantAdminUser();
                   Usuario loguedUser = mainServices.getLoggedUser();
                   List<Url> listaUrl = mainServices.getListaUrl();
                   List<Usuario> listaUsuarios = GestionDbUsuario.getInstance().findAll();

                   Map<String, Object> model = new HashMap<>();
                   model.put("cantUsers", (cantUsers-1));
                   model.put("userActiveLinks", userActiveLinks);
                   model.put("cantAdmin", cantAdmin);
                   model.put("loguedUser", loguedUser);
                   model.put("listaUrl", listaUrl);
                   model.put("listaUsuarios", listaUsuarios);
                   model.put("update", false);
                   ctx.render("/public/adminUsers.html", model);
               });
               get("/users/update/{username}", ctx -> {
                   if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest")) {
                       System.out.println("Acceso no autorizado...");
                       ctx.redirect("/home");
                   }
                   if(!(mainServices.getLoggedUser().isSuperAdmin())) {
                       ctx.redirect("/menu");
                   }
                   String usernameUser = ctx.pathParamAsClass("username", String.class).get();
                   Long cantUsers = GestionDbUsuario.getInstance().contUsuario();
                   Long userActiveLinks = GestionDbUrl.getInstance().cantUserLink();
                   Long cantAdmin = GestionDbUsuario.getInstance().cantAdminUser();
                   Usuario loguedUser = mainServices.getLoggedUser();
                   Usuario selectedUser = mainServices.getUserByUsername(usernameUser);

                   List<Url> listaUrl = mainServices.getListaUrl();
                   List<Usuario> listaUsuarios = GestionDbUsuario.getInstance().findAll();

                   Map<String, Object> model = new HashMap<>();
                   model.put("cantUsers", (cantUsers-1));
                   model.put("userActiveLinks", userActiveLinks);
                   model.put("cantAdmin", cantAdmin);
                   model.put("loguedUser", loguedUser);
                   model.put("listaUrl", listaUrl);
                   model.put("listaUsuarios", listaUsuarios);
                   model.put("selectedUser", selectedUser);
                   model.put("update", true);
                   ctx.render("/public/adminUsers.html", model);
               });
           });

           /* URL Base Paths */
//            path("/usuario/{usuarioId}/url", () -> {
            path("/url", () -> {
//                before(ctx -> {
//                    long usuarioId = ctx.pathParamAsClass("usuarioId", Long.class).get();
//                    Usuario user = mainServices.getLoggedUser();
//                    if (usuarioId != user.getId() && !user.isAdmin()){
//                        System.out.println("No tiene permitido. usuarioId: "+usuarioId+" UserSuperAdmin: "+user.isSuperAdmin()+" Logueado: "+user.getId());
//                        ctx.redirect("/home");
//                    }
//                });
                get(ctx -> {
                    ObjectMapper mapper = new ObjectMapper();
                    String message = null;
                    List<Url> usuarioUrl = mainServices.getUrlsByUsuario(mainServices.getLoggedUser().getId());

                    try {
                        message = mapper.writeValueAsString(usuarioUrl);
                        System.out.println("ResultingJSONallUrl = " + message);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
                get("/{idUrl}", ctx -> {
                    long idUrl = ctx.pathParamAsClass("idUrl", Long.class).get();
                    ObjectMapper mapper = new ObjectMapper();
                    String message = "";
                    String listAccesos = "";
                    Url url = mainServices.getUrlById(idUrl);
                    if (url == null){
                        System.out.println("La URL solicitada no existe...");
                        ctx.redirect("/home");
                    }
                    if (!mainServices.loggedUser.isAdmin()) {
                        System.out.println("Acceso no autorizado...");
                        ctx.redirect("/home");
                    }
                    if (url.getUsuario().getId() != mainServices.loggedUser.getId()) {
                        ctx.redirect("/home");
                    }
                    List<Acceso> accesos = url.getAccesos();
                    try {
                        message = mapper.writeValueAsString(url);
                        listAccesos = mapper.writeValueAsString(accesos);
                        System.out.println("ResultingJSONurl = " + message);
                        System.out.println("ResultingJSONaccesos = " + listAccesos);
                        ctx.result(message);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
            });
        });
    }
}
