FROM openjdk:11
COPY ./target/car-rental-api-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "car-rental-api-0.0.1-SNAPSHOT.jar"]