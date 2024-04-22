package controladores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import encapsulaciones.Comentario;
import encapsulaciones.Producto;
import encapsulaciones.Ventas;
import io.javalin.Javalin;
import org.eclipse.jetty.websocket.api.Session;
import servicios.GestionDbProducto;
import servicios.MainServices;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;
public class WebSocketControlador extends BaseControlador{

    static MainServices mainServices;

    static {
        try {
            mainServices = MainServices.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public WebSocketControlador(Javalin app) throws SQLException, IOException, ClassNotFoundException {
        super(app);
    }

    public void aplicarRutas() {
        app.ws("/usuariosConectados", ws -> {

            ws.onConnect(ctx -> {
                System.out.println("Conexión Nueva Iniciada - "+ctx.getSessionId());
                MainServices.usuariosConectados.add(ctx.session);
                ctx.session.setIdleTimeout(Duration.ofDays(7));
                broadcastCantUsers();
            });

            ws.onClose(ctx -> {
                System.out.println("Conexión Cerrada - "+ctx.getSessionId());
                MainServices.usuariosConectados.remove(ctx.session);
                broadcastCantUsers();
            });
        });

        app.ws("/home/masDetalles/{idProducto}", ws -> {

            ws.onConnect(ctx -> {
                ctx.session.setIdleTimeout(Duration.ofDays(7));
                int id = ctx.pathParamAsClass("idProducto", Integer.class).get();
                MainServices.productosSesion.put(ctx.session, id);
                broadCastComentario(id);
            });

            ws.onClose(ctx -> {
                System.out.println("Conexión Cerrada - "+ctx.getSessionId());
                int id = ctx.pathParamAsClass("idProducto", Integer.class).get();
                MainServices.productosSesion.remove(ctx.session, id);
            });

            ws.onMessage(ctx -> {
                int index = ctx.message().indexOf(":");
                int idComentario = Integer.parseInt(ctx.message().substring(index + 1));
                int idProducto = ctx.pathParamAsClass("idProducto", Integer.class).get();
                Producto p1 = mainServices.getProductoByCodigo(idProducto);
                GestionDbProducto.getInstance().removeComentario(p1, (long)idComentario);
                broadCastComentario(idProducto);
            });

        });

        app.ws("/gestion/dashboard/update", ws -> {
            ws.onConnect(ctx -> {
                System.out.println("Creando conexion con Dashboard...");
                ctx.session.setIdleTimeout(Duration.ofDays(7));
                MainServices.dashboardConectados.add(ctx.session);
            });
            ws.onClose(ctx -> {
                System.out.println("Conexión Cerrada con Dashboard");
                MainServices.dashboardConectados.remove(ctx.session);

            });
//            ws.onMessage(ctx ->{
//                System.out.println("ON MESSAGE VENTAS");
//                broadcastVentas();
//            });
        });
    }

    private void broadCastComentario(int idProducto) throws IOException {
        Producto producto = mainServices.getProductoByCodigo(idProducto);
        List<Session> sesiones = new ArrayList<>();
        String message = null;

        for (Map.Entry<Session, Integer> entry : MainServices.productosSesion.entrySet()) {
            if (entry.getValue().equals(idProducto)){
                sesiones.add(entry.getKey());
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            message = mapper.writeValueAsString(producto.getComentarios());
            System.out.println("ResultingJSONstring = " + message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        for (Session session: sesiones) {
            session.getRemote().sendString(message);
        }
    }

    private void broadcastCantUsers() throws IOException {
        String message = String.format("{\"users\": %d}", MainServices.usuariosConectados.size());
        for (Session session : MainServices.usuariosConectados) {
                session.getRemote().sendString(message);
        }
    }


    public static void broadcastVentas() throws IOException, SQLException, ClassNotFoundException {
        String message = null;
        ObjectMapper mapper = new ObjectMapper();
        List<Producto> prodsVendidos = mainServices.getProdsVendidos();
        ArrayNode prodsVendidosJson = mapper.valueToTree(prodsVendidos);
        ObjectNode jsonObject = mapper.createObjectNode();

        List<Ventas> listaVentas = new ArrayList<>();
        listaVentas = mainServices.getListaVentas();

        jsonObject.put("totalVenta", mainServices.calcTotalVenta(listaVentas));
        jsonObject.put("cantVentas", listaVentas.size());
        jsonObject.put("cantProds", mainServices.calcProdsVendidos(listaVentas));
        jsonObject.put("cantUsuarios", mainServices.getListaUsuario().size());
        System.out.println("JSON PRODS VENDIDOS: "+prodsVendidosJson);
        jsonObject.set("prodsVendidos", prodsVendidosJson);

        try {
            message = mapper.writeValueAsString(jsonObject);
            System.out.println("ResultingJSONstring = " + message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        for (Session session : MainServices.dashboardConectados) {
            session.getRemote().sendString(message);
            System.out.println("SENDING VENTA BroadcastVenta");
        }
    }

}
