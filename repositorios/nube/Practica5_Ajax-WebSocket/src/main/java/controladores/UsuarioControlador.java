package controladores;

import encapsulaciones.CarritoCompra;
import encapsulaciones.Usuario;
import io.javalin.Javalin;
import org.example.Main;
import servicios.MainServices;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static io.javalin.apibuilder.ApiBuilder.*;

public class UsuarioControlador extends BaseControlador {
    MainServices mainServices = MainServices.getInstance();
    public UsuarioControlador(Javalin app) throws SQLException, IOException, ClassNotFoundException {
        super(app);
    }
    public void aplicarRutas(){
        app.routes(() -> {
            path("/usuarios", () -> {
                get("/", ctx -> {
                    CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                    Map<String, Object> model = new HashMap<>();
                    model.put("usuarios", mainServices.getListaUsuario());
                    model.put("cantCarrito", carroCompra.getListaProducto().size());
                    model.put("logguedUser", mainServices.getLoggedUser().getUsername());
                    ctx.render("public/usuarios.html", model);
                });
            });
            path("/register-user", () -> {
                post("/", ctx -> {
                    String username = ctx.formParam("username");
                    String nombre = ctx.formParam("nombre");
                    String email = ctx.formParam("email");
                    String password = ctx.formParam("password");
                    boolean valid = true;
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("admin")) {
                        if (mainServices.getUserByUsername(username) == null) {
                            Usuario usuario = new Usuario(username, nombre, email, password, false);
                            mainServices.addUsuario(usuario);
                            ctx.redirect("/gestion?offset=0#users");
                        }else {
                            valid = false;
                            CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                            Map<String, Object> model = new HashMap<>();
                            model.put("cantCarrito", carroCompra.getListaProducto().size());
                            model.put("loggedUser", mainServices.getLoggedUser());
                            model.put("valid", valid);
                            ctx.render("/public/regUsuario.html", model);
                        }
                    }else{
                        ctx.redirect("/usuarios");
                    }
                });
            });
        });
    }
}