FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . /app
RUN mvn package -f /app/pom.xml

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build app/target/EnglishAppAPI-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8090

CMD ["java", "-jar", "app.jar"]
