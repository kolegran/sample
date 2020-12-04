FROM openjdk:11-jre-slim

COPY build/libs/sample-0.0.1-SNAPSHOT.jar /app

CMD java -jar /app/sample-0.0.1-SNAPSHOT.jar