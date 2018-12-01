package io.github.codejanovic.discord.bot.listener;

import io.github.codejanovic.discord.bot.DiscordBot;
import io.github.codejanovic.discord.bot.entities.Account;
import io.github.codejanovic.discord.bot.entities.AccountProvider;
import io.github.codejanovic.discord.bot.entities.DiscordUser;
import io.github.codejanovic.discord.bot.listener.defaults.MessageCreatedListener;
import io.github.codejanovic.discord.bot.listener.interests.MessageInterestFactory;
import io.github.codejanovic.discord.bot.repository.AccountProviderRepository;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class AddAccountListener extends MessageCreatedListener {

    @Inject
    MessageInterestFactory _interest;
    @Inject
    AccountProviderRepository _accountProviderRepository;
    @Inject
    AccountRepository _accountRepository;
    @Inject
    UsersRepository _usersRepository;
    @Inject
    DiscordBot _bot;

    @Override
    protected void onReceivedMessageAnywhere(final MessageCreateEvent event, final MessageAuthor author, final User authorAsUser, final Optional<Server> server, final Message message, final List<MessageAttachment> messageAttachments) {
        final String[] commandParams = event.getMessage()
                .getContent()
                .replace(String.format("<@!%s>", _bot.userId()), "")
                .replace("add account", "").trim().split(" ");
        if (commandParams.length != 2) {
            event.getChannel().sendMessage("Please provide an account-provider (eg Steam) and an account-id (eg yourSteamId) for adding an account.");
            return;
        }

        final String provider = commandParams[0];
        final String account = commandParams[1];

        final Collection<AccountProvider> providers = _accountProviderRepository.getAll();
        final Optional<AccountProvider> matchingProvider = providers.stream().filter(p -> p.command().equalsIgnoreCase(provider)).findFirst();
        if (!matchingProvider.isPresent()) {
            event.getChannel().sendMessage("Please provide a valid account-provider as first parameter. Supported providers are:");
            EmbedBuilder supportedProviders = new EmbedBuilder();
            for (AccountProvider p : providers) {
                supportedProviders.addField(p.name(), String.format("type: !profile add account %s %s", p.command(), "myAccountId"));
            }
            event.getChannel().sendMessage(supportedProviders);
            return;
        }


        if (_usersRepository.persist(new DiscordUser.Mutable().withDiscordUser(authorAsUser).build()) && _accountRepository.persist(new Account.Mutable()
                .withProviderId(matchingProvider.get().id())
                .withAccountId(account)
                .withUserId(new DiscordUser.Mutable().withDiscordUser(authorAsUser).build().discordUserName())
                .build())) {
            final EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription("Next Steps are:");
            builder.setColor(Color.green);
            builder.setFooter("made by tibbot.org");
            builder.addField("Tell me anywhere to show your or a friends profile", String.format("@%s show profile @mention", _bot.name()));
            builder.addField(".. or Direct Message me to show your or a friends profile ", "show profile @mention");
            event.getChannel().sendMessage("Your account has been successfully upserted!", builder);
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
    protected Predicate<MessageCreateEvent> provideEventFilter() {
        return _interest.isOfInterest("add account");
    }
}
