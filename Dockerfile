# 1. Build Stage: Maven build karne ke liye
FROM maven:3.8.7-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src /app/src
RUN mvn clean install -DskipTests

# 2. Package/Runtime Stage: Application run karne ke liye
FROM openjdk:17-jdk-slim
WORKDIR /app
# Final JAR file ko build stage se copy karna
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9090

# Start command (yeh command Render ke "Docker Command" ko override karegi)
CMD ["java", "-jar", "app.jar"]