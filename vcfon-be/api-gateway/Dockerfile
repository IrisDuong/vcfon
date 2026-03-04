FROM eclipse-temurin:17-jdk-alpine
WORKDIR /eatp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api-gateway.jar
ENTRYPOINT ["java","-jar","/eatp/api-gateway.jar"]