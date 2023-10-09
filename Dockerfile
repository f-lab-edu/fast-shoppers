FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/fastshoppers-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]