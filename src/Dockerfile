FROM maven:3.6.0-jdk-11-slim AS build 
RUN mkdir -p /app
WORKDIR /app
COPY . . 
COPY pom.xml /app  
RUN mvn -f /app/pom.xml clean package
FROM openjdk:11-jre-slim 
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar  
EXPOSE 3038  
CMD ["java","-jar","app.jar"] 
