1: Build the shadowJar
2 RUN: java --add-opens java.base/java.lang=ALL-UNNAMED -jar build/libs/practFinal_Acortador-1.0-SNAPSHOT-all.jar
3: docker build -t shortener -f practFinal_Acortador/Dockerfile .

gcloud run deploy --image=gcepedag/shorter-url --allow-unauthenticated --max-instances=1 --min-instances=0 --set-env-vars "SHORTERURL_PORT=8080"
gcloud run services update shorter-url --update-env-vars HOST=shorter-url-7hxeb24pta-ue.a.run.app/