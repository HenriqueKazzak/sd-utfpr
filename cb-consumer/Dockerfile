FROM openjdk:21 AS builder
VOLUME /tmp

COPY target/*.jar cb-consumer.jar
ENTRYPOINT ["java","-jar","/cb-consumer.jar"]


