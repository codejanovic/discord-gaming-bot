package io.github.codejanovic.discord.bot.repository.firestore;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import io.github.codejanovic.discord.bot.entities.Account;
import io.github.codejanovic.discord.bot.logging.PropertyMessageBuilder;
import io.github.codejanovic.discord.bot.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jusecase.inject.Component;

import javax.inject.Inject;

@Component
public class FirestoreAccountRepository implements AccountRepository {
    private static final Logger _log = LogManager.getLogger(FirestoreAccountRepository.class);
    @Inject
    Firestore _firestore;
    @Inject
    FirestoreDocuments _firestoreDocuments;

    @Override
    public void persist(final Account account) {
        try {
            final DocumentSnapshot accountDocument = accounts().document(account.id()).get().get();
            if (accountDocument.exists()) {
                accounts().document(account.id()).update(_firestoreDocuments.account().toDocument(account));
                return;
            }
            accounts().document(account.id()).create(_firestoreDocuments.account().toDocument(account));
        } catch (Exception e) {
            _log.fatal(new PropertyMessageBuilder(this).withError(e).withMessage("unable to persist account").build());
        }
    }

    private CollectionReference accounts() {
        return _firestore.collection("accounts");
    }
}
