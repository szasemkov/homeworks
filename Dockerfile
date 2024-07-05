FROM openjdk:17 AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw clean package

FROM openjdk:17
COPY --from=build /app/target/homework*.jar /usr/local/lib/homework1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/homework1.jar"]