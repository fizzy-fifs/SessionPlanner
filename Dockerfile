FROM openjdk:16
EXPOSE 8080
ARG JAR_FILE=publish/funded-local-server.jar funded-local-server.jar
COPY ${JAR_FILE} .
ENV env_file: environment_variables
ENTRYPOINT ["java", "-jar","/funded-local-server.jar"]