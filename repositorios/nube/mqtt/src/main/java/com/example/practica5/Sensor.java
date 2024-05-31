package com.example.practica5;

import com.example.practica5.config.SensorHandler;
import com.example.practica5.dto.SensorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.socket.TextMessage;

import java.util.Date;
import java.util.Random;

public class Sensor {
    private String sensorId;
    private int temperatura;
    private int humedad;
    private Date fechaCreacion;
    private static int StaticId;
    private Mqtt mqttClient;

    public Sensor(Mqtt mqttClient) {
        Sensor.StaticId++;
        sensorId = "sensor"+Sensor.StaticId;
        temperatura = 0;
        humedad = 0;
        fechaCreacion = new Date();
        this.mqttClient = mqttClient;
    }

    public void enviarMensaje() throws MqttException, JsonProcessingException {
        genera_data();
        ObjectMapper objectMapper = new ObjectMapper();

        SensorDTO sensor = new SensorDTO(temperatura, humedad, sensorId);
        String json = objectMapper.writeValueAsString(sensor);

        mqttClient.publicar("/sensor/"+sensorId+"/", json);
    }
    public void genera_data(){
        this.temperatura = new Random().nextInt(25) + 15;
        this.humedad = new Random().nextInt(100) + 1;
    }
    public int getHumedad() {
        return humedad;
    }

    public void setHumedad(int humedad) {
        this.humedad = humedad;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
