package com.hearthintellect.morphia.converters;

import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Converter used to save {@link LocalDateTime} into MongoDB as String
 */
public class LocalDateTimeConverter extends TypeConverter {
    private static final Logger LOG = LoggerFactory.getLogger(LocalDateTimeConverter.class);

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public LocalDateTimeConverter() {
        super(LocalDateTime.class);
    }

    @Override
    public Object decode(Class<?> targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
        if (fromDBObject == null)
            return null;

        return LocalDateTime.parse((String) fromDBObject, formatter);
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null)
            return null;

        LocalDateTime dateTime = (LocalDateTime) value;

        return dateTime.format(formatter);
    }
}
