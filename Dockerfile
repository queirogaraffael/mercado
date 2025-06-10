FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY mercado /app/mercado

WORKDIR /app/mercado

RUN mvn clean package -DskipTests 

FROM eclipse-temurin:17-jre-alpine AS runtime

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app
COPY --from=build /app/mercado/target/mercado-0.0.1-SNAPSHOT.jar /app/mercado.jar

RUN chown appuser:appgroup /app/mercado.jar

USER appuser

EXPOSE 8080

CMD ["java", "-jar", "/app/mercado.jar"]
