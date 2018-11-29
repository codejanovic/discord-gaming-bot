package io.github.codejanovic.discord.bot.listener;

import io.github.codejanovic.discord.bot.DiscordBot;
import io.github.codejanovic.discord.bot.entities.DiscordUser;
import io.github.codejanovic.discord.bot.listener.defaults.MessageCreatedListener;
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
public class CreateProfileListener extends MessageCreatedListener {

    @Inject
    UsersRepository _usersRepository;
    @Inject
    DiscordBot _bot;

    @Override
    protected void onReceivedMessageAnywhere(final MessageCreateEvent event, final MessageAuthor author, final User authorAsUser, final Optional<Server> server, final Message message, final List<MessageAttachment> messageAttachments) {
        if (_usersRepository.persist(new DiscordUser.Mutable().withDiscordUser(authorAsUser).build())) {
            final EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription("Next Steps are:");
            builder.setColor(Color.green);
            builder.setFooter("made by tibbot.org");
            builder.addField("Tell me anywhere to add your accounts ", String.format("@%s add account ", _bot.name()));
            builder.addField(".. or Direct Message me to add your accounts ", "add account ");
            event.getChannel().sendMessage("Your profile has been successfully upserted!", builder);
        } else {
            final EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Oops, something went horribly wrong!");
            builder.setDescription("Stay tuned, our developers are informed!");
            builder.setColor(Color.red);
            builder.setFooter("made by tibbot.org");
            event.getChannel().sendMessage(builder);
        }
    }

    @Override
    protected Predicate<MessageCreateEvent> messageFilter() {
        return _interest.isDirectMessage().and(_interest.isCommand("create profile"))
                .or(_interest.isTalkingToMe().and(_interest.isCommand("create profile")));
    }
}
