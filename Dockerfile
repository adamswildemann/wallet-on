# Build
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests package

# Runtime
FROM eclipse-temurin:21-jre
ENV TZ=America/Sao_Paulo \
JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"
RUN useradd -ms /bin/bash spring
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
USER spring
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]