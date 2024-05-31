package controladores;

import encapsulaciones.*;
import io.javalin.Javalin;
import org.example.Main;
import servicios.GestionDbProducto;
import servicios.MainServices;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import static io.javalin.apibuilder.ApiBuilder.*;

public class ProductoControlador extends BaseControlador{
    MainServices mainServices = MainServices.getInstance();
    public ProductoControlador(Javalin app) throws SQLException, IOException, ClassNotFoundException {
        super(app);
    }
    public void aplicarRutas(){
        app.routes(() -> {
            path("/home", () -> {
                get("/", ctx -> {
                    int offset = 0, limit = 10, count = 0;
                    count = (int) (Math.ceil(GestionDbProducto.getInstance().countProducto() / (double)limit));
                    offset = ctx.queryParamAsClass("offset", Integer.class).get();
                    CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                    Map<String, Object> model = new HashMap<>();
                    model.put("productos", mainServices.getListaProducto(limit, offset));
                    model.put("cantCarrito", carroCompra.getListaProducto().size());
                    model.put("logguedUser", mainServices.getLoggedUser().getUsername());
                    model.put("totalCount", count);
                    model.put("offset", offset);
                    ctx.render("public/index.html", model);
                });
                get("/regProducto/{codigo}", ctx -> {
                    int id = ctx.pathParamAsClass("codigo", Integer.class).get();
                    CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                    Producto prodTmp = mainServices.getProductoByCodigo(id);
                    if (prodTmp != null) {
                        prodTmp.setCantidad(prodTmp.getCantidad()+1);
                        carroCompra.getListaProducto().add(prodTmp);
                        ctx.redirect("/home?offset=0#product");
                    }
                });
                get("/masDetalles/{codigo}", ctx -> {
                    CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                    int id = ctx.pathParamAsClass("codigo", Integer.class).get();
                    Producto prodTmp = mainServices.getProductoByCodigo(id);
                    Map<String, Object> model = new HashMap<>();
                    model.put("producto", prodTmp);
                    model.put("cantCarrito", carroCompra.getListaProducto().size());
                    model.put("logguedUser", mainServices.getLoggedUser().getUsername());
                    if(prodTmp.getComentarios() != null){
                        model.put("comentarios", prodTmp.getComentarios());
                    }
                    ctx.render("public/detallesProducto.html", model);
                });
                post("/masDetalles/{codigo}", ctx -> {
                    int id = ctx.pathParamAsClass("codigo", Integer.class).get();
                    Producto prodTmp = mainServices.getProductoByCodigo(id);
                    String cmt = ctx.formParam("comentario");
                    Comentario comentario = new Comentario(mainServices.getLoggedUser().getUsername(), cmt);
                    comentario.setProducto(prodTmp);
                    mainServices.addComentarioProd(comentario);
                    ctx.redirect("/home/masDetalles/"+id+"#comments");
                });
                get("/masDetalles/{codigo}/rmvComment/{id}", ctx ->{
                    int codigo = ctx.pathParamAsClass("codigo", Integer.class).get();
                    Long id = ctx.pathParamAsClass("id", Long.class).get();
                    Producto producto = mainServices.getProductoByCodigo(codigo);
                    GestionDbProducto.getInstance().removeComentario(producto, id);
                    ctx.redirect("/home/masDetalles/"+codigo+"#comments");
                });
            });

            path("/register-product", () -> {
                post("/", ctx -> {
                    int codigo = ctx.formParamAsClass("codigo", Integer.class).get();
                    String nombre = ctx.formParam("nomProducto");
                    String descripcion = ctx.formParam("descripcion");
                    float precio = ctx.formParamAsClass("precio", Float.class).get();
                    boolean valid = true;
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("admin")) {
                        if (mainServices.getProductoByCodigo(codigo) == null) {
                            List<Foto> fotoList = new ArrayList<>();
                            Producto producto = new Producto(codigo, nombre, precio, descripcion);
                            ctx.uploadedFiles("fotos").forEach(uploadedFile -> {
                                try {
                                    byte[] bytes = uploadedFile.content().readAllBytes();
                                    String encodedString = Base64.getEncoder().encodeToString(bytes);
                                    Foto foto = new Foto(uploadedFile.filename(), uploadedFile.contentType(), encodedString, producto);
                                    fotoList.add(foto);
                                } catch (IOException e) {
                                    System.out.println("IMAGEN NO REGISTRADA...");
                                    e.printStackTrace();
                                }
                            });
                            producto.setFotos(fotoList);
                            mainServices.addProducto(producto);
                            ctx.redirect("/gestion?offset=0#products");
                        }else {
                            valid = false;
                            CarritoCompra carroCompra = ctx.sessionAttribute(Main.Constantes.LOGGED_USER.name());
                            Map<String, Object> model = new HashMap<>();
                            model.put("cantCarrito", carroCompra.getListaProducto().size());
                            model.put("loggedUser", mainServices.getLoggedUser());
                            model.put("valid", valid);
                            ctx.render("public/regProducto.html", model);
                        }
                    }else{
                        ctx.redirect("/home?offset=0#product");
                    }
                });
            });

            path("/update-product", () -> {
                post("/", ctx -> {
                    if(mainServices.getLoggedUser().getUsername().equalsIgnoreCase("admin")){
                        int codigo = ctx.formParamAsClass("codigo", Integer.class).get();
                        String nombre = ctx.formParam("nomProducto");
                        String descripcion = ctx.formParam("descripcion");
                        float precio = ctx.formParamAsClass("precio", Float.class).get();
                        List<Foto> fotoList = new ArrayList<>();
                        Producto prodTmp = new Producto(codigo, nombre, precio, descripcion);
                        ctx.uploadedFiles("fotos").forEach(uploadedFile -> {
                            try {
                                byte[] bytes = uploadedFile.content().readAllBytes();
                                String encodedString = Base64.getEncoder().encodeToString(bytes);
                                Foto foto = new Foto(uploadedFile.filename(), uploadedFile.contentType(), encodedString, prodTmp);
                                if (!foto.getMimeType().equalsIgnoreCase("application/octet-stream"))
                                    fotoList.add(foto);
                            } catch (IOException e) {
                                System.out.println("IMAGEN NO REGISTRADA...");
                                e.printStackTrace();
                            }
                        });
                        if (fotoList.size() > 0)
                            prodTmp.setFotos(fotoList);
                        mainServices.actualizarProducto(prodTmp);
                        ctx.redirect("/gestion?offset=0");
                    }else{
                        ctx.redirect("/home?offset=0#product");
                    }
                });
            });
        });
    }
}
