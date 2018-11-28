package io.github.codejanovic.discord.bot.listener.interests;

import io.github.codejanovic.discord.bot.DiscordBot;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jusecase.inject.Component;

import javax.inject.Inject;
import java.util.function.Predicate;

@Component
public final class MessageInterestFactory {
    @Inject
    DiscordBot _bot;

    public Predicate<MessageCreateEvent> any() {
        return new MessageCreatedInterest.Any();
    }

    public Predicate<MessageCreateEvent> isCommand(final String command) {
        return new MessageCreatedInterest.Command(command, _bot);
    }

    public Predicate<MessageCreateEvent> isTalkingToMe() {
        return new MessageCreatedInterest.IsTalkingToMe(_bot);
    }

    public Predicate<MessageCreateEvent> isMentioningMe() {
        return new MessageCreatedInterest.IsMentioningMe();
    }

    public Predicate<MessageCreateEvent> isDirectMessage() {
        return new MessageCreatedInterest.IsDirectMessage();
    }
}
