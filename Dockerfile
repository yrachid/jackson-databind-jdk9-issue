FROM openjdk:9.0.1-11-jre-slim

WORKDIR /usr/src/app

CMD ["./gradlew", "--no-daemon", "--info", "test"]
