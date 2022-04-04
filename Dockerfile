FROM openjdk:16
EXPOSE 8080
ARG JAR_FILE=target/funded-local-server.jar funded-local-server.jar
COPY ${JAR_FILE} .
ENTRYPOINT ["java", "-jar","/funded-local-server.jar"]