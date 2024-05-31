package org.example;

import controladores.*;
import encapsulaciones.*;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinThymeleaf;
import servicios.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.websocket.api.Session;

public class Main {

    public enum Constantes{
        LOGGED_USER,
        LOGIN_COOKIE;
    }

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        /*  Configuracion inicial de Servidor Javalin   */
//        Dotenv dotenv = Dotenv.configure().directory("./app").load();
        String port = System.getenv().getOrDefault("PRODUCTSHOP_PORT", "8080"); //dotenv.get("PRODUCTSHOP_PORT");

        Javalin app = Javalin.create().start(Integer.parseInt(port));
        System.out.println("VOY A LEVANTAR LA APP EN EL PUERTO: "+port);

        app.cfg.staticFiles.add(staticFileConfig -> {
            staticFileConfig.hostedPath = "/";
            staticFileConfig.directory = "/public";
            staticFileConfig.location = Location.CLASSPATH;
        });
        BootStrapServices.getInstancia().init();
        // Denotando rutas especificas
        app.get("/hello", ctx -> ctx.html("Hello World from Javalin!"));
        app.get("/", ctx -> ctx.redirect("/home?offset=0"));
        app.get("/index.html", ctx -> ctx.redirect("/home?offset=0"));
        app.get("/usuarios.html", ctx -> ctx.redirect("/usuarios"));
        app.get("/gestion.html", ctx -> ctx.redirect("/gestion?offset=0"));

        /*  Inicialización de servicios y controladores */
        MainServices mainServices = MainServices.getInstance();
        new ProductoControlador(app).aplicarRutas();
        new UsuarioControlador(app).aplicarRutas();
        new GestionControlador(app).aplicarRutas();
        new CarritoControlador(app).aplicarRutas();
        new SesionControlador(app).aplicarRutas();
        new WebSocketControlador(app).aplicarRutas();
        JavalinRenderer.register(new JavalinThymeleaf(), ".html");

        /*
         *      SECCION DE ELEMENTOS DE PRUEBA
         *                                       */

        //  Creando Productos de prueba
        Producto prodTest = new Producto(1,"Acer Aspire 5", 26785, "2022 Newest Acer Aspire 5 Slim Laptop, 15.6 Full HD IPS, AMD Ryzen 3 3350U Quad-Core Processor, 12 GB DDR4 RAM, 512 GB SSD, Intel WiFi 6, Backlit KB, Fingerprint Reader, Amazon Alexa, Windows 11");
        Producto prodTest2 = new Producto(2,"HP EliteDesk 800", 8250, "High Performance Business Desktop Computer, Intel Quad Core i5-4590 upto 3.7GHz, 16GB RAM, 1TB HDD, 256GB SSD (boot), DVD, WiFi, Windows 10 Professional (Renewed)");
        Producto prodTest3 = new Producto(3,"AMD Ryzen 7 2700", 12100, "AMD Ryzen 7 2700 Processor with Wraith Spire LED Cooler");
        Producto prodTest4 = new Producto(4,"SeaGate BarraCuda 2TB HDD", 2750, "Internal Hard Drive HDD With 3.5 Inch Sata 6 Gb/s 5400 RPM 256MB Cache For Computer Desktop PC – Frustration Free Packaging");
        Producto prodTest5 = new Producto(5,"Samsung Galaxy S10e", 7200, "Samsung Galaxy S10e, 128GB, Prism Black - Unlocked (Renewed)");
        Producto prodTest6 = new Producto(6,"Razer Kraken Headset", 2500, "Lightweight Aluminum Frame, Retractable Noise Isolating Microphone, For PC, PS4, PS5, Switch, Xbox One, Xbox Series X & S, Mobile, 3.5 mm Audio Jack");
        Producto prodTest7 = new Producto(7,"Adaptador Samsung USB-HDMI", 875, "This USB HDMI adapter can connect a computer via USB interface to an HDTV, monitor, or projector with HDMI interface. It will deliver high-definition audio video sources in computer to your HDTV or monitor.");
        Producto prodTest8 = new Producto(8,"Amazon Fire 7", 3400, "Amazon Fire 7 tablet, 16 GB, 10 hours battery life, light and portable for entertainment at home or on-the-go, (2022 release), Black");
        Producto prodTest9 = new Producto(9,"Playstation DualSense 5", 4100, "A gaming icon in your hands - Enjoy a comfortable, evolved design with an iconic layout and enhanced sticks. Hear higher-fidelity sound effects through the built-in speaker in supported games");
        Producto prodTest10 = new Producto(10,"Playstation Pulse 3D", 5600, "Built for a new generation - Fine-tuned for 3D Audio on PS5 consoles. Enjoy comfortable gaming with refined earpads and headband strap.");
        mainServices.addProducto(prodTest);
        mainServices.addProducto(prodTest2);
        mainServices.addProducto(prodTest3);
        mainServices.addProducto(prodTest4);
        mainServices.addProducto(prodTest5);
        mainServices.addProducto(prodTest6);
        mainServices.addProducto(prodTest7);
        mainServices.addProducto(prodTest8);
        mainServices.addProducto(prodTest9);
        mainServices.addProducto(prodTest10);

        for (int i = 11; i <= 40; i++) {
            Producto newProd = new Producto(i,"Producto "+i,999,"Producto de prueba... Testing product...");
            mainServices.addProducto(newProd);
        }

        /*  Inicializando usuario de administrador  */
        Usuario admin = new Usuario("admin", "admin", "admin@admin.com", "admin", true);
        mainServices.addUsuario(admin);

        //  Creando Usuarios de Prueba
        Usuario userTest = new Usuario("gabriel", "Gabriel Cepeda", "gabriel@gmail.com", "gabriel", false);
        Usuario userTest2 = new Usuario("user", "user", "user@gmail.com", "user", false);
        mainServices.addUsuario(userTest);
        mainServices.addUsuario(userTest2);

        //Proceso para agregar comentarios a un producto.
        Comentario cmtTest = new Comentario(userTest.getUsername(), "Wsto es un comentario via websocket a todos los usuarios.");
        Comentario cmtTest2 = new Comentario(userTest2.getUsername(), "Muy mal producto, 0 estrellas.");
        Comentario cmtTest3 = new Comentario(userTest2.getUsername(), "Muy fragil. Prefiero las Dell.");

        cmtTest.setProducto(prodTest);
        cmtTest2.setProducto(prodTest2);
        cmtTest3.setProducto(prodTest);

        mainServices.addComentarioProd(cmtTest);
        mainServices.addComentarioProd(cmtTest2);
        mainServices.addComentarioProd(cmtTest3);

//        //Informacion predeterminada del sistema
        System.out.println("Default user: ["+mainServices.getLoggedUser().getUsername()+"]");
    }
}
