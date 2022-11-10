FROM maven:3.8-openjdk-18 AS build
MAINTAINER Kaushik Ajay
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:8-jdk-alpine
COPY --from=build /usr/src/app/target/InventoryMicroservice-0.0.1-SNAPSHOT.jar /usr/app/InventoryMicroservice-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/InventoryMicroservice-0.0.1-SNAPSHOT.jar", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers","-browser"]
