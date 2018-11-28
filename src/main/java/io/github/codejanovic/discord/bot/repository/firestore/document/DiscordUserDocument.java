package io.github.codejanovic.discord.bot.repository.firestore.document;

import io.github.codejanovic.discord.bot.entities.DiscordUser;

import java.util.HashMap;
import java.util.Map;


public class DiscordUserDocument implements FirestoreDocument<DiscordUser> {

    @Override
    public Map<String, Object> toDocument(final DiscordUser user) {
        final Map<String, Object> map = new HashMap<>();
        map.put("discordUserId", user.discordUserId());
        map.put("discordUserName", user.discordUserName());
        return map;
    }

    @Override
    public DiscordUser toEntity(final Map<String, Object> fields) {
        return new DiscordUser.Mutable()
                .withDiscordUserId(String.valueOf(fields.get("discordUserId")))
                .withDiscordUserName(String.valueOf(fields.get("discordUserName")))
                .build();
    }
}
