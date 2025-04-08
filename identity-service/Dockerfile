# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jre-slim-buster

# Set the working directory in the container
WORKDIR /app

# Copy the local JAR file to the container
COPY target/identity-service.jar /app/identity-service.jar

# Run the JAR file when the container starts
CMD ["java", "-jar", "identity-service.jar"]

# Expose the port the app will run on
EXPOSE 8080


# Set the working directory in the container
WORKDIR /app

# Copy the local JAR file to the container
COPY target/identity-service.jar /app/identity-service.jar

# Run the JAR file when the container starts
CMD ["java", "-jar", "identity-service.jar"]

# Expose the port the app will run on
EXPOSE 8080
