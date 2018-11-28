package io.github.codejanovic.discord.bot.listener;

import io.github.codejanovic.discord.bot.listener.defaults.MessageCreatedListener;
import io.github.codejanovic.discord.bot.listener.interests.MessageCreatedInterest;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.List;
import java.util.function.Predicate;

public class CreateAccountListener extends MessageCreatedListener {
    @Override
    protected void onReceivedMessageAnywhere(final MessageCreateEvent event, final MessageAuthor author, final User authorAsUser, final Server server, final Message message, final List<MessageAttachment> messageAttachments) {

    }

    @Override
    protected Predicate<MessageCreateEvent> messageFilter() {
        return new MessageCreatedInterest.Command("!profile add account");
    }
}
