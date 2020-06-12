node {
    stage("Jenkins init pipeline") {
    }

    def VERSION_TAG = 'SNAPSHOT'
    def JAR_FILE = "";
    def imageName = ""
    def VERSION = "";
    def ARTIFACTID = "";
    def TARGET_REPO = "";
    def POSTMAN = "";

    stage("get child repository") {
        dir("checkout-directory") {
            sh "ls -la"
            sh "echo ${WORKSPACE}/checkout-directory"
            sh "rm -rf ${WORKSPACE}/checkout-directory/*"

            checkout([$class: "GitSCM", branches: [[name: "*/${env.REPO_BRANCH}"]],
                      userRemoteConfigs: [[credentialsId: "CredencialGitLab", url: "${env.REPO_URL}"]]])
        }
    }


    stage("build artifact") {

        stage("build jar file") {
            TARGET_REPO = "${WORKSPACE}/checkout-directory"
            dir("checkout-directory") {
                sh "./mvnw clean install -Dmaven.test.skip=false"
                GROUPID = sh(script: "./mvnw help:evaluate -Dexpression=project.groupId -q -DforceStdout", returnStdout: true)
                VERSION = sh(script: "./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true)
                ARTIFACTID = sh(script: "./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout", returnStdout: true)
            }
            JAR_FILE = "${TARGET_REPO}/target/${ARTIFACTID}-${VERSION}.jar"
            println(JAR_FILE)
        }

        stage('Publish test results Junit') {
            dir("checkout-directory") {
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage("test implementation postman") {
            POSTMAN = "CURRENCY_EXCHANGE.postman_collection.json"
            dir("checkout-directory") {
                sh "newman run ${POSTMAN} --reporters cli,junit --reporter-junit-export 'newman/report.xml'"
            }
        }

        stage('Publish test postman') {
            dir("checkout-directory") {
                junit 'newman/report.xml'
            }
        }

        stage("build & push Docker Image") {
            dir("checkout-directory") {
                imageName = "javadevelop/${ARTIFACTID}:${VERSION}-${VERSION_TAG}"
                println(imageName)
                sh "docker build -t ${imageName}  ."
                sh "docker tag ${imageName} ${imageName}"
                withCredentials([
                        [$class          : 'UsernamePasswordMultiBinding',
                         credentialsId   : 'service-account-docker-hub',
                         usernameVariable: 'USERNAME_DOCKERHUB', passwordVariable: 'PASS_DOCKERHUB']]) {
                    sh "docker login -u ${USERNAME_DOCKERHUB} --password ${PASS_DOCKERHUB}"
                    //sh "docker push ${env.DOCKER_HUB}/${ARTIFACTID}:${VERSION}-${VERSION_TAG}"
                    sh "docker logout"
                }
            }
        }

    }
    stage("Install container in VM") {
        imageName = "javadevelop/${ARTIFACTID}:${VERSION}-${VERSION_TAG}"
        withCredentials([file(credentialsId: "service-account-compute", variable: "COMPUTE_CREDENTIALS")]) {
            sh("gcloud auth activate-service-account --key-file ${COMPUTE_CREDENTIALS}")
            sh "gcloud compute ssh --zone us-central1-a challenge --project servicesmapa-11 --command='docker rm -f ${env.APP_NAME}'"
            sh "gcloud compute ssh --zone us-central1-a challenge --project servicesmapa-11 --command='docker pull ${imageName}'"
            sh "gcloud compute ssh --zone us-central1-a challenge --project servicesmapa-11 --command='docker run -d --name ${env.APP_NAME} -p 80:9080 ${imageName}'"
            sh "gcloud compute ssh --zone us-central1-a challenge --project servicesmapa-11 --command='docker ps'"

        }
    }
}node {
    stage("Jenkins init pipeline") {
    }

    def VERSION_TAG = 'SNAPSHOT'
    def JAR_FILE = "";
    def imageName = ""
    def VERSION = "";
    def ARTIFACTID = "";
    def TARGET_REPO = "";
    def POSTMAN = "";

    stage("get child repository") {
        dir("checkout-directory") {
            sh "ls -la"
            sh "echo ${WORKSPACE}/checkout-directory"
            sh "rm -rf ${WORKSPACE}/checkout-directory/*"

            checkout([$class: "GitSCM", branches: [[name: "*/${env.REPO_BRANCH}"]],
                      userRemoteConfigs: [[credentialsId: "CredencialGitLab", url: "${env.REPO_URL}"]]])
        }
    }


    stage("build artifact") {

        stage("build jar file") {
            TARGET_REPO = "${WORKSPACE}/checkout-directory"
            dir("checkout-directory") {
                sh "./mvnw clean install -Dmaven.test.skip=false"
                GROUPID = sh(script: "./mvnw help:evaluate -Dexpression=project.groupId -q -DforceStdout", returnStdout: true)
                VERSION = sh(script: "./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true)
                ARTIFACTID = sh(script: "./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout", returnStdout: true)
            }
            JAR_FILE = "${TARGET_REPO}/target/${ARTIFACTID}-${VERSION}.jar"
            println(JAR_FILE)
        }

        stage('Publish test results Junit') {
            dir("checkout-directory") {
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage("test implementation postman") {
            POSTMAN = "CURRENCY_EXCHANGE.postman_collection.json"
            dir("checkout-directory") {
                sh "newman run ${POSTMAN} --reporters cli,junit --reporter-junit-export 'newman/report.xml'"
            }
        }

        stage('Publish test postman') {
            dir("checkout-directory") {
                junit 'newman/report.xml'
            }
        }

        stage("build & push Docker Image") {
            dir("checkout-directory") {
                imageName = "javadevelop/${ARTIFACTID}:${VERSION}-${VERSION_TAG}"
                println(imageName)
                sh "docker build -t ${imageName}  ."
                sh "docker tag ${imageName} ${imageName}"
                withCredentials([
                        [$class          : 'UsernamePasswordMultiBinding',
                         credentialsId   : 'service-account-docker-hub',
                         usernameVariable: 'USERNAME_DOCKERHUB', passwordVariable: 'PASS_DOCKERHUB']]) {
                    sh "docker login -u ${USERNAME_DOCKERHUB} --password ${PASS_DOCKERHUB}"
                    //sh "docker push ${env.DOCKER_HUB}/${ARTIFACTID}:${VERSION}-${VERSION_TAG}"
                    sh "docker logout"
                }
            }
        }

    }
    stage("Install container in VM") {
        imageName = "javadevelop/${ARTIFACTID}:${VERSION}-${VERSION_TAG}"
        withCredentials([file(credentialsId: "service-account-compute", variable: "COMPUTE_CREDENTIALS")]) {
            sh("gcloud auth activate-service-account --key-file ${COMPUTE_CREDENTIALS}")
            sh "gcloud compute ssh --zone us-central1-a challenge --project servicesmapa-11 --command='docker rm -f ${env.APP_NAME}'"
            sh "gcloud compute ssh --zone us-central1-a challenge --project servicesmapa-11 --command='docker pull ${imageName}'"
            sh "gcloud compute ssh --zone us-central1-a challenge --project servicesmapa-11 --command='docker run -d --name ${env.APP_NAME} -p 80:9080 ${imageName}'"
            sh "gcloud compute ssh --zone us-central1-a challenge --project servicesmapa-11 --command='docker ps'"

        }
    }
}