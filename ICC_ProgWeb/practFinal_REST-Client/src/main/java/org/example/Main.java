package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import rest.ApiClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        /*  Configuracion inicial de Servidor Javalin   */
        Javalin app = Javalin.create();
        app.cfg.staticFiles.add(staticFileConfig -> {
            staticFileConfig.hostedPath = "/";
            staticFileConfig.directory = "/public";
            staticFileConfig.location = Location.CLASSPATH;
        });
        app.start(8000);

        app.get("/", ctx -> ctx.render("/public/templates/index.html"));

        /*************************************************************************/
        /*  Probando Cliente REST en JAVA */
        Long idUrl = 2L;
        Long idUser = 3L;

        ApiClient client = new ApiClient();
        String getUrl2 = client.getUrlById(idUrl);

        String getUrlsUser = client.getUrlsByUsuario(idUser, "eduardo");

        String clientUrlLarga = "https://www.google.com";
        String newUrl = client.addUrl(idUser, clientUrlLarga);

        System.out.println("URL 2 desde Cliente: "+getUrl2);
        System.out.println("URLs de eemr0001 desde Cliente: "+getUrlsUser);
        System.out.println("Nueva URL desde Cliente: "+newUrl);
        /**************************************************************************/

    }
}