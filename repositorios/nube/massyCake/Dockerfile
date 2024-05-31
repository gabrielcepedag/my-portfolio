# Construcción
FROM gradle:8.4.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Ejecución
FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app

EXPOSE ${CAKESHOP_PORT}

COPY --from=build /home/gradle/src/build/libs/*.jar /app/cakeshop-app.jar

ENTRYPOINT ["java", "-Dorg.gradle.daemon=false",  "-Dserver.port=${CAKESHOP_PORT}", "-jar", "/app/cakeshop-app.jar"]
