package io.github.codejanovic.discord.bot.repository;

import io.github.codejanovic.discord.bot.entities.AccountProvider;

import java.util.Collection;

public interface AccountProviderRepository {
    Collection<AccountProvider> getAll();
}
