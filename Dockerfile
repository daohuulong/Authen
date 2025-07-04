# Build stage
FROM maven:3.9.10-amazoncorretto-17 AS build

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM maven:3.9.10-amazoncorretto-17

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/Authen-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]