package io.github.codejanovic.discord.bot.logging;

import org.apache.logging.log4j.message.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyMessage implements Message {

    private final List<Property> _properties;

    public PropertyMessage(final Collection<Property> properties, final Property message, final Property error, final Property errorReason, final Property time) {
        _properties = new ArrayList<>(properties);
        if (message != null) {
            _properties.add(message);
        }

        if (error != null) {
            _properties.add(error);
        }

        if (errorReason != null) {
            _properties.add(errorReason);
        }

        if (time != null) {
            _properties.add(time);
        }
    }

    @Override
    public String getFormattedMessage() {
        return _properties.stream().map(p -> "\"" + p.name() + "\"" + "=" + "\"" + p.value() + "\"").collect(Collectors.joining(", "));
    }

    @Override
    public String getFormat() {
        return "";
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }
}
