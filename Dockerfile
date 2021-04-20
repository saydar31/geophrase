FROM openjdk:8-jdk-alpine
VOLUME /home/server
WORKDIR /home/server
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} geophrase.jar
ENTRYPOINT ["sh", "-c", "java -jar /geophrase.jar"]