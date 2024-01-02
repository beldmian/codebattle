FROM clojure:tools-deps-alpine AS builder
WORKDIR /opt/app
COPY . .
RUN clojure -T:build uberjar :project http

FROM eclipse-temurin:17-jre-alpine

RUN apk add python3 clang musl-dev

WORKDIR /opt/app
COPY --from=builder /opt/app/projects/http/target/http.jar /opt/app/http.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/http.jar"]
