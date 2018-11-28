package io.github.codejanovic.discord.bot.listener;

import io.github.codejanovic.discord.bot.entities.DiscordUser;
import io.github.codejanovic.discord.bot.listener.defaults.MessageCreatedListener;
import io.github.codejanovic.discord.bot.listener.interests.MessageCreatedInterest;
import io.github.codejanovic.discord.bot.repository.UsersRepository;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jusecase.inject.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Predicate;

@Component
public class CreateProfileListener extends MessageCreatedListener {

    @Inject
    UsersRepository _usersRepository;

    @Override
    protected void onReceivedMessageAnywhere(final MessageCreateEvent event, final MessageAuthor author, final User authorAsUser, final Server server, final Message message, final List<MessageAttachment> messageAttachments) {
        _usersRepository.persist(new DiscordUser.Mutable().withDiscordUser(authorAsUser).build());
    }

    @Override
    protected Predicate<MessageCreateEvent> messageFilter() {
        return new MessageCreatedInterest.Command("!profile create");
    }
}
