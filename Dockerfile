FROM maven:3.6.0-jdk-8 AS builder
COPY src /usr/app/src
COPY pom.xml /usr/app
RUN mvn -f /usr/app/pom.xml clean package

FROM java:8
ENV BOT_TOKEN="someValidToken"
ENV FIRESTORE_CREDENTIALS="/some/path/to/credentials.json"
RUN mkdir -p /usr/app
WORKDIR /usr/app
COPY --from=builder /usr/app/target/discord-gaming-bot.jar /usr/app/discord-gaming-bot.jar
VOLUME /var/lib/discord-gaming-bot
CMD java -Xms1g -Xms2g -DbotToken=$BOT_TOKEN -Dcredentials=$FIRESTORE_CREDENTIALS -Djava.security.egd=file:/dev/./urandom -jar discord-gaming-bot.jar