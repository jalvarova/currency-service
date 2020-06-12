# Currency Exchange

Api que aplica el tipo de intercambio 

## Run Docker images

###Login Docker Hub
docker login 

###Build
docker build -t currency-exchange:<version> .

###Images

docker images

###Run
docker run -d --name currency-exchange -p 9080:9080 currency-exchange:<version> --network host
###Container
docker ps

###Tag

docker tag currency-exchange:<version> <username>/currency-exchange:<version>

###Push

docker push <username>/currency-exchange:<version>


##Postman Collection

esta en el root del proyecto con el nombre: CURRENCY_EXCHANGE.postman_collection.json