package io.github.codejanovic.discord.bot.repository;

import io.github.codejanovic.discord.bot.entities.Account;

public interface AccountRepository {
    void persist(Account account);
}
