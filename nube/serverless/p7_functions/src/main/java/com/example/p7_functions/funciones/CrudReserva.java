package com.example.p7_functions.funciones;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.example.p7_functions.services.ReservaService;
import com.example.p7_functions.encapsulaciones.Reserva;
import com.example.p7_functions.utils.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

 /*
         * En caso que estemos utilizando el laboratorio de AWS Academy es necesario incluir el permiso AWSLambdaBasicExecutionRole y de escritura
         * AmazonDynamoDBFullAccess .
 */
public class CrudReserva implements RequestStreamHandler {
    private ReservaService funcionesReserva = new ReservaService();
    private Gson gson = new Gson();

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        JSONParser parser = new JSONParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String cuerpoRecibido = null;
        JSONObject responseJson = new JSONObject();
        String salida = "";
        Reserva reserva = null;
        ApiResponse response = null;

        try {
            //Parseando el objeto.
            JSONObject evento = (JSONObject) parser.parse(reader);

            //Ver la salida por la consola sobre la trama enviada por el APIGateway
            context.getLogger().log("LOG MINE: "+evento.toJSONString());

            //Recuperando el metodo de acceso de la llamada del API.
            if(evento.get("requestContext")==null){
                throw new IllegalArgumentException("No respeta el API de entrada");
            }
            //String metodoHttp = ((JSONObject)((JSONObject)evento.get("httpMethod")).get("http")).get("method").toString();
            String metodoHttp = evento.get("httpMethod").toString();

            //Realizando la operacion
            switch (metodoHttp){
                case "GET":
                    String filtro = "";
                    if (evento.get("queryStringParameters") != null){
                        JSONObject queryParameters = (JSONObject) evento.get("queryStringParameters");
                        if (queryParameters.get("filtro") != null){
                            filtro = queryParameters.get("filtro").toString();
                        }
                    }

                    response = funcionesReserva.listarReservas(filtro, context);
                    context.getLogger().log("Entro al GET CON FILTRO: "+filtro);
                    salida = gson.toJson(response);
                    context.getLogger().log(salida);
                    break;
                case "POST":
                    reserva = getReservaBodyJson(evento);
                    context.getLogger().log("Entro al POST: "+gson.toJson(reserva));
                    response = funcionesReserva.insertarReservaTabla(reserva, context);
                    salida = gson.toJson(response);
                    context.getLogger().log(salida);
                    break;
                case "PUT":
                    reserva = getReservaBodyJson(evento);
                    context.getLogger().log("Entro al UPDATE: "+gson.toJson(reserva));
                    response = funcionesReserva.actualizarReserva(reserva, context);
                    salida = gson.toJson(response);
                    context.getLogger().log(salida);
                    break;
                case "DELETE":
                    reserva = getReservaBodyJson(evento);
                    response = funcionesReserva.eliminarReserva(reserva, context);
                    context.getLogger().log("Entro al DELETE");
                    salida = gson.toJson(response);
                    break;
            }

            if(evento.get("body")!=null){
                cuerpoRecibido = evento.get("body").toString();
            }

            //Respuesta en el formato esperado:
            JSONObject responseBody = new JSONObject();
            responseBody.put("data", JsonParser.parseString(salida));
            responseBody.put("entrada", cuerpoRecibido);

            JSONObject headerJson = new JSONObject();
            headerJson.put("Content-Type", "application/json");

            responseJson.put("statusCode", response.getCode());
            responseJson.put("headers", headerJson);
            responseJson.put("body", responseBody.toString());

        }catch (Exception ex){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            context.getLogger().log("EXCEPTIONNN en handleRequest!!! "+sw);
            responseJson.put("statusCode", 200);
            responseJson.put("exception", ex.getMessage());
        }

        //Salida
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toString());
        context.getLogger().log("COMPLETOOO!!!");
        writer.close();
    }

    private Reserva getReservaBodyJson(JSONObject json) throws IllegalArgumentException{
        if(json.get("body")==null){
            throw new IllegalArgumentException("No envio el cuerpo en la trama.");
        }
        Reserva reserva = gson.fromJson(json.get("body").toString(), Reserva.class);
        return reserva;
    }
}
