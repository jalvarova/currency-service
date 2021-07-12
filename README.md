# Pipeline Jenkins and Deployment Serverless Cloud Run - Part II

In this part we will do the Jenkins server provisioning with Compute Engine, 
and we'll build a pipeline to delivery the microservice in Cloud Run, 
going through testing with newman and publishing the API contract with Open API.

![arqhi](./img/arq.jpg)


# Pre requirements

* [Cloud SDK Install](https://cloud.google.com/sdk/docs/quickstart)  :mag_right:

* [Gcloud Auth Login](https://cloud.google.com/sdk/gcloud/reference/auth/login)  :mag_right:

We have to create a project on Google Cloud Platform, we'll enable some services to use in our example.

#### Create project in GCP

```bash
$ gcloud auth login
$ gcloud projects create ${PROJECT_ID}
$ gcloud projects list
$ gcloud config set project ${PROJECT_ID}
```

#### Enabled services in GCP

```bash
$ gcloud services list --available
$ gcloud services enable compute.googleapis.com
$ gcloud services enable run.googleapis.com
$ gcloud services enable containerregistry.googleapis.com
$ gcloud services enable artifactregistry.googleapis.com
$ gcloud services enable storage-component.googleapis.com
```

## Provisioning Jenkins Server 

Now let's create the virtual machine on Compute Engine for provisioning a Jenkins Server.

```bash
$ gcloud compute zones list
$ gcloud compute disk-types list
$ gcloud compute images list

$ gcloud compute instances create jenkins-server 
    --zone=us-central1-a 
    --machine-type=e2-medium 
    --image-family=ubuntu-2004-lts  
    --image-project=ubuntu-os-cloud 
    --boot-disk-size=20GB  
    --tags=https-server,http-server,allow-internet-access 
    --service-account=${SERVICE-ACCOUNT} 
    --preemptible 
    --no-restart-on-failure  
    --maintenance-policy=terminate
```
:heavy_check_mark: Verify virtual machine creation

![arqi](./img/vm-jenkins.png)

## Install Package in VM

Install the following packages on the virtual machine that are necessary to start installing Jenkins.

```bash
$ sudo apt-get update -y
$ sudo apt-get install git -y && sudo apt-get install curl -y 
$ sudo apt install openjdk-11-jre-headless -y
```
## Getting started Jenkins install :+1:

Install Docker according to the Linux distribution of the VM.

:file_folder: [docker install](./devops/install-docker.txt)

#### Installing Docker compose

```bash
$ sudo curl -L "https://github.com/docker/compose/releases/download/1.26.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
$ sudo chmod +x /usr/local/bin/docker-compose
$ docker-compose version
```

Create file with jenkins image definition.

```bash
$ sudo vim jenkins.yaml
```
#### Docker Compose

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

Jenkins run image

```bash
$ docker-compose -f jenkins.yml up -d
```

### Get password default Jenkins

Once the image is created, get the default password in the Jenkins image
 
```bash
$ docker exec -it jenkins_server sh -c "cat /var/jenkins_home/secrets/initialAdminPassword"
```

![arqi](./img/jenkins-start.png)

We add our user data in Jenkins, soon to enter the console.

![arqi](./img/jenkinks-user.png)

import job backup

```shell script
# get jenkins-cli
$ wget --no-check-certificate ${JENKINS_URL}/jnlpJars/jenkins-cli.jar
# import pipeline 
$ java -jar jenkins-cli.jar -s  ${JENKINS_URL} -auth  ${JENKINS_USER}:${JENKINS_PWD}  create-job DEPLOY-API-CURRENCy-V2 < job-backup.xml
# export pipeline 
$ java -jar jenkins-cli.jar -s  ${JENKINS_URL} -auth  ${JENKINS_USER}:${JENKINS_PWD}  get-job DEPLOY-API-CURRENCY > job-backup.xml
```
### Additional :memo:

Creating an image of the Jenkins instance, to have a backup of the configured VM.

```bash
gcloud beta compute machine-images create jenkins-server-backup --source-instance=jenkins-server --source-instance-zone=us-central1-a
```
:heavy_check_mark: Verify VM image

![arqi](./img/vm-image.png)

If we can eliminate the VM if we are not going to use.  :warning:

```bash
gcloud compute instances delete jenkins-server --zone=us-central1-a
```

### Pipeline 

Once the instance has been created correctly and importing the job or creating the Jenkins pipeline, 
we are going to execute the pipeline with the steps for delivery to Cloud Run.

![jenkins](./img/jenkins.png)

#### Credential creation :closed_lock_with_key:
 * GitHub account
 * GCP IAM service account, with the necessary permissions.

![credentials](./img/credentials.png)

Pipeline parameters

![parameters](./img/parameters.png)

Pipeline configuration with the reference to the Groovy that is in the repository of the artifact to be deployed.

![jenkins-pipeline](./img/jenkins-pipeline.png)

:rocket: Deployment of the microservices and the steps that the pipeline has from the construction of the artifact to the testing with newman.

![execution-pipeline](./img/execution-pipeline.png)

![log-jenkins](./img/log-jenkins.png)

[Jenkins Server](http://jenkins-wala.duckdns.org/) :mag_right:

## Postman Running Testing

Validate the collection of postman running all requests

![postman](./img/postman-test.png)


#### API Swagger

:file_folder: [Microservice Open API specification](./swagger/api.yml)

[URI Swagger API](https://swagger-currency-exchange-xfrmmz5d4a-uc.a.run.app/) :mag_right: 
 
## CLIENT HTTP

#### LOGIN AUTH

```bash
curl --location --request POST 'https://service-currency-exchange-xfrmmz5d4a-uc.a.run.app/authentication' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "walavo",
    "password": "12334"
}'
```

#### HEALTH CHECK

```bash
curl --location --request GET 'https://service-currency-exchange-xfrmmz5d4a-uc.a.run.app/health'
```


#### METRICS

```bash
curl --location --request GET 'https://service-currency-exchange-xfrmmz5d4a-uc.a.run.app/metrics'
```

#### APPLY CURRENCY EXCHANGE

```bash
curl --location --request POST 'https://service-currency-exchange-xfrmmz5d4a-uc.a.run.app/api/v1/currency-exchange/apply' \
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
curl --location --request PUT 'https://service-currency-exchange-xfrmmz5d4a-uc.a.run.app/api/v1/currency-exchange' \
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
curl --location --request GET 'https://service-currency-exchange-xfrmmz5d4a-uc.a.run.app/metrics' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YWxhdm8iLCJleHAiOjE2MjQ4NjU5NjYsImlhdCI6MTYyNDg0Nzk2Nn0.SCeK957PRYHBD90KEz-YuTS8pf0l-8FRcDMDGe7Bh2b-NAjxNObjrdh3qgp2XxtLpIzD2BuLq2H6DqNmTPFKUA'
```

#### GET ALL CURRENCY EXCHANGE

```bash
curl --location --request GET 'https://service-currency-exchange-xfrmmz5d4a-uc.a.run.app/metrics' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YWxhdm8iLCJleHAiOjE2MjQ4NjU5NjYsImlhdCI6MTYyNDg0Nzk2Nn0.SCeK957PRYHBD90KEz-YuTS8pf0l-8FRcDMDGe7Bh2b-NAjxNObjrdh3qgp2XxtLpIzD2BuLq2H6DqNmTPFKUA'
```

### Next Step :question:

 * Client Web with Angular
 * Infrastructure as Code with Terraform
 * Monitoring Application with Dynatrace
 
### References

[Jenkins-CLI](https://wiki.jenkins.io/display/JENKINS/Jenkins+CLI)

[Jenkins-Pipeline](https://www.jenkins.io/doc/pipeline/tour/hello-world/#:~:text=Jenkins%20Pipeline%20(or%20simply%20%22Pipeline,continuous%20delivery%20pipelines%20into%20Jenkins.&text=The%20definition%20of%20a%20Jenkins,a%20project's%20source%20control%20repository. )

[Create VM](https://cloud.google.com/sdk/gcloud/reference/compute/images/create)

[VM-Instance](https://cloud.google.com/compute/docs/instances/create-start-instance)

[VM Image](https://cloud.google.com/sdk/gcloud/reference/beta/compute/machine-images/create)

[Swagger](https://swagger.io/specification/v2/)