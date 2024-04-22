package controladores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import encapsulaciones.Acceso;
import encapsulaciones.Url;
import encapsulaciones.Usuario;
import io.javalin.Javalin;
import main.Main;
import org.json.JSONObject;
import servicios.GestionDbAcceso;
import servicios.GestionDbUrl;
import servicios.MainServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HomeControlador extends BaseControlador{

    MainServices mainServices = MainServices.getInstance();
    public HomeControlador(Javalin app) throws SQLException, IOException, ClassNotFoundException{
        super(app);
    }

    public void aplicarRutas() {
        app.routes(()->{
            path("/home", () -> {
                get("/", ctx -> {
                    if(!(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest"))){
                        ctx.redirect("/menu");
                    }
                    Map<String, Object> model = new HashMap<>();
                    ctx.render("/public/home.html", model);
                });
                get("/404", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    ctx.render("/public/404_page.html", model);
                });

                path("/converter", () -> {
                    get("/", ctx -> {
                        if(!(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest"))){
                            ctx.redirect("/menu");
                        }
                        Map<String, Object> model = new HashMap<>();
                        List<Url> listaUrl = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                        model.put("listaUrl", listaUrl);
                        model.put("preview", false);
                        ctx.render("public/converter.html", model);
                    });
                    post("/", ctx -> {
                        if(!(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest"))){
                            ctx.redirect("/menu");
                        }
                        List<Url> listaUrl = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                        String longUrl = ctx.formParam("longUrl");
                        Url url = new Url(longUrl);
                        String imagen = url.getVistaPrevia();

                        Map<String, Object> model = new HashMap<>();
                        model.put("listaUrl", listaUrl);
                        model.put("previewImage", imagen);
                        model.put("longUrl", longUrl);
                        model.put("preview", true);
                        ctx.render("public/converter.html", model);
                    });
                    post("/url", ctx -> {
                        String longUrl = ctx.formParam("longUrl");
                        Url url = new Url(longUrl);
                        if(mainServices.getLoggedUser().getUsername().equals("guest")) {
                            url.setUsuario(MainServices.guest);
                            List<Url> listaUrl = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                            listaUrl.add(url);
                            ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name(), listaUrl);
                        }else{
                            url.setUsuario(mainServices.getLoggedUser());
                        }
                        mainServices.addUrl(url);
                        ctx.redirect("/home/converter");
                    });
                    /*PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA */
                    get("/delete/{idUrl}", ctx -> {
                        long idUrl = ctx.pathParamAsClass("idUrl", Long.class).get();
                        Url url = mainServices.getUrlById(idUrl);
                        if(!(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest"))) {
                            ctx.redirect("/menu");
                        }
                        if(url.getUsuario().getId() != mainServices.getLoggedUser().getId()) {
                            System.out.println("Acceso no autorizado...");
                            ctx.redirect("/home/converter");
                        }
                        List<Url> listaUrl = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                        listaUrl.removeIf(urlObj -> Objects.equals(urlObj.getId(), url.getId()));
                        ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name(), listaUrl);

                        mainServices.eliminarUrl(url);
                        mainServices.getListaUrl().remove(url);

                        ctx.redirect("/home/converter");
                    });
                    /*PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA */

                        get("/stats/{idUrl}", ctx -> {
                        if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest")){
                            Usuario loguedUser = mainServices.getLoggedUser();
                            long idUrl = ctx.pathParamAsClass("idUrl", Long.class).get();
                            Url url = mainServices.getUrlById(idUrl);
                            if(url==null){
                                ctx.render("/public/404_page.html");
                            }else{
//                                if (url.getUsuario().getId() == mainServices.loggedUser.getId() || loguedUser.isAdmin()){
                                if (url.getUsuario().getId() == mainServices.loggedUser.getId()){
                                    List<Acceso> listaAccesos = url.getAccesos();
                                    Map<String, Object> model = new HashMap<>();
                                    ObjectMapper mapper = new ObjectMapper();
                                    String listaAccesosJson = "";
                                    try {
                                        listaAccesosJson = mapper.writeValueAsString(listaAccesos);
                                        System.out.println("LISTA ACCESOS: "+listaAccesosJson);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    model.put("loguedUser", loguedUser);
                                    model.put("url", url);
                                    model.put("listaAccesos", listaAccesos);
                                    model.put("listaAccesosJson", listaAccesosJson);
                                    ctx.render("/public/statistics.html", model);
                                }else{
                                    System.out.println("NO TIENE PERMISO PARA ACCEDER LINKS DE OTRO USUARIO...");
                                    ctx.redirect("/home");
                                }
                            }
                        }else{
                            ctx.redirect("/menu");
                        }
                    });


                    get("/url/{idUrl}", ctx -> { //TODO: CAMBIAR ESTOOOOO
                      if (mainServices.loggedUser.getUsername().equals("guest")){
                          int id = ctx.pathParamAsClass("idUrl", Integer.class).get();
                          Url url = GestionDbUrl.getInstance().findOne(id);
                        if (url != null && url.getUsuario().getUsername().equals("guest")){
                            List<Url> listaUrl = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                            if (!listaUrl.stream().filter(link -> link.getId() == id).findFirst().isEmpty()){
                                  Usuario loguedUser = mainServices.getLoggedUser();
                                  Map<String, Object> model = new HashMap<>();
                                  model.put("loguedUser", loguedUser);
                                  model.put("listaUrl", listaUrl);
                                  ctx.render("/public/userLinks.html", model); //TODO: CAMBIAR ESTO
                              }else{
                                  ctx.redirect("/home/converter");
                              }
                        }else{
                              ctx.redirect("/home/converter");
                        }
                      }
                    });
                });
            });
            get("/{hash}", ctx -> {
                String hash = ctx.pathParam("hash");
                Url url = MainServices.getInstance().findUrl(hash);
                if (url == null){
                    System.out.println("dicha url corta no existe");
                    ctx.redirect("/home");
                }else{
                    JSONObject json = mainServices.getUserAgentParsed(ctx.userAgent());
                    String ip = ctx.req().getRemoteAddr();
                    String plataforma = json.getJSONObject("device").getString("brand") + " " + json.getString("os_family");
                    String navegador = json.getString("browser_family");
                    Acceso acceso = new Acceso(navegador, ip, plataforma);
                    acceso.setUrl(url);
                    url.setCantAccesos(url.getCantAccesos() + 1);
                    GestionDbUrl.getInstance().editar(url);
                    GestionDbAcceso.getInstance().crear(acceso);
                    ctx.redirect(url.getUrlLarga());
                }
            });
        });
    }
}
