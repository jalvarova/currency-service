node {
    stage("Jenkins init pipeline") {
    }

    def JAR_FILE = "";
    def VERSION = "";
    def ARTIFACTID = "";
    def TARGET_REPO = "";
    def POSTMAN = "";

    stage("get child repository") {
        dir("checkout-directory") {
            sh "ls -la"
            sh "echo ${WORKSPACE}/checkout-directory"
            sh "rm -rf ${WORKSPACE}/checkout-directory/*"
            sh "env"
            checkout([$class           : "GitSCM", branches: [[name: "*/${env.REPO_BRANCH}"]],
                      userRemoteConfigs: [[credentialsId: "github-account", url: "${env.REPO_URL}"]]])
        }
    }


    stage("build artifact") {

        stage("build jar file") {
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

//        stage('Publish test results Junit') {
//            dir("checkout-directory/${env.SERVICE}") {
//                junit 'target/surefire-reports/*.xml'
//                archiveArtifacts 'target/*.jar'
//            }
//        }
    }

    DOCKER_FOUND = ''
    withCredentials([file(credentialsId: "service-account-gcp", variable: "COMPUTE_CREDENTIALS")]) {

        stage("Auth Login GCP") {
            sh("gcloud auth activate-service-account --key-file ${COMPUTE_CREDENTIALS}")
        }

        stage("Build & push Docker Image") {
            dir("checkout-directory/${env.SERVICE}") {
                sh("docker build --build-arg ARTIFACT_ID,ARTIFACT_VERSION,APPLICATION_PORT . -t ${ARTIFACTID}:${VERSION}")
                sh("docker images")
                sh("docker tag ${ARTIFACTID}:${VERSION} gcr.io/${env.PROJECT_ID}/${ARTIFACTID}:${VERSION}")
                sh("gcloud docker --  push gcr.io/${env.PROJECT_ID}/${ARTIFACTID}:${VERSION}")
            }
        }

        stage("Install Cloud Run Service") {
            sh("gcloud beta run deploy service-currency-exchange" +
                    " --image gcr.io/${env.PROJECT_ID}/${ARTIFACTID}:${VERSION}" +
                    " --args ARTIFACT_ID=${ARTIFACTID},ARTIFACT_VERSION=${VERSION},APPLICATION_PORT=${env.APP_PORT} " +
                    " --platform managed " +
                    " --allow-unauthenticated " +
                    " --cpu=1 " +
                    " --memory=512Mi " +
                    " --region=us-central1" +
                    " --project=${env.PROJECT_ID}" +
                    " --service-account=service-azure-devops")

        }
    }
}