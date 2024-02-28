FROM maven:3.9.6-eclipse-temurin-17

COPY . .

EXPOSE 8080

RUN mvn clean package -DskipTests

ENTRYPOINT ["java", "-jar", "target/billing-0.0.1-SNAPSHOT.jar"]