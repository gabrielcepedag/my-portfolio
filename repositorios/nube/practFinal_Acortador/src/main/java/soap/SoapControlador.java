package soap;

import com.sun.net.httpserver.HttpContext;
import controladores.BaseControlador;
import io.javalin.Javalin;
import jakarta.xml.ws.Endpoint;
import org.eclipse.jetty.http.spi.HttpSpiContextHandler;
import org.eclipse.jetty.http.spi.JettyHttpContext;
import org.eclipse.jetty.http.spi.JettyHttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import soap.UrlWebServices;


import java.lang.reflect.Method;


/**
 * Clase para implementar JAX-WS
 */
public class SoapControlador extends BaseControlador {


    public SoapControlador(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        Server server = app.jettyServer().server();
        ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
        server.setHandler(contextHandlerCollection);

        //Contexto donde estoy agrupando.
        try {
            HttpContext context = build(server, "/webServ");

            //El o los servicios que estoy agrupando en ese contexto
            UrlWebServices wsa = new UrlWebServices();
            Endpoint endpoint = Endpoint.create(wsa);
            endpoint.publish(context);
            // Para acceder al wsdl en http://localhost:8888/webServ/UrlWebServices?wsdl
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param server
     * @param contextString
     * @return
     * @throws Exception
     */
    private HttpContext build(Server server, String contextString) throws Exception {
        JettyHttpServer jettyHttpServer = new JettyHttpServer(server, true);
        JettyHttpContext ctx = (JettyHttpContext) jettyHttpServer.createContext(contextString);
        Method method = JettyHttpContext.class.getDeclaredMethod("getJettyContextHandler");
        method.setAccessible(true);
        HttpSpiContextHandler contextHandler = (HttpSpiContextHandler) method.invoke(ctx);
        contextHandler.start();
        return ctx;
    }
}
