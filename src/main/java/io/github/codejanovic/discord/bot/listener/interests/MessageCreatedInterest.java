package io.github.codejanovic.discord.bot.listener.interests;

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

    final class ForMe implements MessageCreatedInterest {

        @Override
        public boolean test(final MessageCreateEvent event) {
            return event.getMessage().getMentionedUsers().stream().anyMatch(User::isYourself);
        }
    }

    final class Command implements MessageCreatedInterest {
        private final String _value;

        public Command(final String value) {
            _value = value;
        }

        @Override
        public boolean test(final MessageCreateEvent event) {
            return event.getMessage().getReadableContent().startsWith(_value);
        }
    }
}
