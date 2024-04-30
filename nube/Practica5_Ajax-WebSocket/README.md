1. Crear .env en el home del proyecto
2. Crear base de datos en cockroach

gcloud auth configure-docker ó gcloud auth configure-docker \
us-east1-docker.pkg.dev

docker tag IMAGEN_ID gcr.io/PROJECT_ID/IMAGEN_ID
docker push gcr.io/PROJECT_ID/IMAGEN_ID

gcloud run deploy --image gcr.io/PROJECT_ID/IMAGEN_ID --platform managed --allow-unauthenticated --max-instances=1 --min-instances=1