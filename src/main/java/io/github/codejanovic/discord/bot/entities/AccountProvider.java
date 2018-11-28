package io.github.codejanovic.discord.bot.entities;

import io.github.codejanovic.discord.bot.common.Builder;

import static java.util.Objects.requireNonNull;

public interface AccountProvider {
    String id();
    String name();

    String command();

    default String asCommandHelp() {
        return String.format("%s (type: %s)", name(), command());
    }

    final class Of implements AccountProvider {
        private final String _name;
        private final String _command;
        private final String _id;

        public Of(final String id, final String name, final String command) {
            _id = requireNonNull(id);
            _name = requireNonNull(name);
            _command = requireNonNull(command);
        }

        @Override
        public String id() {
            return _id;
        }

        @Override
        public String name() {
            return _name;
        }

        @Override
        public String command() {
            return _command;
        }
    }

    final class Mutable implements Builder<AccountProvider> {
        private String _name;
        private String _command;
        private String _id;

        public Mutable withName(final String name) {
            _name = requireNonNull(name);
            return this;
        }

        public Mutable withCommand(final String command) {
            _command = requireNonNull(command);
            return this;
        }

        public Mutable withId(final String id) {
            _id = requireNonNull(id);
            return this;
        }

        @Override
        public AccountProvider build() {
            return new AccountProvider.Of(_id, _name, _command);
        }
    }
}
