package com.example.practica5;

import com.example.practica5.config.SensorHandler;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mqtt {
    private static final String MQTT_PUBLISHER_ID = "demo_client";
    private static final String MQTT_SERVER_ADDRESS = "tcp://test.mosquitto.org:1883";
    private static IMqttClient instance;
    private SensorHandler sensorHandler;
    ExecutorService executorService = Executors.newCachedThreadPool();


    public Mqtt(SensorHandler sensorHandler) {
        this.sensorHandler = sensorHandler;
    }

    public static IMqttClient getInstance() {
        try{
            if (instance == null){
                instance = new MqttClient(MQTT_SERVER_ADDRESS, MQTT_PUBLISHER_ID);
            }
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(false); //Para limpiar los mensajes
            options.setConnectionTimeout(10);

            if (!instance.isConnected()){
                instance.connect(options);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public void publicar(final String topic, final String payload) throws MqttException {
        MqttMessage message = new MqttMessage();
        message.setPayload(payload.getBytes());
        message.setRetained(true);
        message.setQos(1); //Para que el servidor confirme la recepcion del mensaje
        Mqtt.getInstance().publish(topic, message);

        instance.disconnect();
    }
    public void suscribir(final String topic) throws MqttException {
        Mqtt.getInstance().subscribeWithResponse(topic, (tpic, msg) -> {
            System.out.println("Message Received: " + msg.getId() + " -> " + new String((msg.getPayload())));

            // Ejecutar el envío de mensajes WebSocket en otro hilo
            executorService.submit(() -> {
                for (WebSocketSession webSocketSession : sensorHandler.getSessions()) {
                    try {
                        TextMessage message = new TextMessage(new String(msg.getPayload()));
                        webSocketSession.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

}
