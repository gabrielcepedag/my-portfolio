# Construcción
FROM gradle:8.4.0-jdk17 AS build
# Se debe de correr el docker build desde la carpeta padre para que en el contexto encuentre el archivo .env
#COPY --chown=gradle:gradle ./practFinal_Acortador/ /home/gradle/src
#COPY --chown=gradle:gradle ../.env /home/gradle/src/.env
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

# Ejecución
FROM eclipse-temurin:17-jre-alpine

RUN mkdir /app

EXPOSE ${SHORTERURL_PORT}

COPY --from=build /home/gradle/src/build/libs/*.jar /app/shorter-url.jar
#COPY --from=build /home/gradle/src/.env /app/.env

ENTRYPOINT ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-Dserver.port=${SHORTERURL_PORT}", "-jar", "/app/shorter-url.jar"]
