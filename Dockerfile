FROM openjdk:8-jdk-alpine
MAINTAINER Kaushik Ajay
COPY target/InventoryMicroservice-0.0.1-SNAPSHOT.jar InventoryMicroservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/RestProject-0.0.1-SNAPSHOT.jar", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers","-browser"]