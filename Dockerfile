# Development Dockerfile with live reload support
FROM eclipse-temurin:21-jdk-alpine

# Install Maven
RUN apk add --no-cache maven

WORKDIR /app

# Copy pom.xml first and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Don't copy src — we mount it live in docker-compose)
# COPY src ./src

# Enable Spring DevTools restart
ENV SPRING_DEVTOOLS_RESTART_ENABLED=true

# Expose the app port
EXPOSE 8080

# Run the app using Maven
CMD ["mvn", "spring-boot:run"]
