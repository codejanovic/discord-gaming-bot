package io.github.codejanovic.discord.bot.repository.firestore;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import io.github.codejanovic.discord.bot.entities.Account;
import io.github.codejanovic.discord.bot.entities.DiscordUser;
import io.github.codejanovic.discord.bot.logging.PropertyMessageBuilder;
import io.github.codejanovic.discord.bot.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jusecase.inject.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class FirestoreAccountRepository implements AccountRepository {
    private static final Logger _log = LogManager.getLogger(FirestoreAccountRepository.class);
    @Inject
    Firestore _firestore;
    @Inject
    FirestoreDocuments _firestoreDocuments;

    @Override
    public boolean persist(final Account account) {
        try {
            final DocumentSnapshot accountDocument = accounts().document(account.id()).get().get();
            if (accountDocument.exists()) {
                accounts().document(account.id()).update(_firestoreDocuments.account().toDocument(account)).get();
                return true;
            }
            accounts().document(account.id()).create(_firestoreDocuments.account().toDocument(account)).get();
            return true;
        } catch (Exception e) {
            _log.fatal(new PropertyMessageBuilder(this).withError(e).withMessage("unable to persist account").build());
            return false;
        }
    }

    @Override
    public Collection<Account> getBy(final DiscordUser user) {
        try {
            return accounts().whereEqualTo("userId", user.discordUserName()).get().get().getDocuments()
                    .stream()
                    .map(q -> _firestoreDocuments.account().toEntity(q.getData()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            _log.fatal(new PropertyMessageBuilder(this).withError(e).withMessage("unable to query accounts").build());
            return Collections.emptyList();
        }
    }

    @Override
    public boolean delete(final DiscordUser user) {
        try {
            final Collection<Account> registeredAccounts = getBy(user);
            for (Account registeredAccount : registeredAccounts) {
                accounts().document(registeredAccount.id()).delete().get();
            }
            return true;
        } catch (Exception e) {
            _log.fatal(new PropertyMessageBuilder(this).withError(e).withMessage("unable to delete all accounts for user " + user.discordUserName()).build());
            return false;
        }
    }

    private CollectionReference accounts() {
        return _firestore.collection("accounts");
    }
}
