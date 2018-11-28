package io.github.codejanovic.discord.bot.repository;

import io.github.codejanovic.discord.bot.entities.DiscordUser;
import io.github.codejanovic.discord.bot.repository.document.DiscordUserDocument;

public class FirestoreDocuments {

    private FirestoreDocument<DiscordUser> _discordUserDocument = new DiscordUserDocument();

    public FirestoreDocument<DiscordUser> discordUser() {
        return _discordUserDocument;
    }
}
