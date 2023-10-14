FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/gym.jar gym.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/gym.jar"]