package io.github.codejanovic.discord.bot;


import com.google.cloud.firestore.Firestore;
import io.github.codejanovic.discord.bot.listener.CreateProfileListener;
import org.javacord.api.DiscordApi;
import org.jusecase.inject.Component;

import javax.inject.Inject;

/**
 * required System properties:
 * * xodusDirectory, default: /srv/.xodus
 * * botName, default: Gaben
 * * botToken, no default
 * * adminUsers, no default
 * * adminRoles, no default
 */

@Component
public class Bot {
    @Inject
    DiscordApi _api;

    @Inject
    CreateProfileListener _createProfileListener;

    @Inject
    Firestore _firestore;

    public void start() {
        _api.addMessageCreateListener(_createProfileListener);
    }
}
