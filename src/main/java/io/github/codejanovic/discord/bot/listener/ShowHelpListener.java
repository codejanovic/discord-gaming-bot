package io.github.codejanovic.discord.bot.listener;

import io.github.codejanovic.discord.bot.DiscordBot;
import io.github.codejanovic.discord.bot.listener.defaults.MessageCreatedListener;
import io.github.codejanovic.discord.bot.listener.interests.MessageInterestFactory;
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
public class ShowHelpListener extends MessageCreatedListener {

    @Inject
    MessageInterestFactory _interest;
    @Inject
    DiscordBot _bot;


    @Override
    protected void onReceivedMessageAnywhere(final MessageCreateEvent event, final MessageAuthor author, final User authorAsUser, final Optional<Server> server, final Message message, final List<MessageAttachment> messageAttachments) {
        final EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Discord Gaming Bot Manual");
        builder.setColor(Color.yellow);
        builder.setFooter("made by tibbot.org");
        builder.addField("Create a profile", String.format("@%s create profile (or direct message me)", _bot.name()));
        builder.addField("Show your profile", String.format("@%s show profile (or direct message me)", _bot.name()));
        builder.addField("Show profile of a friend", String.format("@%s show profile @user (or direct message me)", _bot.name()));
        builder.addField("Add an account (like Steam, Origin or whatsoever)", String.format("@%s add account [provider] [account-id] (or direct message me)", _bot.name()));


        event.getChannel().sendMessage("Heres the manual:", builder);
    }

    @Override
    protected Predicate<MessageCreateEvent> provideEventFilter() {
        return _interest.isOfInterest("help").or(_interest.isOfInterest("rtfm"));
    }
}
