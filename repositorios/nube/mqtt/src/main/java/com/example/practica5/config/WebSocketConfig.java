package com.example.practica5.config;

import com.example.practica5.Sensor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private SensorHandler sensorHandler;

    public WebSocketConfig(SensorHandler sensorHandler) {
        this.sensorHandler = sensorHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(sensorHandler, "/sensores");
    }
}
