package io.github.codejanovic.discord.bot.listener;

import io.github.codejanovic.discord.bot.entities.DiscordUser;
import io.github.codejanovic.discord.bot.listener.defaults.MessageCreatedListener;
import io.github.codejanovic.discord.bot.listener.interests.MessageInterestFactory;
import io.github.codejanovic.discord.bot.repository.AccountRepository;
import io.github.codejanovic.discord.bot.repository.UsersRepository;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jusecase.inject.Component;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class DeleteProfileListener extends MessageCreatedListener {

    @Inject
    MessageInterestFactory _interest;
    @Inject
    UsersRepository _usersRepository;
    @Inject
    AccountRepository _accountRepository;

    @Override
    protected void onReceivedMessageAnywhere(final MessageCreateEvent event, final MessageAuthor author, final User authorAsUser, final Optional<Server> server, final Message message, final List<MessageAttachment> messageAttachments) {
        if (!_accountRepository.delete(new DiscordUser.Mutable().withDiscordUser(authorAsUser).build())) {
            final EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Oops, something went horribly wrong!");
            builder.setDescription("Stay tuned, our developers are informed!");
            builder.setColor(Color.red);
            builder.setFooter("made by tibbot.org");
            event.getChannel().sendMessage(builder);
            return;
        }

        if (!_usersRepository.delete(new DiscordUser.Mutable().withDiscordUser(authorAsUser).build())) {
            final EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Oops, something went horribly wrong!");
            builder.setDescription("Stay tuned, our developers are informed!");
            builder.setColor(Color.red);
            builder.setFooter("made by tibbot.org");
            event.getChannel().sendMessage(builder);
            return;
        }

        event.getChannel().sendMessage(String.format("<@%s> Your profile has been deleted successfully!", authorAsUser.getId()));


    }

    @Override
    protected Predicate<MessageCreateEvent> provideEventFilter() {
        return _interest.isOfInterest("delete profile");
    }
}
