FROM openjdk:11-jdk-slim
VOLUME /tmp
ARG JAR_FILE=/kafka-producer-0.0.1-SNAPSHOT.jar
COPY ../kafka-producer/target/kafka-producer-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]