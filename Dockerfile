FROM openjdk:11

WORKDIR /app
ADD target/amdb-0.0.1-SNAPSHOT.jar ./
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "amdb-0.0.1-SNAPSHOT.jar"]