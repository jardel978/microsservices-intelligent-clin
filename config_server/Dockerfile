FROM eclipse-temurin:21-jre-alpine
ARG JAR_FILE=target/config_server.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8888