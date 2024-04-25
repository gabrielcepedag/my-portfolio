package com.example.practica5;

import com.example.practica5.config.SensorHandler;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class Practica5Application {
    private final SensorHandler sensorHandler;
    @Value("${SENSOR_COUNT}")
    private int sensorCount;
    @Value("${INTERVAL_MS}")
    private int interval;
    @Value("${TOPIC_SUBSCRIBE}")
    private String topic;

    public Practica5Application(SensorHandler sensorHandler) {
        this.sensorHandler = sensorHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(Practica5Application.class, args);
    }

    @Bean
    public CommandLineRunner demo() throws MqttException {

        System.out.println("Voy a crear un total de: "+sensorCount+ " Sensores");
        System.out.println("Voy a refrescar la vista cada: "+interval/1000+ " Segundos");
        System.out.println("Voy a subscribirme al topic: "+topic);

        List<Sensor> sensorList = new ArrayList<>(sensorCount);
        Mqtt mqttClient = new Mqtt(sensorHandler);
        mqttClient.suscribir(topic);

        for (int i = 0; i < sensorCount; i++) {
            Sensor sensor = new Sensor(mqttClient);
            sensorList.add(sensor);
        }

        return args -> {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        for (Sensor s1: sensorList) {
                            s1.enviarMensaje();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, interval);
        };
    }

}
