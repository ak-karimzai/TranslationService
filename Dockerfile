FROM openjdk:17-jdk-alpine

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} app.jar

USER root
RUN apk add netcat-openbsd
COPY ./ShellScripts/wait-for.sh .
COPY ./ShellScripts/start.sh .

ENTRYPOINT ["java", "-jar", "/app.jar"]