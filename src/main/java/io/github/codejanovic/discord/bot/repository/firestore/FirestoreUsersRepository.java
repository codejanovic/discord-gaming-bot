package io.github.codejanovic.discord.bot.repository.firestore;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import io.github.codejanovic.discord.bot.entities.DiscordUser;
import io.github.codejanovic.discord.bot.logging.PropertyMessageBuilder;
import io.github.codejanovic.discord.bot.repository.UsersRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jusecase.inject.Component;

import javax.inject.Inject;
import java.util.Optional;

@Component
public class FirestoreUsersRepository implements UsersRepository {

    private static final Logger _log = LogManager.getLogger(FirestoreUsersRepository.class);
    @Inject
    Firestore _firestore;
    @Inject
    FirestoreDocuments _firestoreDocuments;

    @Override
    public boolean persist(final DiscordUser discordUser) {
        try {
            final DocumentSnapshot userDocument = users().document(discordUser.discordUserName()).get().get();
            if (userDocument.exists()) {
                users().document(discordUser.discordUserName()).update(_firestoreDocuments.discordUser().toDocument(discordUser)).get();
                return true;
            }

            users().document(discordUser.discordUserName()).create(_firestoreDocuments.discordUser().toDocument(discordUser)).get();
            return true;
        } catch (Exception e) {
            _log.fatal(new PropertyMessageBuilder(this).withError(e).withMessage("unable to persist user").build());
            return false;
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

    @Override
    public boolean delete(final DiscordUser user) {
        try {
            final Optional<DiscordUser> registeredUser = getBy(user);

            if (registeredUser.isPresent()) {
                users().document(user.discordUserName()).delete().get();
            }
            return true;
        } catch (Exception e) {
            _log.fatal(new PropertyMessageBuilder(this).withError(e).withMessage("unable to delete profile for user " + user.discordUserName()).build());
            return false;
        }
    }

    private CollectionReference users() {
        return _firestore.collection("users");
    }
}
