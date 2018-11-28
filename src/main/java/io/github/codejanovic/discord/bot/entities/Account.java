package io.github.codejanovic.discord.bot.entities;

import java.util.Objects;

public interface Account {
    String id();

    String providerId();

    String userId();

    final class Of implements Account {
        private final String _id;
        private final String _providerId;
        private final String _userId;

        public Of(final String id, final String providerId, final String userId) {
            _id = id;
            _providerId = providerId;
            _userId = userId;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Of of = (Of) o;
            return Objects.equals(_id, of._id) &&
                    Objects.equals(_providerId, of._providerId) &&
                    Objects.equals(_userId, of._userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(_id, _providerId, _userId);
        }

        @Override
        public String id() {
            return _id;
        }

        @Override
        public String providerId() {
            return _providerId;
        }

        @Override
        public String userId() {
            return _userId;
        }
    }
}
