package io.github.codejanovic.discord.bot.entities;

import io.github.codejanovic.discord.bot.common.Builder;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public interface Account {
    String id();

    String providerId();

    String userId();

    final class Mutable implements Builder<Account> {
        private String _providerId;
        private String _userId;

        public Mutable withProviderId(final String providerId) {
            _providerId = requireNonNull(providerId);
            return this;
        }

        public Mutable withUserId(final String userId) {
            _userId = requireNonNull(userId);
            return this;
        }

        @Override
        public Account build() {
            return new Account.Of(_providerId, _userId);
        }
    }
    final class Of implements Account {
        private final String _id;
        private final String _providerId;
        private final String _userId;

        public Of(final String providerId, final String userId) {
            _id = userId + "_" + providerId;
            _providerId = requireNonNull(providerId);
            _userId = requireNonNull(userId);
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
