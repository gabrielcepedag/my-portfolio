package controladores;

import encapsulaciones.CarritoCompra;
import encapsulaciones.Usuario;
import io.javalin.Javalin;
import io.javalin.http.Cookie;
import org.example.Main;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import servicios.GestionDbUsuario;
import servicios.MainServices;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static io.javalin.apibuilder.ApiBuilder.*;

public class SesionControlador extends BaseControlador {
    MainServices mainServices = MainServices.getInstance();
    public SesionControlador(Javalin app) throws SQLException, IOException, ClassNotFoundException {
        super(app);
    }
    public void aplicarRutas(){
        app.before(ctx -> {
            CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
            if(carroCompra == null){
                carroCompra = new CarritoCompra();
                ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name(), carroCompra);
            }
            String secretKey = "mySecretKey";
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(secretKey);
            String encryptedUsername = ctx.cookie("login");
            String username = encryptor.decrypt(encryptedUsername);
            Usuario auxUsuario = GestionDbUsuario.getInstance().findUsuarioByUsername(username);
            if (auxUsuario != null)
                mainServices.setLoggedUser(auxUsuario);
            else
                mainServices.setLoggedUser(MainServices.guestUser);
        });

        app.routes(() -> {
            path("/login", () -> {
                get("/", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("valid", true);
                    ctx.render("/public/login.html", model);
                });
                post("/", ctx -> {
                    String username = ctx.formParam("username");
                    String password = ctx.formParam("password");
                    String rememberMe = ctx.formParam("recuerdame");
                    Usuario userByName = mainServices.getUserByUsername(username);
                    Usuario userTmp = mainServices.userVerification(username, password);
                    if (userByName != null) {
                        if (userTmp != null) {
                            String secretKey = "mySecretKey"; // Inicializar el encriptamiento con llave.
                            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
                            encryptor.setPassword(secretKey);
                            String encryptedUsername = encryptor.encrypt(username); // Encriptando la información de usuario.
                            Cookie loginCookie = new Cookie("login", encryptedUsername); // Creando la cookie concatenando ambas informaciones encriptadas.
                            loginCookie.setPath("/");
//                            loginCookie.setMaxAge(600); //10 minutos
                            if (rememberMe != null) {
                                loginCookie.setMaxAge(604800); // 1 semana
                            }
                            ctx.cookie(loginCookie);
                            ctx.sessionAttribute(Main.Constantes.LOGIN_COOKIE.name(), loginCookie);

                            ctx.redirect("/home?offset=0");
                        } else {
                            Map<String, Object> model = new HashMap<>();
                            model.put("valid", false);
                            ctx.render("public/login.html", model);
                        }
                    } else {
                        ctx.redirect("/register");
                    }
                });
            });

            path("/register", () -> {
                get("/", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("valid", true);
                    ctx.render("/public/register.html", model);
                });
                post("/", ctx -> {
                    String username = ctx.formParam("username");
                    String nombre = ctx.formParam("nombre");
                    String email = ctx.formParam("email");
                    String password = ctx.formParam("password");

                    if (mainServices.getUserByUsername(username) == null) {
                        Usuario usuario = new Usuario(username, nombre, email, password, false);
                        mainServices.addUsuario(usuario);
                        String secretKey = "mySecretKey"; // Inicializar el encriptamiento con llave.
                        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
                        encryptor.setPassword(secretKey); // Encriptando la información de usuario.
                        String encryptedUsername = encryptor.encrypt(username);
                        Cookie loginCookie = new Cookie("login", encryptedUsername); // Creando la cookie concatenando ambas informaciones encriptadas.
                        loginCookie.setPath("/");
//                        loginCookie.setMaxAge(600); //10 minutos
                        ctx.cookie(loginCookie);
                        ctx.sessionAttribute(Main.Constantes.LOGIN_COOKIE.name(), loginCookie);
                        ctx.redirect("/home?offset=0");
                    } else {
                        Map<String, Object> model = new HashMap<>();
                        model.put("valid", false);
                        ctx.render("/public/register.html", model);
                    }
                });
            });
        });

        app.get("/logout", ctx -> {
            if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("guest")){
                System.out.println("el usuario es guest...");
                ctx.redirect("/home?offset=0");
            } else{
                System.out.println("Invalidando la sesion...");
                ctx.req().getSession().invalidate();
                ctx.removeCookie("login");
                Cookie cookie = new Cookie("login", "");
                cookie.setMaxAge(0);
                ctx.cookie(cookie);
                mainServices.setLoggedUser(MainServices.guestUser);
                ctx.redirect("/home?offset=0");
            }
        });
    }
}
