package io.github.codejanovic.discord.bot.repository.firestore;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import io.github.codejanovic.discord.bot.entities.DiscordUser;
import io.github.codejanovic.discord.bot.logging.PropertyMessageBuilder;
import io.github.codejanovic.discord.bot.repository.UsersRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jusecase.inject.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Component
public class FirestoreUsersRepository implements UsersRepository {

    private static final Logger _log = LogManager.getLogger(FirestoreUsersRepository.class);
    @Inject
    Firestore _firestore;
    @Inject
    FirestoreDocuments _firestoreDocuments;

    @Override
    public void persist(final DiscordUser discordUser) {
        try {
            final DocumentSnapshot userDocument = users().document(discordUser.discordUserName()).get().get();
            if (userDocument.exists()) {
                users().document(discordUser.discordUserName()).update(_firestoreDocuments.discordUser().toDocument(discordUser)).get();
                return;
            }

            users().document(discordUser.discordUserName()).create(_firestoreDocuments.discordUser().toDocument(discordUser)).get();
        } catch (Exception e) {
            _log.fatal(new PropertyMessageBuilder(this).withError(e).withMessage("unable to persist user").build());
        }
    }

    @Override
    public Optional<DiscordUser> getBy(final DiscordUser discordUser) {
        try {
            final DocumentSnapshot userDocument = users().document(discordUser.discordUserName()).get().get();
            return _firestoreDocuments.discordUser().toEntity(userDocument);
        } catch (Exception e) {
            _log.fatal(new PropertyMessageBuilder(this).withError(e).withMessage("unable to persist user").build());
            return Optional.empty();
        }
    }

    private CollectionReference users() {
        return _firestore.collection("users");
    }

    private void test() {
        try {
            final DocumentSnapshot user = _firestore.collection("users").document("dammi#6677").get().get();
            final List<QueryDocumentSnapshot> accounts = _firestore.collection("accounts").whereEqualTo("userId", "dammi#6677").get().get().getDocuments();
            final List<QueryDocumentSnapshot> providers = _firestore.collection("providers").get().get().getDocuments();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
