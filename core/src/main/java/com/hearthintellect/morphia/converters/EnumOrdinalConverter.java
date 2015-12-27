package com.hearthintellect.morphia.converters;

import org.mongodb.morphia.converters.EnumConverter;
import org.mongodb.morphia.mapping.MappedField;

/**
 * Converter used to save {@link Enum} into MongoDB as Int32
 */
public class EnumOrdinalConverter extends EnumConverter {

    @Override
    @SuppressWarnings("unchecked")
    public Object decode(final Class targetClass, final Object fromDBObject, final MappedField optionalExtraInfo) {
        if (fromDBObject == null) {
            return null;
        }

        int ordinal;

        if (fromDBObject instanceof Integer) {
            ordinal = (Integer) fromDBObject;
        } else if (fromDBObject instanceof Number) {
            ordinal = ((Number) fromDBObject).intValue();
        } else {
            ordinal = Integer.parseInt(fromDBObject.toString());
        }

        return targetClass.getEnumConstants()[ordinal];
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }

        return ((Enum) value).ordinal();
    }

}
