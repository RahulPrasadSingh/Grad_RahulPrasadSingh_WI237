# ---------- STAGE 1 : BUILD (Compile inside container) ----------
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml first (for dependency caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the Spring Boot jar
RUN mvn clean package -DskipTests

# ---------- STAGE 2 : RUN APPLICATION ----------
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Spring Boot default port
EXPOSE 5000

# Run application
ENTRYPOINT ["java","-jar","app.jar"]