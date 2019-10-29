FROM openjdk:11-jdk-slim
VOLUME /tmp
RUN \
apt-get install -y maven
CMD ["mvn clean install"]
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
