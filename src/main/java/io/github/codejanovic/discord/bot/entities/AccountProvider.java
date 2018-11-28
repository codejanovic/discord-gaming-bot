package io.github.codejanovic.discord.bot.entities;

public interface AccountProvider {
    String name();

    String command();

    final class Of implements AccountProvider {
        private final String _name;
        private final String _command;

        public Of(final String name, final String command) {
            _name = name;
            _command = command;
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
}
