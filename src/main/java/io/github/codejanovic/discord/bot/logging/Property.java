package io.github.codejanovic.discord.bot.logging;

public interface Property {
    String name();

    String value();

    final class Of implements Property {
        private final String _name;
        private final String _value;

        public Of(final String name, final String value) {
            _name = name;
            _value = value;
        }

        public Of(final String name, final Long value) {
            this(name, String.valueOf(value));
        }

        public Of(final String name, final Integer value) {
            this(name, String.valueOf(value));
        }

        public Of(final String name, final Double value) {
            this(name, String.valueOf(value));
        }

        @Override
        public String name() {
            return _name;
        }

        @Override
        public String value() {
            return _value;
        }
    }
}
