FROM amazoncorretto:17-alpine3.18-jdk
COPY ./target/online-booking-*.jar app.jar
CMD ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]