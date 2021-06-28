# Pipeline Jenkins With Deployment Cloud Run  

![arqhi](./img/arq.jpg)


# Run Docker images

### Docker Build and Docker Run Images
```bash
$ docker login
$ docker build --build-arg ARTIFACT_ID,ARTIFACT_VERSION,APPLICATION_PORT . -t currency-exchange:${ARTIFACT_VERSION}
$ docker images
$ docker run -d --name currency-exchange-local -p 8080:8080 currency-exchange:${ARTIFACT_VERSION}
$ docker ps
$ docker tag currency-exchange gcr.io/${PROJECT_ID}/currency-exchange:${ARTIFACT_VERSION}
```
## Build Swagger Local

```yaml
version: '3.7'
services:
  swagger-ui:
    image: swaggerapi/swagger-ui
    container_name: "swagger-ui"
    ports:
      - "8080:8080"
    volumes:
      - ./swagger.yml:/api.yml
    environment:
      SWAGGER_JSON: /api.yml
```

#Docker delete image

```bash
docker rmi -f $(docker images 'api-swagger' -a -q)
docker rmi -f $(docker images 'currency-exchange' -a -q)
```

## Install Package VM

![arqhi](./img/jenkins-cloud-engine.jpg)

```bash
$ sudo apt-get update
$ sudo apt install git
$ sudo apt install curl -y
$ sudo curl -L "https://github.com/docker/compose/releases/download/1.26.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
$ sudo chmod +x /usr/local/bin/docker-compose
$ docker-compose version
$ docker-compose -f jenkins.yml up -d
```

## Jenkins Server
```yaml
version: '3.7'
services:
  jenkins:
    image: javadevelop/jenkins-server
    privileged: true
    user: root
    ports:
      - 80:8080
      - 50000:50000
    container_name: jenkins_server
    volumes:
      - ~/tools/jenkins:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
      - /usr/bin/docker:/usr/local/bin/docker
      - /usr/local/bin/docker-compose:/usr/local/bin/docker-compose
```

### Get password default Jenkins
```bash
docker exec -it jenkins_server sh -c "cat /var/jenkins_home/secrets/initialAdminPassword"
```

![jenkins](./img/jenkins.png)

![jenkins](./img/credentials.png)

![jenkins](./img/parameters.png)

![jenkins](./img/jenkins-pipeline.png)

![jenkins](./img/execution-pipeline.png)

[Jenkins Server](http://jenkins-wala.duckdns.org/)



## Postman Collection

esta en el root del proyecto con el nombre: CURRENCY_EXCHANGE.postman_collection.json
![postman](./img/postman-test.png)

## CLIENT HTTP

### Api Swagger

[Swagger API](https://swagger-currency-exchange-wcyidxth5q-uc.a.run.app)
 
#### LOGIN AUTH

```bash
curl --location --request POST 'https://service-currency-exchange-wcyidxth5q-uc.a.run.app/authentication' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "walavo",
    "password": "12334"
}'
```

#### HEALTH CHECK

```bash
curl --location --request GET 'https://service-currency-exchange-wcyidxth5q-uc.a.run.app/health' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YWxhdm8iLCJleHAiOjE2MjQ4NjU5NjYsImlhdCI6MTYyNDg0Nzk2Nn0.SCeK957PRYHBD90KEz-YuTS8pf0l-8FRcDMDGe7Bh2b-NAjxNObjrdh3qgp2XxtLpIzD2BuLq2H6DqNmTPFKUA'
```


#### METRICS

```bash
curl --location --request GET 'https://service-currency-exchange-wcyidxth5q-uc.a.run.app/metrics' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YWxhdm8iLCJleHAiOjE2MTc5MDQwNjMsImlhdCI6MTYxNzg4NjA2M30.EK9MtPmlYKkNuLlXwF-3ga58ndzAnvcqS8Zhiu7Pmi7QtVBEEvIhoVEtyXfaOrt2RLA-NTy6V-eD4OY0soCvpg'
```


#### APPLY CURRENCY EXCHANGE

```bash
curl --location --request POST 'https://service-currency-exchange-wcyidxth5q-uc.a.run.app/api/v1/currency-exchange/apply' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YWxhdm8iLCJleHAiOjE2MjQ4NjU5NjYsImlhdCI6MTYyNDg0Nzk2Nn0.SCeK957PRYHBD90KEz-YuTS8pf0l-8FRcDMDGe7Bh2b-NAjxNObjrdh3qgp2XxtLpIzD2BuLq2H6DqNmTPFKUA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "currencyOrigin": "USD",
    "currencyDestination": "PEN",
    "amount": 5
}'
```

#### UPDATE CURRENCY EXCHANGE

```bash
curl --location --request PUT 'https://service-currency-exchange-wcyidxth5q-uc.a.run.app/api/v1/currency-exchange' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YWxhdm8iLCJleHAiOjE2MjQ4NjU5NjYsImlhdCI6MTYyNDg0Nzk2Nn0.SCeK957PRYHBD90KEz-YuTS8pf0l-8FRcDMDGe7Bh2b-NAjxNObjrdh3qgp2XxtLpIzD2BuLq2H6DqNmTPFKUA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "currencyOrigin": "USD",
    "currencyDestination": "PEN",
    "amount": "3.50"
}'
```

#### SAVE ALL CURRENCY EXCHANGE

```bash
curl --location --request GET 'https://service-currency-exchange-wcyidxth5q-uc.a.run.app/metrics' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YWxhdm8iLCJleHAiOjE2MjQ4NjU5NjYsImlhdCI6MTYyNDg0Nzk2Nn0.SCeK957PRYHBD90KEz-YuTS8pf0l-8FRcDMDGe7Bh2b-NAjxNObjrdh3qgp2XxtLpIzD2BuLq2H6DqNmTPFKUA'
```

#### GET ALL CURRENCY EXCHANGE

```bash
curl --location --request GET 'https://service-currency-exchange-wcyidxth5q-uc.a.run.app/metrics' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YWxhdm8iLCJleHAiOjE2MjQ4NjU5NjYsImlhdCI6MTYyNDg0Nzk2Nn0.SCeK957PRYHBD90KEz-YuTS8pf0l-8FRcDMDGe7Bh2b-NAjxNObjrdh3qgp2XxtLpIzD2BuLq2H6DqNmTPFKUA'
```