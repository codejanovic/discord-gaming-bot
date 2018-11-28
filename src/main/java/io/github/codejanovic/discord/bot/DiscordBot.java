package io.github.codejanovic.discord.bot;

public interface DiscordBot {
    String name();

    final class BySystemProperty implements DiscordBot {

        @Override
        public String name() {
            return System.getProperty("botName", "Gaben");
        }
    }
}
