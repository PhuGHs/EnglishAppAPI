FROM openjdk:17

WORKDIR /app

COPY target/EnglishAppAPI-0.0.1-SNAPSHOT.jar /app

CMD ["java", "-jar", "EnglishAppAPI-0.0.1-SNAPSHOT.jar"]