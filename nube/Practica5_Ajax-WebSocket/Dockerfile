# Construcción
FROM gradle:8.4.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
# Se debe de correr el docker build desde la carpeta padre para que en el contexto encuentre el archivo .env
COPY --chown=gradle:gradle ./Practica5_Ajax-WebSocket/ /home/gradle/src
COPY --chown=gradle:gradle ../.env /home/gradle/src/.env
#COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

# Ejecución
FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app

EXPOSE ${PRODUCTSHOP_PORT}

COPY --from=build /home/gradle/src/build/libs/*.jar /app/products-shop.jar
COPY --from=build /home/gradle/src/.env /app/.env

ENTRYPOINT ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-Dserver.port=${PRODUCTSHOP_PORT}", "-jar", "/app/products-shop.jar"]
