docker build -t gcepedag/mockup-h2 .
docker tag gcepedag/mockup-h2 gcr.io/consummate-atom-423313-s2/mockup-h2
docker push gcr.io/consummate-atom-423313-s2/mockup-h2

gcloud run deploy --image gcr.io/consummate-atom-423313-s2/mockup-h2:latest --allow-unauthenticated --max-instances=1 --min-instances=0 --platform managed