FROM maven:3.8-openjdk-18 AS build
MAINTAINER Kaushik Ajay
RUN mvn clean package

FROM openjdk:8-jdk-alpine
COPY --from=build target/InventoryMicroservice-0.0.1-SNAPSHOT.jar InventoryMicroservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/InventoryMicroservice-0.0.1-SNAPSHOT.jar", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers","-browser"]
