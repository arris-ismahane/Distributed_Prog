FROM amazoncorretto:21.0.4
WORKDIR /app
COPY . .
RUN yum update -y && yum install -y maven
RUN mvn clean package -DskipTests
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]