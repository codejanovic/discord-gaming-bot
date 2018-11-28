package io.github.codejanovic.discord.bot.logging;

import org.apache.logging.log4j.message.Message;
import org.javacord.api.entity.activity.Activity;
import org.javacord.api.entity.user.User;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PropertyMessageBuilder {

    private final List<Property> _properties = new ArrayList<>();
    private Property _message;
    private Property _error;
    private Property _errorReason;
    private Property _time;

    public PropertyMessageBuilder(final Object callee) {
        _properties.add(new Property.Of("class", callee.getClass().getSimpleName()));
    }

    public PropertyMessageBuilder withCustom(final String key, final String value) {
        _properties.add(new Property.Of(key, value));
        return this;
    }

    public PropertyMessageBuilder withProperty(final String property, final String value) {
        _properties.add(new Property.Of("property", property));
        _properties.add(new Property.Of("propertyValue", value));
        return this;
    }


    public PropertyMessageBuilder withMessage(final String value) {
        _message = new Property.Of("message", value);
        return this;
    }

    public PropertyMessageBuilder withTime(final Instant start) {
        _time = new Property.Of("executionTime", Duration.between(start, Instant.now()).toMillis() + "ms");
        return this;
    }

    public PropertyMessageBuilder withError(final Exception error) {
        _error = new Property.Of("error", error.getMessage());
        if (error.getCause() != null) {
            _errorReason = new Property.Of("errorReason", error.getCause().getMessage());
        }
        return this;
    }

    public Message build() {
        return new PropertyMessage(_properties, _message, _error, _errorReason, _time);
    }

    public PropertyMessageBuilder withUser(final User user) {
        _properties.add(new Property.Of("userId", user.getDiscriminatedName()));
        _properties.add(new Property.Of("userName", user.getName()));
        return this;
    }

    public PropertyMessageBuilder withActivity(final Activity activity) {
        _properties.add(new Property.Of("activityType", activity.getType().toString()));
        _properties.add(new Property.Of("activityName", activity.getName()));
        return this;
    }
}
