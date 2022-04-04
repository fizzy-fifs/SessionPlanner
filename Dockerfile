FROM openjdk:16
EXPOSE 8080
ARG JAR_FILE=publish/funded-local-server.jar funded-local-server.jar
COPY ${JAR_FILE} .
RUN echo "MONGODB_USERNAME = $MONGODB_USERNAME" > /environment_variables
RUN echo "MONGODB_PASSWORD = $MONGODB_PASSWORD" > /environment_variables
ENTRYPOINT ["java", "-jar","/funded-local-server.jar"]