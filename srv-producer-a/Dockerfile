FROM openjdk:21 AS builder
VOLUME /tmp

COPY target/*.jar app-a.jar
ENTRYPOINT ["java","-jar","/app-a.jar"]


