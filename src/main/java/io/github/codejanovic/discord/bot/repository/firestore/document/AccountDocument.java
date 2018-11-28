package io.github.codejanovic.discord.bot.repository.firestore.document;

import io.github.codejanovic.discord.bot.entities.Account;

import java.util.HashMap;
import java.util.Map;


public class AccountDocument implements FirestoreDocument<Account> {

    @Override
    public Map<String, Object> toDocument(final Account account) {
        final Map<String, Object> map = new HashMap<>();
        map.put("id", account.id());
        map.put("providerId", account.providerId());
        map.put("userId", account.userId());
        return map;
    }

    @Override
    public Account toEntity(final Map<String, Object> fields) {
        return new Account.Mutable()
                .withUserId(String.valueOf(fields.get("userId")))
                .withProviderId(String.valueOf(fields.get("providerId")))
                .build();
    }
}
