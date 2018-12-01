package io.github.codejanovic.discord.bot.listener.defaults;

import org.javacord.api.entity.channel.*;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class MessageCreatedListener implements MessageCreateListener {

    private Predicate<MessageCreateEvent> _eventFilter;

    @Override
    public final void onMessageCreate(final MessageCreateEvent event) {
        if (_eventFilter == null) {
            synchronized (this) {
                _eventFilter = provideEventFilter();
            }
        }

        if (!_eventFilter.test(event)) {
            return;
        }
        onReceivedMessageAnywhere(event, event.getMessageAuthor(), event.getMessageAuthor().asUser().get(), event.getServer(), event.getMessage(), event.getMessageAttachments());
        switch (event.getChannel().getType()) {
            case SERVER_TEXT_CHANNEL:
                onReceivedMessageInTextChannel(event.getChannel().asServerTextChannel().get(), event.getMessageAuthor(), event.getMessageAuthor().asUser().get(), event.getServer().get(), event.getMessage(), event.getMessageAttachments());
                break;
            case PRIVATE_CHANNEL:
                onReceivedPrivateMessage(event.getChannel().asPrivateChannel().get(), event.getMessageAuthor(), event.getMessageAuthor().asUser().get(), event.getMessage(), event.getMessageAttachments());
                break;
            case SERVER_VOICE_CHANNEL:
                onReceivedMessageInVoiceChannel(event.getChannel().asServerVoiceChannel().get(), event.getMessageAuthor(), event.getMessageAuthor().asUser().get(), event.getServer().get(), event.getMessage(), event.getMessageAttachments());
                break;
            case GROUP_CHANNEL:
                onReceivedMessageInGroupChannel(event.getChannel().asGroupChannel().get(), event.getMessageAuthor(), event.getMessageAuthor().asUser().get(), event.getServer().get(), event.getMessage(), event.getMessageAttachments());
                break;
            case CHANNEL_CATEGORY:
                onReceivedMessageInCategoryChannel(event.getChannel().asChannelCategory().get(), event.getMessageAuthor(), event.getMessageAuthor().asUser().get(), event.getServer().get(), event.getMessage(), event.getMessageAttachments());
                break;
            case UNKNOWN:
                break;
        }
    }

    protected abstract Predicate<MessageCreateEvent> provideEventFilter();

    protected void onReceivedMessageInCategoryChannel(final ChannelCategory channelCategory, final MessageAuthor author, final User authorAsUser, final Server server, final Message message, final List<MessageAttachment> messageAttachments) {

    }

    protected void onReceivedMessageInGroupChannel(final GroupChannel groupChannel, final MessageAuthor author, final User authorAsUser, final Server server, final Message message, final List<MessageAttachment> messageAttachments) {

    }

    protected void onReceivedMessageInVoiceChannel(final ServerVoiceChannel serverVoiceChannel, final MessageAuthor author, final User authorAsUser, final Server server, final Message message, final List<MessageAttachment> messageAttachments) {

    }

    protected void onReceivedMessageInTextChannel(final ServerTextChannel serverTextChannel, final MessageAuthor author, final User authorAsUser, final Server server, final Message message, final List<MessageAttachment> messageAttachments) {

    }

    protected void onReceivedMessageAnywhere(final MessageCreateEvent event, final MessageAuthor author, final User authorAsUser, final Optional<Server> server, final Message message, final List<MessageAttachment> messageAttachments) {

    }

    protected void onReceivedPrivateMessage(final PrivateChannel privateChannel, final MessageAuthor author, final User authorAsUser, final Message message, final List<MessageAttachment> messageAttachments) {

    }

}
