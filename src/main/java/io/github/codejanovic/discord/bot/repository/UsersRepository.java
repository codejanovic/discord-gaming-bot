package io.github.codejanovic.discord.bot.repository;

import io.github.codejanovic.discord.bot.entities.DiscordUser;

import java.util.Optional;

public interface UsersRepository {
    boolean persist(final DiscordUser discordUser);

    Optional<DiscordUser> getBy(final DiscordUser discordUser);
}
