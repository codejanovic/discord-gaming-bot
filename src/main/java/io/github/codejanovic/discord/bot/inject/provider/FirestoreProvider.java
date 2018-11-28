package io.github.codejanovic.discord.bot.inject.provider;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import io.github.codejanovic.discord.bot.logging.PropertyMessageBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Provider;
import java.io.FileInputStream;

public class FirestoreProvider implements Provider<Firestore> {

    private static final Logger _log = LogManager.getLogger(FirestoreProvider.class);

    @Override
    public Firestore get() {
        try {
            final FileInputStream serviceAccount = new FileInputStream(System.getProperty("credentials", "firestore-credentials.json"));
            final FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://discord-gaming-bot.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
            return FirestoreClient.getFirestore();
        } catch (Exception e) {
            _log.fatal(new PropertyMessageBuilder(this).withError(e).withMessage("unable to connect to firestore").build());
            throw new IllegalArgumentException("unable to connect to firestore");
        }
    }
}
