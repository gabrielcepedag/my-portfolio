package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.UUID;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:8888/api";
    private HttpClient httpClient;
    public ApiClient() {
        this.httpClient = HttpClientBuilder.create().build();
    }

    public String getUrlById(Long id) throws IOException, URISyntaxException {
        URI uri = new URIBuilder(BASE_URL + "/url/" + id).build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }

    public String getUrlsByUsuario(Long id, String username) throws IOException, URISyntaxException {
//        String token = generateJwtToken(username);
         String token = UUID.randomUUID().toString();

        URI uri = new URIBuilder(BASE_URL + "/url/usuario/" + id).build();
        HttpGet request = new HttpGet(uri);

        request.setHeader("Authorization", "Bearer " + token);


        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }

    public String addUrl(Long id, String urlLarga) throws IOException, URISyntaxException {
        URI uri = new URIBuilder(BASE_URL + "/url/usuario/" + id +"?url="+ urlLarga).build();
        HttpPost request = new HttpPost(uri);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(uri);
        StringEntity entity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }

//    public void deleteUrl(Long id) throws IOException, URISyntaxException {
//        URI uri = new URIBuilder(BASE_URL + "/url/" + id).build();
//        HttpDelete request = new HttpDelete(uri);
//        httpClient.execute(request);
//    }

//    public void updateUrl(Long id, Url url) throws IOException, URISyntaxException {
//        URI uri = new URIBuilder(BASE_URL + "/url/" + id).build();
//        HttpPut request = new HttpPut(uri);
//        ObjectMapper mapper = new ObjectMapper();
//        String requestBody = mapper.writeValueAsString(url);
//        StringEntity entity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
//        request.setEntity(entity);
//        httpClient.execute(request);
//    }

    public String generateJwtToken(String username) {
        String secretKey = "your-secret-key";
        long expirationTimeInMs = 60000; // 1 minute
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + expirationTimeInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(expirationTime)
                .claim("role", "user")
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        return token;
    }
}
