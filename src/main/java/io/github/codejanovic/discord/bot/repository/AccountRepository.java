package io.github.codejanovic.discord.bot.repository;

import io.github.codejanovic.discord.bot.entities.Account;
import io.github.codejanovic.discord.bot.entities.DiscordUser;

import java.util.Collection;

public interface AccountRepository {
    boolean persist(Account account);

    Collection<Account> getBy(DiscordUser user);
}
