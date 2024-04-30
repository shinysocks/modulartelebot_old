FROM maven:3-eclipse-temurin-17-alpine
WORKDIR /app
COPY . .
RUN mvn clean compile
RUN rm -rf temp
CMD ["mvn", "exec:java"]
