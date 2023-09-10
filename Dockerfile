FROM openjdk:17.0.2-jdk-slim-buster
COPY ./target/*.jar lms.jar
ENTRYPOINT ["java","-jar","lms.jar"]