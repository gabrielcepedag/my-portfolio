package soap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import encapsulaciones.Url;
import encapsulaciones.Usuario;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.json.JSONObject;
import servicios.MainServices;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@WebService
public class UrlWebServices implements Serializable {
    private MainServices mainServices = MainServices.getInstance();

    @WebMethod
    public String createUrlToUser(Long idUser, String longUrl) throws SQLException, IOException, ClassNotFoundException {
        String jsonString = "";
        ObjectMapper mapper = new ObjectMapper();
        Usuario user = mainServices.getUserById(idUser);

        if (user == null){
            ObjectNode jsonObject = mapper.createObjectNode();
            jsonObject.put("MESSAGE", "USUARIO NO ENCONTRADO");
            jsonString = mapper.writeValueAsString(jsonObject);
            return jsonString;
        }else{
            Url url = new Url(longUrl);
            url.setUsuario(user);
            mainServices.addUrl(url);
            jsonString = mapper.writeValueAsString(url);
            System.out.println("URL CREADA DESDE EL SERVICIO SOAP");
            System.out.println("ResultingJSONallUrl = " + jsonString);
            return jsonString;
        }
    }

    @WebMethod
    public String getListaUrl() {
        List<Url> lista = mainServices.getListaUrl();
        ObjectMapper mapper = new ObjectMapper();
        String listaUrlJson = "";
        try {
            listaUrlJson = mapper.writeValueAsString(lista);
            System.out.println("ResultingJSONallUrl = " + listaUrlJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return listaUrlJson;
    }

    @WebMethod
    public String getListaUrlByUsuario(@WebParam(name = "userId") Long userId){

        List<Url> lista = mainServices.getUrlsByUsuarioConAcceso(userId);
        ObjectMapper mapper = new ObjectMapper();
        String listaUrlJson = "";

        try {
            listaUrlJson = mapper.writeValueAsString(lista);
            System.out.println("ResultingJSONallUrl = " + listaUrlJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return listaUrlJson;
    }

}
