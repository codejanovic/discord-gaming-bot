package io.github.codejanovic.discord.bot.listener;

import io.github.codejanovic.discord.bot.entities.AccountProvider;
import io.github.codejanovic.discord.bot.listener.defaults.MessageCreatedListener;
import io.github.codejanovic.discord.bot.listener.interests.MessageCreatedInterest;
import io.github.codejanovic.discord.bot.repository.AccountProviderRepository;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jusecase.inject.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

@Component
public class CreateAccountListener extends MessageCreatedListener {

    @Inject
    AccountProviderRepository _accountProviderRepository;

    @Override
    protected void onReceivedMessageAnywhere(final MessageCreateEvent event, final MessageAuthor author, final User authorAsUser, final Server server, final Message message, final List<MessageAttachment> messageAttachments) {
        final String[] commandParams = event.getMessage().getReadableContent().replace("!profile add account", "").trim().split(" ");
        if (commandParams.length != 2) {
            event.getChannel().sendMessage("Please provide an account-provider (eg Steam) and an account-id (eg yourSteamId) for adding an account.");
            return;
        }

        final String provider = commandParams[0];
        final String account = commandParams[1];

        final Collection<AccountProvider> providers = _accountProviderRepository.getAll();
        if (providers.stream().noneMatch(p -> p.command().equalsIgnoreCase(provider))) {
            event.getChannel().sendMessage("Please provide a valid account-provider as first parameter. Supported providers are:");
            EmbedBuilder supportedProviders = new EmbedBuilder();
            for (AccountProvider p : providers) {
                supportedProviders.addField(p.name(), String.format("type: !profile add account %s %s", p.command(), "myAccountId"));
            }
            event.getChannel().sendMessage(supportedProviders);
            return;
        }


    }

    @Override
    protected Predicate<MessageCreateEvent> messageFilter() {
        return new MessageCreatedInterest.Command("!profile add account");
    }
}
