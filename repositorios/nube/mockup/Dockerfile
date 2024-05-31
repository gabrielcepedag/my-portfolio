# Construcción
FROM gradle:8.4.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

# Ejecución
FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app

EXPOSE 8080

COPY --from=build /home/gradle/src/build/libs/*.jar /app/mockup.jar

ENTRYPOINT ["java", "-Dorg.gradle.daemon=false", "-jar", "/app/mockup.jar"]
