# Use an OpenJDK 17 base image
FROM openjdk:17-jdk-slim AS build
WORKDIR /project
# Copy Gradle files
COPY gradlew .
COPY gradle gradle

# Copy the project files
COPY . .

# Build the project
RUN ./gradlew clean build

# Second stage to run the app
FROM openjdk:17-jdk-slim

# Copy the built WAR file
COPY /project/build/libs/*.war app.war

# Expose the port the app runs on
EXPOSE 8080

# Set the entrypoint to run the WAR file
ENTRYPOINT ["java", "-jar", "app.war"]
