package controladores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import encapsulaciones.*;
import io.javalin.Javalin;
import org.example.Main;
import servicios.GestionDbProducto;
import servicios.GestionDbUsuario;
import servicios.MainServices;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class GestionControlador extends BaseControlador{
    MainServices mainServices = MainServices.getInstance();
    public GestionControlador(Javalin app) throws SQLException, IOException, ClassNotFoundException {
        super(app);
    }
    public void aplicarRutas(){
        app.routes(() -> {
            path("/gestion", () ->{
                get("/", ctx -> {
                    int offset = 0, limit = 20, count = 0;
                    count = (int) (Math.ceil(GestionDbProducto.getInstance().countProducto() / (double)limit));
                    offset = ctx.queryParamAsClass("offset", Integer.class).get();
                    CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("admin")){
                        Map<String, Object> model = new HashMap<>();
                        model.put("usuarios", mainServices.getListaUsuario());
                        model.put("productos", mainServices.getListaProducto(limit, offset));
                        model.put("cantCarrito", carroCompra.getListaProducto().size());
                        model.put("ventas", mainServices.getListaVentas());
                        model.put("logguedUser", mainServices.getLoggedUser().getUsername());
                        model.put("totalCount", count);
                        model.put("offset", offset);
                        ctx.render("public/gestion.html", model);
                    }else{
                        ctx.redirect("/home?offset=0");
                    }
                });
                get("/register/product", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("admin")){
                        CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                        Map<String, Object> model = new HashMap<>();
                        model.put("cantCarrito", carroCompra.getListaProducto().size());
                        model.put("loggedUser", mainServices.getLoggedUser());
                        ctx.render("/public/regProducto.html", model);
                    }else{
                        ctx.redirect("/home?offset=0");
                    }
                });
                get("/remove/product/{codigo}", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("admin")){
                        int id = ctx.pathParamAsClass("codigo", Integer.class).get();
                        Producto prodTmp = mainServices.getProductoByCodigo(id);
                         if(prodTmp!=null){
                            GestionDbProducto.getInstance().eliminar(prodTmp.getId());
                            ctx.redirect("/gestion?offset=0#products");
                         }
                    }else{
                        ctx.redirect("/home?offset=0");
                    }
                });
                get("/register/usuario", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("admin")){
                        CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                        Map<String, Object> model = new HashMap<>();
                        model.put("cantCarrito", carroCompra.getListaProducto().size());
                        model.put("loggedUser", mainServices.getLoggedUser());

                        ctx.render("/public/regUsuario.html", model);
                    }else{
                        ctx.redirect("/home?offset=0");
                    }
                });
                get("/remove/usuario/{username}", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("admin")){
                        String user = ctx.pathParam("username");
                        Usuario userTmp = mainServices.getUserByUsername(user);
                        if(userTmp!=null){
                            GestionDbUsuario.getInstance().eliminar(userTmp.getId());
                            ctx.redirect("/gestion?offset=0#users");
                        }
                    }else{
                        ctx.redirect("/home?offset=0");
                    }
                });
                get("/update/product/{codigo}", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("admin")){
                        int id = ctx.pathParamAsClass("codigo", Integer.class).get();
                        CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                        Producto prodTmp = mainServices.getProductoByCodigo(id);
                        List<Foto> fotos = GestionDbProducto.getInstance().findAllFotosByProd(prodTmp);
                        Map<String, Object> model = new HashMap<>();
                        model.put("cantCarrito", carroCompra.getListaProducto().size());
                        model.put("loggedUser", mainServices.getLoggedUser());
                        model.put("producto", prodTmp);
                        model.put("fotos", fotos);
                        ctx.render("/public/updateProducto.html", model);
                    }else{
                        ctx.redirect("/home?offset=0");
                    }
                });
                get("/update/product/visualizar/{id}", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("admin")){
                        Foto foto = GestionDbProducto.getInstance().findFotoByCodigo(ctx.pathParamAsClass("id", Long.class).get());
                        if (foto != null) {
                            CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                            Map<String, Object> model = new HashMap<>();
                            model.put("foto", foto);
                            model.put("cantCarrito", carroCompra.getListaProducto().size());
                            model.put("loggedUser", mainServices.getLoggedUser());
                            ctx.render("public/preview.html", model);
                        }
                    }else{
                        ctx.redirect("/home?offset=0");
                    }
                });
                get("/update/product/{codigo}/eliminar/{id}", ctx -> {
                    int codigo = ctx.pathParamAsClass("codigo", Integer.class).get();
                    long id = ctx.pathParamAsClass("id", Long.class).get();
                    Producto prod = mainServices.getProductoByCodigo(codigo);
                    Foto foto = GestionDbProducto.getInstance().findFotoByCodigo(id);
                    if (foto != null) {
                        GestionDbProducto.getInstance().removeFoto(prod.getId(), id);
                        System.out.println("Se elimino la foto");
                    }
                    ctx.redirect("/gestion/update/product/"+codigo);
                });
                get("/ventas/ver-mas/{codigo}", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("admin")){
                        String codigo = ctx.pathParam("codigo");
                        Ventas ventaTmp = mainServices.getVentaByCodigo(codigo);
                        CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                        Map<String, Object> model = new HashMap<>();
                        model.put("venta", ventaTmp);
                        model.put("cantCarrito", carroCompra.getListaProducto().size());
                        model.put("loggedUser", mainServices.getLoggedUser().getUsername());
                        model.put("producto", ventaTmp.getListaProductosFromDB());
                        model.put("total", ventaTmp.getMonto());
                        ctx.render("/public/detallesVenta.html", model);
                    }else{
                        ctx.redirect("/home?offset=0");
                    }
                });
                get("/dashboard", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    List<Ventas> listaVentas = new ArrayList<>();

                    listaVentas = mainServices.getListaVentas();
                    float ingresosTotales = mainServices.calcTotalVenta(listaVentas);
                    int cantVentas = mainServices.getListaVentas().size();
                    int cantProd = mainServices.calcProdsVendidos(listaVentas);
                    int cantUsers = mainServices.getListaUsuario().size();
                    List<Producto> prodsVendidos = mainServices.getProdsVendidos();
                    ObjectMapper mapper = new ObjectMapper();
                    String prodsVendidosJson = "";
                    try {
                        prodsVendidosJson = mapper.writeValueAsString(prodsVendidos);
                        System.out.println(prodsVendidosJson);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    model.put("logguedUser", mainServices.getLoggedUser().getUsername());
                    model.put("ingresos", ingresosTotales);
                    model.put("cantVentas", cantVentas);
                    model.put("cantProductos", cantProd);
                    model.put("cantUsuarios", cantUsers);
                    model.put("prodsVendidos", prodsVendidosJson);
            /*
                    List<String> nombreProdsVendidos = new ArrayList<>();
                    List<Integer> cantProdsVendidos = new ArrayList<>();
                    for (Producto prod : prodsVendidos){
                        nombreProdsVendidos.add(prod.getNombre());
                        cantProdsVendidos.add(prod.getCantidad());
                    }
                    model.put("nombreProdsVendidos", nombreProdsVendidos);
                    model.put("cantProdsVendidos", cantProdsVendidos);
            */

                    ctx.render("public/dashboard.html", model);
                });

            });
        });
    }
}