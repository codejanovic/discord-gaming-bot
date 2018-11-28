package io.github.codejanovic.discord.bot;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.user.User;
import org.jusecase.inject.Component;

import javax.inject.Inject;

public interface DiscordBot {
    String name();

    @Component
    final class Api implements DiscordBot {
        @Inject
        DiscordApi _api;

        private User _user;

        @Override
        public String name() {
            return user().getName();
        }

        @Override
        public String userId() {
            return user().getIdAsString();
        }

        private User user() {
            if (_user == null) {
                synchronized (this) {
                    _user = _api.getYourself();
                }
            }
            return _user;
        }
    }

    String userId();
}
