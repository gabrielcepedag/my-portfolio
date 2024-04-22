# Construcción
FROM gradle:8.4.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

# Ejecución
FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app

EXPOSE 8000

COPY --from=build /home/gradle/src/build/libs/*.jar /app/shorter-url.jar

ENTRYPOINT ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED" ,"-jar", "/app/shorter-url.jar"]
