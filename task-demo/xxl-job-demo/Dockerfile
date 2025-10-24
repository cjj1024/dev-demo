# Dockerfile
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY xxl-job-demo/build/libs/xxl-job-demo.jar xxl-job-demo.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","xxl-job-demo.jar"]