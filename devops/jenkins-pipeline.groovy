node {
    stage("Jenkins Pipeline") {
    }

    def JAR_FILE = "";
    def VERSION = "";
    def ARTIFACTID = "";
    def TARGET_REPO = "";
    def POSTMAN = "";

    stage("Get child repository") {
        dir("checkout-directory") {
            sh "ls -la"
            sh "echo ${WORKSPACE}/checkout-directory"
            sh "rm -rf ${WORKSPACE}/checkout-directory/*"
            sh "env"
            checkout([$class           : "GitSCM", branches: [[name: "*/${env.REPO_BRANCH}"]],
                      userRemoteConfigs: [[credentialsId: "github-account", url: "${env.REPO_URL}"]]])
        }
    }


    stage("Build artifact") {

        dir("checkout-directory/${env.SERVICE}") {
            sh "./mvnw clean install -Dmaven.test.skip=false"
            GROUPID = sh(script: "./mvnw help:evaluate -Dexpression=project.groupId -q -DforceStdout", returnStdout: true)
            VERSION = sh(script: "./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true)
            ARTIFACTID = sh(script: "./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout", returnStdout: true)
        }
        TARGET_REPO = "${WORKSPACE}/checkout-directory/${ARTIFACTID}"
        JAR_FILE = "${TARGET_REPO}/target/${ARTIFACTID}-${VERSION}.jar"
        println(JAR_FILE)
    }

    DOCKER_FOUND = ''
    withCredentials([file(credentialsId: "service-account-gcp", variable: "COMPUTE_CREDENTIALS")]) {

        stage("Auth Login GCP") {
        }

        stage("Build & push Docker Image") {
            dir("checkout-directory/${env.SERVICE}") {

                sh("gcloud auth activate-service-account ${env.ACCOUNT_EMAIL} --key-file ${COMPUTE_CREDENTIALS}")
                sh("gcloud auth configure-docker us.gcr.io -q")
                sh("docker-credential-gcloud list")

                sh("docker build --build-arg ARTIFACT_ID,ARTIFACT_VERSION,APPLICATION_PORT . -t ${ARTIFACTID}:${VERSION}")
                sh("docker images")
                sh("docker tag ${ARTIFACTID}:${VERSION} gcr.io/${env.PROJECT_ID}/${ARTIFACTID}:${VERSION}")
                //sh("gcloud docker --  push gcr.io/${env.PROJECT_ID}/${ARTIFACTID}:${VERSION}")
                sh("docker push gcr.io/${env.PROJECT_ID}/${ARTIFACTID}:${VERSION}")
            }
        }

        stage("Deploy Cloud Run Service") {
            sh("gcloud beta run deploy service-currency-exchange" +
                    " --image gcr.io/${env.PROJECT_ID}/${ARTIFACTID}:${VERSION}" +
                    " --args ARTIFACT_ID=${ARTIFACTID},ARTIFACT_VERSION=${VERSION},APPLICATION_PORT=${env.APP_PORT} " +
                    " --platform managed " +
                    " --allow-unauthenticated " +
                    " --cpu=1 " +
                    " --memory=512Mi " +
                    " --region=us-central1" +
                    " --project=${env.PROJECT_ID}" +
                    " --service-account=${env.SERVICE_ACCOUNT}")

        }
        dir("checkout-directory/swagger") {
            def API_SWAGGER = "swagger-${env.SERVICE}:${VERSION}"

            stage('Publish Swagger Service') {
                sh("echo ${API_SWAGGER}")
                stage("Build & push Docker Image Swagger") {
                    sh("docker build  . -t ${API_SWAGGER}")
                    sh("docker tag ${API_SWAGGER} gcr.io/${env.PROJECT_ID}/${API_SWAGGER}")
                    sh("gcloud docker --  push gcr.io/${env.PROJECT_ID}/${API_SWAGGER}")
                }
            }

            stage("Deploy Cloud Run API Swagger") {
                sh("gcloud beta run deploy swagger-${env.SERVICE}" +
                        " --image gcr.io/${env.PROJECT_ID}/${API_SWAGGER}" +
                        " --platform managed " +
                        " --allow-unauthenticated " +
                        " --cpu=1 " +
                        " --memory=512Mi " +
                        " --region=us-central1" +
                        " --project=${env.PROJECT_ID}" +
                        " --service-account=${env.SERVICE_ACCOUNT}")

            }

        }
    }

    stage("Test implementation postman") {
        POSTMAN = "CURRENCY_EXCHANGE.postman_collection.json"
        sleep 30
        dir("checkout-directory") {
            sh "npm install -g newman"
            sh "newman run ${POSTMAN} -g api.postman_environment.json --reporters cli,junit --reporter-junit-export 'newman/report.xml'"
        }
    }

    stage('Publish test postman') {
        dir("checkout-directory") {
            junit 'newman/report.xml'
        }
    }
}