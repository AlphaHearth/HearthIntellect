package com.hearthintellect.morphia.converters;

import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Converter used to save {@link ZonedDateTime} into MongoDB as String
 */
public class ZonedDateTimeConverter extends TypeConverter {
    private static final Logger LOG = LoggerFactory.getLogger(ZonedDateTimeConverter.class);

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    public ZonedDateTimeConverter() {
        super(String.class);
    }

    @Override
    public Object decode(Class<?> targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
        if (fromDBObject == null)
            return null;

        return ZonedDateTime.parse((String) fromDBObject, formatter);
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null)
            return null;

        ZonedDateTime dateTime = (ZonedDateTime) value;

        return dateTime.format(formatter);
    }
}
