FROM openjdk:21 AS builder
VOLUME /tmp

COPY target/*.jar app-b.jar
ENTRYPOINT ["java","-jar","/app-b.jar"]


