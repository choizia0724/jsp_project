# Use an OpenJDK 17 base image
FROM openjdk:17-jdk-slim AS build
WORKDIR .

# Copy Gradle wrapper and related files
COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew

# Copy the project files
COPY . .

# Build the project
RUN ./gradlew clean build --info

# Second stage to run the app
FROM openjdk:17-jdk-slim
WORKDIR .

# Copy the built WAR file from the build stage
COPY --from=build /build/libs/*.war app.war

# Expose the port the app runs on
EXPOSE 8080

# Set the entrypoint to run the WAR file
ENTRYPOINT ["java", "-jar", "app.war"]
