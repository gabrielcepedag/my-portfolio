package controladores;

import encapsulaciones.CarritoCompra;
import encapsulaciones.Producto;
import encapsulaciones.Usuario;
import io.javalin.Javalin;
import org.example.Main;
import servicios.MainServices;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static io.javalin.apibuilder.ApiBuilder.*;

public class CarritoControlador extends BaseControlador {
    MainServices mainServices = MainServices.getInstance();
    public CarritoControlador(Javalin app) throws SQLException, IOException, ClassNotFoundException {
        super(app);
    }
    public void aplicarRutas(){
        app.routes(() -> {
            path("/cart", () ->{
                get("/", ctx -> {
                    CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                    Map<String, Object> model = new HashMap<>();
                    model.put("productosCarrito", carroCompra.getListaProducto());
                    model.put("totalCarrito", carroCompra.getTotalCarrito());
                    ctx.render("public/shopping-cart.html", model);
                });
                get("/remove/{codigo}", ctx -> {
                    int id = ctx.pathParamAsClass("codigo", Integer.class).get();
                    CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                    Producto prodTmp = mainServices.getProductoByCodigo(id);
                    carroCompra.getListaProducto().remove(carroCompra.getListaProducto().stream()
                           .filter(prod -> prod.getCodigo() == prodTmp.getCodigo()).findFirst().get());
                    ctx.redirect("/cart");
                });
                post("/checkout", ctx ->{
                    String nombreCliente = ctx.formParam("nombreCliente");
                    Usuario user = mainServices.getLoggedUser();
                    CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                    if(carroCompra.getListaProducto().size() < 1) {
                        ctx.redirect("/home?offset=0");
                    }else if(user.getUsername().equalsIgnoreCase("guest")){
                        ctx.redirect("/login");
                    }else{
                        ArrayList<Producto> listProdVenta = new ArrayList<>(carroCompra.getListaProducto());
                        mainServices.procesarCheckout(nombreCliente, listProdVenta);
                        carroCompra.getListaProducto().clear();
                        System.out.println("Antes de broadcastVentas");
                        WebSocketControlador.broadcastVentas();
                        System.out.println("Despues de broadcastVentas");
                        ctx.redirect("/home?offset=0");
                    }
                });
            });
        });
    }
}

