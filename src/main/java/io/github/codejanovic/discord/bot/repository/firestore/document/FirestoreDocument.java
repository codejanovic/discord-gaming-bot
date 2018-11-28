package io.github.codejanovic.discord.bot.repository.firestore.document;

import com.google.cloud.firestore.DocumentSnapshot;

import java.util.Map;
import java.util.Optional;

public interface FirestoreDocument<T> {
    Map<String, Object> toDocument(T entity);

    Optional<T> toEntity(final DocumentSnapshot snapshot);
}
