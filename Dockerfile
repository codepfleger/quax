FROM openjdk:8-jdk-alpine
ENTRYPOINT [ "./gradlew bootRun" ]
EXPOSE 8080
