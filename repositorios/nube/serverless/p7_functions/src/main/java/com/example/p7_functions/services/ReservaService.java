package com.example.p7_functions.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.example.p7_functions.encapsulaciones.Reserva;
import com.example.p7_functions.utils.ApiResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservaService {

    private static String tableName = "TABLA_RESERVA";
    private static int maxLimit = 7;

    public ApiResponse insertarReservaTabla(Reserva reserva, Context context){

        PutItemOutcome putItemOutcome;
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDB = new DynamoDB(client);

        try {
            ApiResponse response = validaReserva(reserva);
            if (response != null){
                return response;
            }

            Table table = dynamoDB.getTable(tableName);
            String idReserva = java.util.UUID.randomUUID().toString();
            reserva.setUuid(idReserva);

            Item item = new Item().withPrimaryKey("uuid", reserva.getUuid())
                    .withString("nombre", reserva.getNombre())
                    .withString("correo", reserva.getCorreo())
                    .withString("matricula", reserva.getMatricula())
                    .withString("laboratorio", reserva.getLaboratorio())
                    .withString("horario", reserva.getHorario());

            putItemOutcome = table.putItem(item);

        }catch (Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            context.getLogger().log("EXCEPTIONNN en insertarReservaTabla!!! "+sw);
            return new ApiResponse(true, 200, e.getMessage(), reserva);
        }

        return new ApiResponse(false, 200, "Reserva realizada con éxito!", reserva);
    }

    public ApiResponse listarReservas(String filtro, Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(tableName);
        List<Reserva> reservas = new ArrayList<>();

        try {
            ScanSpec scanSpec = new ScanSpec();

            if (filtro.equalsIgnoreCase("activas")) {

                LocalDateTime now = ZonedDateTime.now(ZoneId.of("UTC-4")).toLocalDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                String formattedNow = now.format(formatter);
                scanSpec.withFilterExpression("#horario > :now")
                        .withNameMap(Map.of("#horario", "horario"))
                        .withValueMap(new ValueMap().withString(":now", formattedNow));
            }

            ItemCollection<ScanOutcome> items = table.scan(scanSpec);

            for (Item item : items) {
                Reserva reserva = new Reserva();
                reserva.setUuid(item.getString("uuid"));
                reserva.setNombre(item.getString("nombre"));
                reserva.setCorreo(item.getString("correo"));
                reserva.setMatricula(item.getString("matricula"));
                reserva.setLaboratorio(item.getString("laboratorio"));
                reserva.setHorario(item.getString("horario"));
                reservas.add(reserva);
            }

            return new ApiResponse(false, 200, "Reservas encontradas satisfactoriamente", reservas);

        } catch (Exception e) {
            context.getLogger().log("EXCEPTIONNN en listarReservas!!! " + e.getMessage());
            return new ApiResponse(true, 200, "Error al listar reservas: " + e.getMessage(), null);
        }
    }

    public ApiResponse eliminarReserva(Reserva reserva, Context context){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable(tableName);

        try {
            DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                    .withPrimaryKey("uuid", reserva.getUuid());

            DeleteItemOutcome outcome = table.deleteItem(deleteItemSpec);
            return new ApiResponse(false, 200, "Reserva eliminada satisfactoriamente!", null);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            context.getLogger().log("EXCEPTIONNN en eliminarReserva!!! "+sw);
            return new ApiResponse(true, 200, "Error al eliminar la reserva: " + e.getMessage(), null);
        }
    }

    public ApiResponse actualizarReserva(Reserva reserva, Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable(tableName);

        try {
            ApiResponse response = validaReserva(reserva);
            if (response != null){
                return response;
            }

            UpdateItemOutcome outcome = table.updateItem("uuid", reserva.getUuid(),
                    new AttributeUpdate("nombre").put(reserva.getNombre()),
                    new AttributeUpdate("correo").put(reserva.getCorreo()),
                    new AttributeUpdate("matricula").put(reserva.getMatricula()),
                    new AttributeUpdate("laboratorio").put(reserva.getLaboratorio()),
                    new AttributeUpdate("horario").put(reserva.getHorario())
            );

            return new ApiResponse(false, 200, "Reserva actualizada satisfactoriamente!", reserva);

        } catch (Exception e) {
            context.getLogger().log("EXCEPTIONNN en actualizarReserva!!! " + e.getMessage());
            return new ApiResponse(true, 200, "Error al actualizar la reserva: " + e.getMessage(), null);
        }
    }

    public int cantEspaciosLab(String laboratorio, String horarioReserva) {
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":hora", new AttributeValue().withS(horarioReserva));
        expressionAttributeValues.put(":lab", new AttributeValue().withS(laboratorio));

        String filterExpression = "horario = :hora AND laboratorio = :lab";

        ScanRequest scanRequest = new ScanRequest()
                .withTableName(tableName)
                .withFilterExpression(filterExpression)
                .withExpressionAttributeValues(expressionAttributeValues);

        ScanResult result = ddb.scan(scanRequest);

        return result.getCount();
    }

    private ApiResponse validaReserva(Reserva reserva) {
        ApiResponse apiResponse;

        if (reserva.getMatricula().isEmpty() || reserva.getNombre().isEmpty() || reserva.getLaboratorio().isEmpty()
                || reserva.getCorreo().isEmpty() || reserva.getHorario() == null){
            return new ApiResponse(true, 200, "Los datos de la reserva no son correctos!", reserva);
        }

        LocalDateTime horarioReserva = LocalDateTime.parse(reserva.getHorario(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        if (horarioReserva.getHour() < 8 || horarioReserva.getHour() >= 22) {
            return new ApiResponse(true, 200, "El horario de la reserva debe estar entre las 8 am y las 10 pm: "+horarioReserva, reserva);
        }

        if (horarioReserva.getMinute() != 0 || horarioReserva.getSecond() != 0) {
            return new ApiResponse(true, 200, "El horario de la reserva debe ser un múltiplo de hora: "+horarioReserva, reserva);
        }

        int espacios = cantEspaciosLab(reserva.getLaboratorio(), reserva.getHorario());

        if (espacios >= maxLimit){
            return new ApiResponse(true, 200, "No hay disponibilidad para el horario: "+horarioReserva, reserva);
        }
        return null;
    }

}
