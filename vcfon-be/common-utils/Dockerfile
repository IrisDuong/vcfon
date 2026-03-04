FROM eclipse-temurin:17-jdk-alpine
WORKDIR /eatp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} common-utils.jar
ENTRYPOINT ["java","-jar","/eatp/common-utils.jar"]