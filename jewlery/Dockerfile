FROM amazoncorretto:21.0.4 as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests  # For Maven

FROM amazoncorretto:21.0.4
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]