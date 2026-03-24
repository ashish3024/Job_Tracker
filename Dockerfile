# Stage 1 — Build the jar using Gradle
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# Stage 2 — Run the jar in a lightweight image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]