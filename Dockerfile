FROM gradle:alpine as build
USER root
COPY . /workdir
WORKDIR /workdir

FROM openjdk:alpine
WORKDIR /app
COPY /build/libs/btc-1.0.jar btc.jar
EXPOSE 8080 8080

ENTRYPOINT exec java -jar btc.jar
