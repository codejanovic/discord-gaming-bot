package io.github.codejanovic.discord.bot.listener;

import io.github.codejanovic.discord.bot.entities.Account;
import io.github.codejanovic.discord.bot.entities.AccountProvider;
import io.github.codejanovic.discord.bot.entities.DiscordUser;
import io.github.codejanovic.discord.bot.listener.defaults.MessageCreatedListener;
import io.github.codejanovic.discord.bot.repository.AccountProviderRepository;
import io.github.codejanovic.discord.bot.repository.AccountRepository;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class ShowProfileListener extends MessageCreatedListener {

    @Inject
    AccountRepository _accountRepository;
    @Inject
    AccountProviderRepository _accountProviderRepository;

    @Override
    protected void onReceivedMessageAnywhere(final MessageCreateEvent event, final MessageAuthor author, final User authorAsUser, final Optional<Server> server, final Message message, final List<MessageAttachment> messageAttachments) {
        final List<User> mentionedUsers = event.getMessage().getMentionedUsers().stream().filter(u -> !u.isYourself()).collect(Collectors.toList());
        final DiscordUser profileOf = mentionedUsers.isEmpty() ? new DiscordUser.Mutable().withDiscordUser(authorAsUser).build() : new DiscordUser.Mutable().withDiscordUser(mentionedUsers.get(0)).build();
        final Collection<Account> accountsToShow = _accountRepository.getBy(profileOf);

        if (accountsToShow.isEmpty()) {
            event.getChannel().sendMessage(String.format("No profile found for <@%s>", profileOf.discordUserId()));
            return;
        }

        final Map<String, AccountProvider> providers = _accountProviderRepository.getAll().stream().collect(Collectors.toMap(AccountProvider::id, Function.identity()));

        final EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription(String.format("Gaming Profile of <@%s>", profileOf.discordUserId()));
        builder.setColor(Color.green);
        builder.setFooter("made by tibbot.org");
        for (Account account : accountsToShow) {
            builder.addField(providers.get(account.providerId()).name(), account.accountId());
        }
        event.getChannel().sendMessage(builder);
    }

    @Override
    protected Predicate<MessageCreateEvent> messageFilter() {
        return _interest.isDirectMessage().and(_interest.isCommand("show profile"))
                .or(_interest.isTalkingToMe().and(_interest.isCommand("show profile")));
    }
}
