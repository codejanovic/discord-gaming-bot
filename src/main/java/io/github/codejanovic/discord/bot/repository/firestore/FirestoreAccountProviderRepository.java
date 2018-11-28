package io.github.codejanovic.discord.bot.repository.firestore;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import io.github.codejanovic.discord.bot.entities.AccountProvider;
import io.github.codejanovic.discord.bot.logging.PropertyMessageBuilder;
import io.github.codejanovic.discord.bot.repository.AccountProviderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Collection;
import java.util.stream.Collectors;

public class FirestoreAccountProviderRepository implements AccountProviderRepository {
    private static final Logger _log = LogManager.getLogger(FirestoreAccountProviderRepository.class);
    @Inject
    Firestore _firestore;
    @Inject
    FirestoreDocuments _firestoreDocuments;

    @Override
    public Collection<AccountProvider> getAll() {
        try {
            return providers().get().get().getDocuments()
                    .stream()
                    .map(query -> _firestoreDocuments.accountProvider().toEntity(query.getData()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            _log.fatal(new PropertyMessageBuilder(this).withError(e).withMessage("unable to query providers"));
        }
        return null;
    }

    private CollectionReference providers() {
        return _firestore.collection("providers");
    }
}
