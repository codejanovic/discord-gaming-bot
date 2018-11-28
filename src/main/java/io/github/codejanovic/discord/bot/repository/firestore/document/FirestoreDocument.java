package io.github.codejanovic.discord.bot.repository.firestore.document;

import com.google.cloud.firestore.DocumentSnapshot;

import java.util.Map;
import java.util.Optional;

public interface FirestoreDocument<T> {
    Map<String, Object> toDocument(T entity);

    T toEntity(final Map<String, Object> fields);

    default Optional<T> toEntity(final DocumentSnapshot snapshot) {
        if (!snapshot.exists()) {
            return Optional.empty();
        }

        return Optional.of(toEntity(snapshot.getData()));
    }
}
