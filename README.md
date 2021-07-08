# Pipeline Jenkins and Deployment Serverless Cloud Run Part II

![arqhi](./img/arq.jpg)

> We are going to access the GCP console with the account created Google

```bash
gcloud auth login
gcloud projects list
gcloud projects create ${PROJECT_ID}
gcloud config set project ${PROJECT_ID}
```


#Enabled services in GCP

```bash
gcloud services list --available
gcloud services enable compute.googleapis.com
gcloud services enable run.googleapis.com
gcloud services enable containerregistry.googleapis.com
gcloud services enable storage-component.googleapis.com
```

```bash
gcloud compute zones list
gcloud compute disk-types list
gcloud compute images list

gcloud compute instances create jenkins-server --zone=us-central1-a --machine-type=e2-medium --image-family=ubuntu-2004-lts  --image-project=ubuntu-os-cloud --boot-disk-size=20GB  --tags=https-server,http-server,allow-internet-access --service-account=${SERVICE-ACCOUNT} --preemptible --no-restart-on-failure  --maintenance-policy=terminate

gcloud beta compute machine-images create jenkins-server-backup --source-instance=jenkins-server --source-instance-zone=us-central1-a

gcloud compute instances delete jenkins-server --zone=us-central1-a
```

## Install Package VM

![arqhi](./img/jenkins-cloud-engine.jpg)

```bash
$ sudo apt-get update
$ sudo apt-get install git -y && sudo apt-get install curl -y 
$ sudo curl -L "https://github.com/docker/compose/releases/download/1.26.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
$ sudo chmod +x /usr/local/bin/docker-compose
$ docker-compose version
```

```bash
$ sudo vim jenkins.yaml
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
```bash
$ docker-compose -f jenkins.yml up -d
```

### Get password default Jenkins
```bash
docker exec -it jenkins_server sh -c "cat /var/jenkins_home/secrets/initialAdminPassword"
```

### Job Jenkins Pipeline

```shell script
# get jenkins-cli
wget --no-check-certificate ${JENKINS_URL}/jnlpJars/jenkins-cli.jar
# update so
sudo apt-get update
# install java
sudo apt install openjdk-11-jre-headless -y
# export pipeline 
java -jar jenkins-cli.jar -s  ${JENKINS_URL} -auth  ${JENKINS_USER}:${JENKINS_PWD}  get-job DEPLOY-API-CURRENCY > job-backup.xml
# import pipeline 
java -jar jenkins-cli.jar -s  ${JENKINS_URL} -auth  ${JENKINS_USER}:${JENKINS_PWD}  create-job DEPLOY-API-CURRENCy-V2 < job-backup.xml
```

![jenkins](./img/jenkins.png)

![credentials](./img/credentials.png)

![parameters](./img/parameters.png)

![jenkins-pipeline](./img/jenkins-pipeline.png)

![execution-pipeline](./img/execution-pipeline.png)

![log-jenkins](./img/log-jenkins.png)

[Jenkins Server](http://jenkins-wala.duckdns.org/)

## Postman Running Testing

![postman](./img/postman-test.png)


### Api Swagger

[Swagger API](https://swagger-currency-exchange-wcyidxth5q-uc.a.run.app)
 
## CLIENT HTTP

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


[Jenkins-CLI](https://wiki.jenkins.io/display/JENKINS/Jenkins+CLI)

[Jenkins-Pipeline](https://www.jenkins.io/doc/pipeline/tour/hello-world/#:~:text=Jenkins%20Pipeline%20(or%20simply%20%22Pipeline,continuous%20delivery%20pipelines%20into%20Jenkins.&text=The%20definition%20of%20a%20Jenkins,a%20project's%20source%20control%20repository. )

[Create VM](https://cloud.google.com/sdk/gcloud/reference/compute/images/create)

[VM-Instance](https://cloud.google.com/compute/docs/instances/create-start-instance)

[VM Image](https://cloud.google.com/sdk/gcloud/reference/beta/compute/machine-images/create)

