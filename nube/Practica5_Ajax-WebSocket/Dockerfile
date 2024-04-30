# Construcción
FROM gradle:8.4.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
# Se debe de correr el docker build desde la carpeta padre para que en el contexto encuentre el archivo .env
#COPY --chown=gradle:gradle ./Practica5_Ajax-WebSocket/ /home/gradle/src
#COPY --chown=gradle:gradle ../.env /home/gradle/src/.env
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

# Ejecución
FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app

# ENV VAR PARA CUANDO SE CREE EN CLOUD RUN
ENV DATABASE_URL=jdbc:postgresql://portfolio-14346.7tt.aws-us-east-1.cockroachlabs.cloud:26257/product-shop
ENV DATABASE_USERNAME=gdcg
ENV DATABASE_PASSWORD=nbRswCuO0Gj-x0bnd7Pr7A
ENV PRODUCTSHOP_PORT=8080

EXPOSE ${PRODUCTSHOP_PORT}

COPY --from=build /home/gradle/src/build/libs/*.jar /app/products-shop.jar
#COPY --from=build /home/gradle/src/.env /app/.env

ENTRYPOINT ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-Dserver.port=${PRODUCTSHOP_PORT}", "-jar", "/app/products-shop.jar"]
