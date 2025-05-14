FROM openjdk:17
EXPOSE 8080
WORKDIR /app
COPY target/salon.jar salon.jar
ENTRYPOINT ["java", "-jar", "salon.jar"]