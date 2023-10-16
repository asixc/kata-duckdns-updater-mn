FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
COPY ./target/duckdns-updater-mn-0.1.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
ENV TZ="Europe/Madrid"