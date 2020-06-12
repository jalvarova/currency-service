FROM openjdk:8
RUN apt-get update && apt-get upgrade \
&& apt-get install -y ca-certificates \
&& update-ca-certificates \
# Change TimeZone
&& apt-get install -y tzdata
ENV TZ=America/Lima
RUN mkdir -p /currency-exchange/
# Copy *.jar and resources files
COPY target/currency-exchange-3.0.0.jar /currency-exchange/
# Run service java
CMD ["sh","-c","java -jar /currency-exchange/currency-exchange-3.0.0.jar -Duser.timezone=America/Lima ${JAVA_OPTS}"]
RUN du -sh /var/cache/apt