package io.github.codejanovic.discord.bot.repository.firestore.document;

import io.github.codejanovic.discord.bot.entities.AccountProvider;

import java.util.HashMap;
import java.util.Map;


public class AccountProviderDocument implements FirestoreDocument<AccountProvider> {

    @Override
    public Map<String, Object> toDocument(final AccountProvider provider) {
        final Map<String, Object> map = new HashMap<>();
        map.put("id", provider.id());
        map.put("name", provider.name());
        map.put("command", provider.command());
        return map;
    }

    @Override
    public AccountProvider toEntity(final Map<String, Object> fields) {
        return new AccountProvider.Mutable()
                .withId(String.valueOf(fields.get("id")))
                .withName(String.valueOf(fields.get("name")))
                .withCommand(String.valueOf(fields.get("command")))
                .build();
    }
}
