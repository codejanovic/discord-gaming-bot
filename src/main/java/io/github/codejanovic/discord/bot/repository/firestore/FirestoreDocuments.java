package io.github.codejanovic.discord.bot.repository.firestore;

import io.github.codejanovic.discord.bot.entities.DiscordUser;
import io.github.codejanovic.discord.bot.repository.firestore.document.DiscordUserDocument;
import io.github.codejanovic.discord.bot.repository.firestore.document.FirestoreDocument;

public class FirestoreDocuments {

    private FirestoreDocument<DiscordUser> _discordUserDocument = new DiscordUserDocument();

    public FirestoreDocument<DiscordUser> discordUser() {
        return _discordUserDocument;
    }
}
