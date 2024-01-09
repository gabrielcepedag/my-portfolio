package grpc;

import com.sun.net.httpserver.HttpContext;
import controladores.BaseControlador;
import io.javalin.Javalin;
import org.eclipse.jetty.http.spi.HttpSpiContextHandler;
import org.eclipse.jetty.http.spi.JettyHttpContext;
import org.eclipse.jetty.http.spi.JettyHttpServer;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.lang.reflect.Method;


public class GrpcControlador extends BaseControlador {

    public GrpcControlador(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        Server grpcServer = ServerBuilder.forPort(50051)
                .addService(new UrlGrpcService())
                .build();
    try {
        grpcServer.start();
        System.out.println("Servidor gRPC iniciando y escuchando en " + 50051);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Cerrando servidor por la JVM ");
            if (grpcServer != null) {
                grpcServer.shutdown();
            }
            System.err.println("Servidor abajo!...");
        }));
        grpcServer.awaitTermination();
    }catch (IOException e){

    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }

    }
}

