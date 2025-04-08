# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jre-slim-buster

# Set the working directory in the container
WORKDIR /app

# Copy the local JAR file to the container
COPY target/zone-service.jar /app/zone-service.jar

# Run the JAR file when the container starts
CMD ["java", "-jar", "zone-service.jar"]

# Expose the port the app will run on
EXPOSE 8081
