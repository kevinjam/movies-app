# Use the official Maven image to build the app with JDK 21
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy the project files to the container
COPY . .

# Build the Maven project (without tests)
RUN mvn clean package -DskipTests

# Use a lightweight OpenJDK image for running the app
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory for the runtime container
WORKDIR /app

# Copy the jar from the build container
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 9090

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

