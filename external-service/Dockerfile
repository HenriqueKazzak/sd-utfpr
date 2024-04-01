FROM openjdk:21 AS builder
VOLUME /tmp

COPY target/*.jar external-service.jar
ENTRYPOINT ["java","-jar","/external-service.jar"]


