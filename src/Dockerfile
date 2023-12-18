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




# Use the official Maven image as a base image
FROM maven:3.6.3-openjdk-8 AS builder

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the Maven POM file
COPY pom.xml .

# Download Maven dependencies (cache dependencies separately from source code)
RUN mvn dependency:go-offline

# Copy the source code
COPY src/ ./src/

# Build the application
RUN mvn package

# Use a smaller base image for the final image
FROM openjdk:8-jre-alpine

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the JAR file from the builder stage to the final image
COPY --from=builder /usr/src/app/target/demo-0.0.1-SNAPSHOT.jar .

# Expose the port the application runs on
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]

