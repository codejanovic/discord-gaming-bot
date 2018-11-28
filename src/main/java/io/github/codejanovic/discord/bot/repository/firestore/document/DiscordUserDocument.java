package io.github.codejanovic.discord.bot.repository.firestore.document;

import com.google.cloud.firestore.DocumentSnapshot;
import io.github.codejanovic.discord.bot.entities.DiscordUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class DiscordUserDocument implements FirestoreDocument<DiscordUser> {

    @Override
    public Map<String, Object> toDocument(final DiscordUser user) {
        final Map<String, Object> map = new HashMap<>();
        map.put("discordUserId", user.discordUserId());
        map.put("discordUserName", user.discordUserName());
        return map;
    }

    @Override
    public Optional<DiscordUser> toEntity(final DocumentSnapshot snapshot) {
        if (!snapshot.exists()) {
            return Optional.empty();
        }

        return Optional.of(new DiscordUser.Mutable()
                .withDiscordUserId(String.valueOf(snapshot.getData().get("discordUserId")))
                .withDiscordUserName(String.valueOf(snapshot.getData().get("discordUserName")))
                .build());
    }
}
