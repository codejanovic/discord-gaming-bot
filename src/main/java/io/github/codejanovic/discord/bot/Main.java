package io.github.codejanovic.discord.bot;

import io.github.codejanovic.discord.bot.inject.Registry;

public class Main {

    public static void main(String[] args) {
        final Registry registry = Registry.instance;
        final Bot bot = new Bot();
        registry.inject(bot, Bot.class);
        new Bot().start();
    }
}
