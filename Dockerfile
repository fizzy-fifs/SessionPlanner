FROM openjdk:8-jdk-alpine
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} .
# ENTRYPOINT ["java", "-jar","/app/build/libs/app-1.0.0.jar"]