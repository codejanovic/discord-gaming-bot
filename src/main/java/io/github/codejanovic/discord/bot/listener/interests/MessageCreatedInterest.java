package io.github.codejanovic.discord.bot.listener.interests;

import io.github.codejanovic.discord.bot.DiscordBot;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.function.Predicate;

public interface MessageCreatedInterest extends Predicate<MessageCreateEvent> {

    final class Any implements MessageCreatedInterest {

        @Override
        public boolean test(final MessageCreateEvent event) {
            return true;
        }
    }

    final class IsMentioningMe implements MessageCreatedInterest {

        @Override
        public boolean test(final MessageCreateEvent event) {
            return event.getMessage().getMentionedUsers().stream().anyMatch(User::isYourself);
        }
    }

    final class IsTalkingToMe implements MessageCreatedInterest {

        private final DiscordBot _bot;

        public IsTalkingToMe(final DiscordBot discordBot) {
            _bot = discordBot;
        }

        @Override
        public boolean test(final MessageCreateEvent event) {
            return event.getMessage().getContent().startsWith(String.format("<@!%s>", _bot.userId()));
        }
    }

    final class IsDirectMessage implements MessageCreatedInterest {

        @Override
        public boolean test(final MessageCreateEvent event) {
            return event.isPrivateMessage();
        }
    }

    final class Command implements MessageCreatedInterest {
        private final String _value;
        private final DiscordBot _discordBot;

        public Command(final String value, final DiscordBot discordBot) {
            _value = value;
            _discordBot = discordBot;
        }

        @Override
        public boolean test(final MessageCreateEvent event) {
            return event.getMessage().getContent()
                    .replace(String.format("<@!%s>", _discordBot.userId()), "")
                    .trim()
                    .startsWith(_value);
        }
    }
}
