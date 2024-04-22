package grpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import encapsulaciones.Url;
import encapsulaciones.Usuario;
import io.grpc.stub.StreamObserver;
import jakarta.ws.rs.NotFoundException;
import servicios.MainServices;
import urlRn.UrlRnGrpc;
import urlRn.UrlRnOuterClass;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UrlGrpcService extends UrlRnGrpc.UrlRnImplBase {

    MainServices mainServices = MainServices.getInstance();

    @Override
    public void crearUrlByUsuario(UrlRnOuterClass.crearUrlRequest request, StreamObserver<UrlRnOuterClass.UrlByUsuarioResponse> responseObserver) {
        Usuario user = mainServices.getUserById(request.getUsuarioID());

        if (user == null) {
            responseObserver.onNext(UrlRnOuterClass.UrlByUsuarioResponse.newBuilder().setCodRespuesta(404).setJsonUrl("USER NOT FOUND").build());
            responseObserver.onCompleted();
        }else{
            ObjectMapper mapper = new ObjectMapper();
            String urlJson = "";
            try {
                Url url = new Url(request.getLongUrl());
                url.setUsuario(user);
                mainServices.addUrl(url);

                urlJson = mapper.writeValueAsString(url);
                System.out.println("URL CREADA DESDE EL SERVICIO GRPC");
                System.out.println("ResultingJSONallUrl = " + urlJson);
            }catch (IOException | SQLException | ClassNotFoundException e){
                e.printStackTrace();
            }
            responseObserver.onNext(UrlRnOuterClass.UrlByUsuarioResponse.newBuilder().setCodRespuesta(200).setJsonUrl(urlJson).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getUrlByUsuario(UrlRnOuterClass.UrlByUsuarioRequest request, StreamObserver<UrlRnOuterClass.UrlByUsuarioResponse> responseObserver) {
        Usuario user = mainServices.getUserById(request.getUsuarioID());

        if (user != null){
            List<Url> lista = mainServices.getUrlsByUsuarioConAcceso(request.getUsuarioID());
            ObjectMapper mapper = new ObjectMapper();
            String listaUrlJson = "";
            try {
                listaUrlJson = mapper.writeValueAsString(lista);
                System.out.println("ResultingJSONallUrl = " + listaUrlJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(UrlRnOuterClass.UrlByUsuarioResponse.newBuilder().setCodRespuesta(200).setJsonUrl(listaUrlJson).build());
            responseObserver.onCompleted();
        }else{
            responseObserver.onNext(UrlRnOuterClass.UrlByUsuarioResponse.newBuilder().setCodRespuesta(404).setJsonUrl("USER NOT FOUND").build());
            responseObserver.onCompleted();
        }
    }
}
