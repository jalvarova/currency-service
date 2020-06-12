node {
    stage("Jenkins init pipeline") {
        stage("build artifact")
    }

    stage("get child repository") {
        dir("checkout-directory") {
            checkout([$class: "GitSCM", branches: [[name: "*/${env.REPO_BRANCH}"]],
                      userRemoteConfigs: [[credentialsId: "CredencialGitLab", url: "${env.REPO_URL}"]]])
        }
        LOGBAK_XML = "${WORKSPACE}/checkout-directory/src/main/resources/logback.xml";
    }
    def VERSION_TAG = 'SNAPSHOT'
    def JAR_FILE = "";
    def imageName = ''
    def VERSION = "";
    def ARTIFACTID = "";
    def TARGET_REPO = "";
    def POSTMAN = "";

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

        stage("test implementation postman") {
            POSTMAN = "${env.APP_NAME.toUpperCase()}".concat(".postman_collection.json")
            dir("checkout-directory") {
                sh "newman run ${POSTMAN}"
            }
        }
        stage("build & push Docker Image") {
            imageName = "javadevelop/currency-exchange/${ARTIFACTID}:${VERSION}-${VERSION_TAG}"
            println(imageName)
            withCredentials([
                    [$class          : 'UsernamePasswordMultiBinding',
                     credentialsId   : 'service-account-docker-hub',
                     usernameVariable: 'USERNAME_DOCKERHUB', passwordVariable: 'PASS_DOCKERHUB']]) {
                sh "docker login -u ${USERNAME_DOCKERHUB} --password ${PASS_DOCKERHUB}"
                sh "docker build -t ${imageName} -f ."
                sh "docker tag ${imageName} ${imageName}"
                sh "docker push ${imageName}"
                sh "docker images"
                sh "docker logout"
            }
        }

    }
    stage("Install container in VM") {
        imageName = "javadevelop/currency-exchange/${ARTIFACTID}:${VERSION}-${VERSION_TAG}"
        withCredentials([file(credentialsId: "service-account-compute", variable: "COMPUTE_CREDENTIALS")]) {
            sh("gcloud auth activate-service-account --key-file ${COMPUTE_CREDENTIALS}")
            sh "gcloud compute ssh --zone us-central1-a challenge --project servicesmapa-11"
            sh "docker rm -f ${imageName}"
            sh "docker pull ${imageName}"
            sh "docker run -d --name ${env.APP_NAME} -p 80:9080 ${imageName}"
            sh "exit"
        }
    }
}