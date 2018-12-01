package io.github.codejanovic.discord.bot;


import io.github.codejanovic.discord.bot.listener.*;
import org.javacord.api.DiscordApi;
import org.jusecase.inject.Component;

import javax.inject.Inject;

@Component
public class Bot {
    @Inject
    DiscordApi _api;

    @Inject
    CreateProfileListener _createProfileListener;

    @Inject
    AddAccountListener _addAccountListener;

    @Inject
    ShowProfileListener _showProfileListener;

    @Inject
    ShowHelpListener _showHelpListener;

    @Inject
    DeleteProfileListener _deleteProfileListener;

    public void start() {
        _api.addMessageCreateListener(_createProfileListener);
        _api.addMessageCreateListener(_addAccountListener);
        _api.addMessageCreateListener(_showProfileListener);
        _api.addMessageCreateListener(_showHelpListener);
        _api.addMessageCreateListener(_deleteProfileListener);
    }
}
