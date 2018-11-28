package io.github.codejanovic.discord.bot.inject.provider;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.jusecase.inject.Component;

import javax.inject.Provider;


@Component
public class DiscordApiProvider implements Provider<DiscordApi> {

    @Override
    public DiscordApi get() {
        return new DiscordApiBuilder().setToken(System.getProperty("botToken"))
                .login()
                .join();
    }
}
