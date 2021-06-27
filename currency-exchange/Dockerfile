FROM openjdk:8

ENV ARTIFACT_ID=currency-exchange
ENV ARTIFACT_VERSION=5.0.0
ENV APPLICATION_PORT=8080

RUN apt-get update \
	&& apt-get install -y ca-certificates \
	&& update-ca-certificates \
	&& apt-get install -y tzdata
	ENV TZ=America/Lima
RUN mkdir -p /${ARTIFACT_ID}/target && mkdir -p /${ARTIFACT_ID}/resources
COPY target/${ARTIFACT_ID}-${ARTIFACT_VERSION}.jar /${ARTIFACT_ID}/target/
COPY src/main/resources/* /${ARTIFACT_ID}/resources/
CMD ["sh","-c","java -jar /${ARTIFACT_ID}/target/${ARTIFACT_ID}-${ARTIFACT_VERSION}.jar -Duser.timezone=America/Lima ${JAVA_OPTS}"]
# Clean APK cache
RUN du -sh /var/cache/apt