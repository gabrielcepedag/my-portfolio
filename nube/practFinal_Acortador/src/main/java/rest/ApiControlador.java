package rest;

import controladores.BaseControlador;
import encapsulaciones.Url;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import utilites.JwtUtils;
import encapsulaciones.Usuario;
import io.javalin.Javalin;
import io.javalin.openapi.OpenApi;
import servicios.MainServices;

import static io.javalin.apibuilder.ApiBuilder.*;


@OpenApi(path = "/api")
public class ApiControlador extends BaseControlador {

    private MainServices mainServices = MainServices.getInstance();

    public ApiControlador(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/api", () -> {
                /**
                 * Ejemplo de una API REST, implementando el CRUD
                 * ir a
                 */
                path("/url", () -> {
                    after(ctx -> {
                        ctx.header("Content-Type", "application/json");

                    });

                    get("/", ctx -> {
                        ctx.json(mainServices.getListaUrl());
                    });

                    get("/{id}", ctx -> {
                        ctx.json(mainServices.getUrlById(ctx.pathParamAsClass("id", Long.class).get()));
                    });

                    get("/usuario/{id}", ctx -> {
                        Long id = ctx.pathParamAsClass("id", Long.class).get();
                        checkJwtToken(ctx);
                        if (ctx.status() != HttpStatus.OK) {
                            return;
                        }
                        ctx.json(mainServices.getUrlsByUsuario(id));
                    });

                    post("/usuario/{id}", ctx -> {
                        //parseando la informacion del POJO debe venir en formato json.
                        String urlLarga = ctx.queryParam("url");
                        Url auxUrl = new Url(urlLarga);
                        Long id = ctx.pathParamAsClass("id", Long.class).get();
                        Usuario user = mainServices.getUserById(id);
                        if (user != null){
                            auxUrl.setUsuario(user);
                            mainServices.addUrl(auxUrl);
                            ctx.json(auxUrl);
                        }else{
                            ctx.status(404);
                        }
                    });
                });
            });
        });
    }

    public void checkJwtToken(Context ctx) {
        String authHeader = ctx.header("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ctx.status(401).result("No autorizado");
            return;
        }
        String token = authHeader.substring(7);
        String secretKey = "your-secret-key";
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); // Parse and validate the token
        } catch (JwtException e) {
            ctx.status(401).result("No autorizado");
            return;
        }
    }

}
