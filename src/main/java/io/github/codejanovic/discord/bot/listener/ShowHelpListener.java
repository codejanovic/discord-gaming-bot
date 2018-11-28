package io.github.codejanovic.discord.bot.listener;

import io.github.codejanovic.discord.bot.listener.defaults.MessageCreatedListener;
import io.github.codejanovic.discord.bot.listener.interests.MessageCreatedInterest;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jusecase.inject.Component;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class ShowHelpListener extends MessageCreatedListener {

    @Override
    protected void onReceivedMessageAnywhere(final MessageCreateEvent event, final MessageAuthor author, final User authorAsUser, final Optional<Server> server, final Message message, final List<MessageAttachment> messageAttachments) {
        final EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Discord Gaming Bot Manual");
        builder.setColor(Color.red);
        builder.setFooter("made by tibbot.org");
        builder.addField("Create a profile", "!profile create");
        builder.addField("Show your profile", "!profile show");
        builder.addField("Show profile of a friend", "!profile show @mention");
        builder.addField("Add an account (like Steam, Origin or whatsoever)", "!profile add account [provider] [account-id]");


        event.getChannel().sendMessage("Heres the manual:", builder);
    }

    @Override
    protected Predicate<MessageCreateEvent> messageFilter() {
        return new MessageCreatedInterest.Command("!rtfm")
                .or(new MessageCreatedInterest.Command("!help"));
    }
}
