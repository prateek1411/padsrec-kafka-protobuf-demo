FROM openjdk:11-jdk-slim
VOLUME /tmp
RUN \
"apt-get install -y maven"
CMD ["mvn clean install"]
ARG JAR_FILE=/kafka-producer-0.0.1-SNAPSHOT.jar
COPY ../kafka-producer/target/kafka-producer-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]