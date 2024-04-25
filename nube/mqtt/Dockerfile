# Construcción
FROM gradle:8.4.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Ejecución
FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app

EXPOSE ${MQTT_PORT}
WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/mqtt.jar

ENTRYPOINT ["java", "-Dserver.port=${MQTT_PORT}", "-jar", "/app/mqtt.jar"]