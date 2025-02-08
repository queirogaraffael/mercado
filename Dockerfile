FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY mercado/target/mercado-0.0.1-SNAPSHOT.jar /app/mercado.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/mercado.jar"]
