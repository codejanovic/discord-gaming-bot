FROM maven:3.6.0-jdk-8 AS builder
COPY src /usr/app/src
COPY pom.xml /usr/app
RUN mvn -f /usr/app/pom.xml clean package

FROM java:8
ENV BOT_NAME="Gaben"
ENV BOT_TOKEN="someValidToken"
RUN mkdir -p /usr/app
WORKDIR /usr/app
COPY --from=builder /usr/app/target/discord-gaming-bot.jar /usr/app/discord-gaming-bot.jar
VOLUME /var/lib/discord-gaming-bot
CMD java -DxodusDirectory=/var/lib/discord-gaming-bot/.xodus -DbotName=$BOT_NAME -DbotToken=$BOT_TOKEN -Djava.security.egd=file:/dev/./urandom -jar discord-gaming-bot.jar