# Construcción
FROM gradle:8.4.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Ejecución
FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app

EXPOSE ${MOCKUP_PORT}

COPY --from=build /home/gradle/src/build/libs/*.jar /app/mockup-api.jar

ENTRYPOINT ["java", "-Dorg.gradle.daemon=false",  "-Dserver.port=${MOCKUP_PORT}", "-jar", "/app/mockup-api.jar"]
