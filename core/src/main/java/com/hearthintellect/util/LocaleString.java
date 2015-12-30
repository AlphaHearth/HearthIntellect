package com.hearthintellect.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Mappings from {@link Locale} to the corresponding content in {@link String},
 * implemented by a {@link HashMap}
 */
public class LocaleString extends HashMap<Locale, String> {
    public LocaleString() {
        super();
    }

    public LocaleString(Map<? extends Locale, String> map) {
        super(map);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other instanceof LocaleString) {
            LocaleString otherLocales = (LocaleString) other;
            if (size() != otherLocales.size())
                return false;

            for (Map.Entry<Locale, String> entry : entrySet())
                if (!entry.getValue().equals(otherLocales.get(entry.getKey())))
                    return false;

            return true;
        }
        return false;
    }
}
