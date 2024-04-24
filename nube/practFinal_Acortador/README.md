1: Build the shadowJar
2 RUN: java --add-opens java.base/java.lang=ALL-UNNAMED -jar build/libs/practFinal_Acortador-1.0-SNAPSHOT-all.jar
3: docker build -t shortener -f practFinal_Acortador/Dockerfile .