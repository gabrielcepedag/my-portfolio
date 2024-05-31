1. Crear .env en el home del proyecto
2. Crear base de datos en cockroach

gcloud auth configure-docker ó gcloud auth configure-docker \
us-east1-docker.pkg.dev

docker tag IMAGEN_ID gcr.io/PROJECT_ID/IMAGEN_ID
docker push gcr.io/PROJECT_ID/IMAGEN_ID

gcloud run deploy --image=gcepedag/products-shop --allow-unauthenticated --max-instances=1 --min-instances=0 --set-env-vars "PRODUCTSHOP_PORT=8080" --set-env-vars "DATABASE_URL=jdbc:postgresql://portfolio-14346.7tt.aws-us-east-1.cockroachlabs.cloud:26257/product-shop" --set-env-vars DATABASE_USERNAME=gdcg --set-env-vars "DATABASE_PASSWORD=nbRswCuO0Gj-x0bnd7Pr7A"
