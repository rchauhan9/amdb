FROM openjdk:11-jdk-slim

WORKDIR /app
ENV profile=""

ADD target/amdb-0.0.1-SNAPSHOT.jar ./
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${profile}", "amdb-0.0.1-SNAPSHOT.jar"]