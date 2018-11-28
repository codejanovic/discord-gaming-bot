package io.github.codejanovic.discord.bot.entities;

import io.github.codejanovic.discord.bot.common.Builder;
import org.javacord.api.entity.user.User;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public interface DiscordUser {
    String discordUserId();

    String discordUserName();

    final class Of implements DiscordUser {
        private final String _discordUserId;
        private final String _discordUserName;

        public Of(final String discordUserId, final String discordUserName) {
            _discordUserId = requireNonNull(discordUserId);
            _discordUserName = requireNonNull(discordUserName);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Of of = (Of) o;
            return Objects.equals(_discordUserId, of._discordUserId) &&
                    Objects.equals(_discordUserName, of._discordUserName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(_discordUserId, _discordUserName);
        }

        @Override
        public String discordUserId() {
            return _discordUserId;
        }

        @Override
        public String discordUserName() {
            return _discordUserName;
        }
    }

    final class Mutable implements Builder<DiscordUser> {

        private String _discordUserId;
        private String _discordUserName;

        public Mutable withDiscordUserId(final String discordUserId) {
            _discordUserId = requireNonNull(discordUserId);
            return this;
        }

        public Mutable withDiscordUserName(final String discordUserName) {
            _discordUserName = requireNonNull(discordUserName);
            return this;
        }

        public Mutable withDiscordUser(final User user) {
            withDiscordUserId(user.getId() + "");
            withDiscordUserName(user.getDiscriminatedName());
            return this;
        }


        @Override
        public DiscordUser build() {
            return new DiscordUser.Of(_discordUserId, _discordUserName);
        }
    }
}
