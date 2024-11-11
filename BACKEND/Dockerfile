FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# List contents before copy (for debugging)
RUN ls -la

# Copy the entire target directory
COPY target ./target/

# List contents after copy (for debugging)
RUN ls -la target/

EXPOSE 8080

CMD ["java", "-jar", "target/BACKEND-0.0.1-SNAPSHOT.jar"]