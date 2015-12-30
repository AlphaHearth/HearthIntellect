package com.hearthintellect.morphia.converters;

import com.hearthintellect.util.LocaleString;
import org.mongodb.morphia.converters.MapOfValuesConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.utils.IterHelper;
import org.mongodb.morphia.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Converter used to save {@link LocaleString} into MongoDB as JSON object
 */
public class LocaleStringConverter extends TypeConverter {
    private static final Logger LOG = LoggerFactory.getLogger(LocaleStringConverter.class);

    public LocaleStringConverter() {
        super(LocaleString.class);
    }

    @Override
    public Object decode(Class<?> targetClass, Object fromDBObject, MappedField mf) {
        if (fromDBObject == null) {
            return null;
        }

        final Map<String, String> map = (Map<String, String>) fromDBObject;
        if (!map.isEmpty() || getMapper().getOptions().isStoreEmpties()) {
            final LocaleString localeString = new LocaleString();
            for (final Map.Entry<String, String> entry : map.entrySet()) {
                localeString.put(new Locale(entry.getKey()), entry.getValue());
            }
            return localeString;
        }
        return null;
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }

        final Map<String, String> values = new HashMap<>();
        new IterHelper<Locale, String>().loopMap(value, new IterHelper.MapIterCallback<Locale, String>() {
            @Override
            public void eval(final Locale key, final String val) {
                values.put(key.getLanguage(), val);
            }
        });

        return values;
    }
}
