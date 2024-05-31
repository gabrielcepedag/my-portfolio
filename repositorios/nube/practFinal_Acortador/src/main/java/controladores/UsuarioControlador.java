package controladores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import encapsulaciones.Url;
import encapsulaciones.Usuario;
import io.javalin.Javalin;
import org.eclipse.jetty.websocket.api.Session;
import servicios.GestionDbUrl;
import servicios.GestionDbUsuario;
import servicios.MainServices;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.javalin.apibuilder.ApiBuilder.*;

public class UsuarioControlador extends BaseControlador{
    MainServices mainServices = MainServices.getInstance();

    public UsuarioControlador(Javalin app) throws SQLException, IOException, ClassNotFoundException{
        super(app);
    }

    public void aplicarRutas() {
        app.routes(() -> {
            path("/login", () -> {
                get("/", ctx -> {
                    if(!(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest"))) {
                        ctx.redirect("/menu");
                    }
                    Map<String, Object> model = new HashMap<>();
                    model.put("valid", true);
                    model.put("login", true);
                    ctx.render("/public/registration.html", model);
                });
                post("/", ctx -> {
                    String username = ctx.formParam("username");
                    String password = ctx.formParam("password");
                    Usuario userByName = mainServices.getUserByUsername(username);
                    Usuario userTmp = mainServices.userVerification(username, password);
                    if (userByName == null) {
                        ctx.redirect("/login/register");
                    }
                    if (userTmp != null) {
                        mainServices.setLoggedUser(userTmp);
                        ctx.redirect("/menu");
                    }
                    Map<String, Object> model = new HashMap<>();
                    model.put("valid", false);
                    model.put("login", true);
                    ctx.render("public/registration.html", model);
                });
                //Endpoint para visualización de Register Form
                get("/register", ctx -> {
                    if(!(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest"))) {
                        ctx.redirect("/menu");
                    }
                    Map<String, Object> model = new HashMap<>();
                    model.put("valid", true);
                    model.put("login", false);
                    ctx.render("public/registration.html", model);
                });
            });

            // Endpoint para procesamiento de Register Form
            path("/register", () -> {
                post("/", ctx -> {
                    String username = ctx.formParam("username");
                    String nombre = ctx.formParam("name");
                    String email = ctx.formParam("email");
                    String password = ctx.formParam("password");

                    if (mainServices.getUserByUsername(username) == null) {
                        Usuario usuario = new Usuario(username, password, nombre, email, false, false);
                        mainServices.addUsuario(usuario);
                        mainServices.setLoggedUser(usuario);
                        ctx.redirect("/menu");
                    } else {
                        Map<String, Object> model = new HashMap<>();
                        model.put("valid", false);
                        model.put("login", false);
                        ctx.render("/public/registration.html", model);
                    }
                });
                post("/modal", ctx -> {
                    String username = ctx.formParam("username");
                    String nombre = ctx.formParam("name");
                    String email = ctx.formParam("email");
                    String password = ctx.formParam("password");
                    String checked = ctx.formParam("admin");
                    Boolean admin = false;
                    if (checked != null){
                        admin = true;
                    }

                    if (mainServices.getUserByUsername(username) == null) {
                        Usuario usuario = new Usuario(username, password, nombre, email, admin, false);
                        mainServices.addUsuario(usuario);
                        System.out.println("NUEVO USUARIO: "+usuario.getUsername());
                        ctx.redirect("/gestion/users");
                    } else {
                        System.out.println("Error: Ya Existe un usuario con este username.");
                        ctx.redirect("/gestion/users");
                    }
                });
            });

            path("/logout", () -> {
                get("/", ctx -> {
                    mainServices.setLoggedUser(MainServices.guest);
                    ctx.redirect("/home");
                });
            });


            /* PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA*/
            //TODO: Modificar implementación endpoint para seguir protocolo REST (PUT)
            path("/gestion/users/update/{idUsuario}", () -> {
                post(ctx -> {
                    if(mainServices.getLoggedUser().isSuperAdmin()){
                        Long id = ctx.pathParamAsClass("idUsuario", Long.class).get();
                        Usuario usuario = GestionDbUsuario.getInstance().findUsuario(id);
                        String username = ctx.formParam("username");
                        String nombre = ctx.formParam("name");
                        String email = ctx.formParam("email");
                        String checked = ctx.formParam("admin");
                        Boolean admin = false;
                        if (checked != null){
                            admin = true;
                        }
                        if (!usuario.isSuperAdmin()){
                            usuario.setUsername(username);
                            usuario.setNombre(nombre);
                            usuario.setEmail(email);
                            usuario.setAdmin(admin);
                            GestionDbUsuario.getInstance().editar(usuario);
                            ctx.redirect("/gestion/users");
                        }
                    }else {
                        ctx.redirect("menu");
                    }

                });
            });
            /* PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA */

            /* PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA */
            //TODO: Modificar implementación endpoint para seguir protocolo REST (DELETE)
            path("/gestion/users/delete/{idUsuario}", () -> {
                get("/", ctx ->{
                    if(mainServices.getLoggedUser().isSuperAdmin()){
                        Long idUser = ctx.pathParamAsClass("idUsuario", Long.class).get();
                        Usuario user = mainServices.getUserById(idUser);
                        if(user == null){
                            ctx.render("/public/404_page.html");
                        }else{
                            if(user.isSuperAdmin() || user.getUsername().equalsIgnoreCase("guest")){
                                System.out.println("NO TIENE PERMISO PARA ELIMINAR EL USUARIO SELECCIONADO.");
                            }else{
                                mainServices.eliminarUsuario(user);
                            }
                            ctx.redirect("/gestion/users");
                        }
                    }else{
                        ctx.redirect("/menu");
                    }
                });
            });
            /* PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA PRUEBA*/


            path("/usuario", () -> {
                delete(ctx -> {
                    int id = ctx.formParamAsClass("idUsuario", Integer.class).get();
                    Usuario usuario = GestionDbUsuario.getInstance().findUsuario(id);
                    if (!usuario.isSuperAdmin()){
                        GestionDbUsuario.getInstance().eliminar(id);
                    }else{
                        System.out.println("No puedes eliminar un superAdmin");
                    }
                });

                put(ctx -> {
                    int id = ctx.formParamAsClass("idUsuario", Integer.class).get();
                    Usuario usuario = GestionDbUsuario.getInstance().findUsuario(id);
                    if (!usuario.isSuperAdmin()){
                        usuario.setUsername(ctx.formParam("username"));
                        usuario.setNombre(ctx.formParam("nombre"));
                        if (ctx.formParam("password").isEmpty()){
                            usuario.setPassword(usuario.getPassword());
                        }else{
                            usuario.setPassword(ctx.formParam("password"));
                        }
                        usuario.setEmail(ctx.formParam("email"));
                        usuario.setAdmin(ctx.formParamAsClass("admin", Boolean.class).get());
                        GestionDbUsuario.getInstance().editar(usuario);
                    }
                });
            });
        });

        app.ws("/usuariosConectados", ws -> {

            ws.onConnect(ctx -> {
                System.out.println("Conexión Nueva Iniciada - "+ctx.getSessionId());
                MainServices.usuariosConectados.add(ctx.session);
                ctx.session.setIdleTimeout(Duration.ofDays(1));
                broadcastCantUsers();
            });

            ws.onClose(ctx -> {
                System.out.println("Conexión Cerrada - "+ctx.getSessionId());
                MainServices.usuariosConectados.remove(ctx.session);
                broadcastCantUsers();
            });
        });
    }

    private void broadcastCantUsers() throws IOException {
        String message = String.format("{\"users\": %d}", MainServices.usuariosConectados.size());
        System.out.println("JSON: "+message);
        for (Session session : MainServices.usuariosConectados) {
            session.getRemote().sendString(message);
        }
    }

}

