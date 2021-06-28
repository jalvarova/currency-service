# Currency Service
Api que aplica el tipo de intercambio de moneda


## Jenkins server

```bash
sudo apt-get update
sudo apt install git
sudo apt install curl -y
sudo curl -L "https://github.com/docker/compose/releases/download/1.26.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose  version
sudo vim jenkins.yml
```
## Install jenkins server

```bash
docker-compose -f jenkins.yml up -d
```

```yaml
version: '3.7'
services:
  jenkins:
    image: jenkins/jenkins:lts
    privileged: true
    user: root
    ports:
      - 80:8080
      - 50000:50000
    container_name: jenkins
    volumes:
      - ~/tools/jenkins:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
      - /usr/bin/docker:/usr/local/bin/docker
```
### Get password default jenkins
```bash
docker exec -it jenkins sh -c "cat /var/jenkins_home/secrets/initialAdminPassword"
```

[Jenkins](http://jenkins-wala.duckdns.org/)

# Run Docker images

### Login Docker Hub 
```bash
docker login
```

### Build 
```
docker build --build-arg ARTIFACT_ID,ARTIFACT_VERSION,APPLICATION_PORT . -t currency-exchange
```

### Images
```bash
docker images
```

### Run 
```bash
docker run -d --name currency-exchange-local -p 9080:8080 currency-exchange
```

### Container 
```bash
docker ps
```

### Tag
```bash
docker tag currency-exchange gcr.io/[PROJECT_ID]/currency-exchange:ARTIFACT_VERSION
```

# Push Containet Registry GCP

```bash
gcloud docker --  push gcr.io/[PROJECT_ID]/currency-exchange:ARTIFACT_VERSION
```

# Run Deploy Service API
```bash
gcloud beta run deploy service-currency-exchange
--image gcr.io/[PROJECT_ID]/currency-exchange:ARTIFACT_ID
--args ARTIFACT_ID=currency-exchange,ARTIFACT_VERSION=1.0.0,APPLICATION_PORT=8080 
--platform managed 
--allow-unauthenticated 
--cpu=1 
--memory=512Mi
--region=us-central1
--project=[PROJECT_ID]"
--[SERVICE_ACCOUNT]"
```

## Build Api Swagger

```bash
docker build  . -t api-swagger-v2:2.0.0
docker tag api-swagger-v2:2.0.0 gcr.io/boxwood-valve-310104/api-swagger-v2:2.0.0
gcloud docker --  push gcr.io/boxwood-valve-310104/api-swagger-v2:2.0.0
```

# Run Deploy API Swagger
```bash
gcloud beta run deploy service-api-swagger
--image gcr.io/[PROJECT_ID]/api-swagger-v2:2.0.0
--platform managed 
--allow-unauthenticated 
--cpu=1 
--memory=512Mi
--region=us-central1
--project=[PROJECT_ID]"
--[SERVICE_ACCOUNT]"
```

#Docker delete image
docker rmi -f $(docker images 'currency-exchange' -a -q)

##Postman Collection
esta en el root del proyecto con el nombre: CURRENCY_EXCHANGE.postman_collection.json


## CLIENT HTTP

### Api Swagger

[Swagger](https://service-api-swagger-wcyidxth5q-uc.a.run.app/)
 
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

### Architecture Challenge

![arqhi](./image/DEMO_CURRENCY_EXCHANGE.jpg)