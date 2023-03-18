FROM amazoncorretto:17-alpine
EXPOSE 8080
ADD target/sophos-0.0.1.jar sophos-0.0.1.jar
ENTRYPOINT ["java", "-jar", "sophos-0.0.1.jar"]