package com.hearthintellect.utils;

public abstract class LocaleStringUtils {

    public static LocaleString merge(LocaleString original, LocaleString update) {
        if (update == null || update.isEmpty())
            return original;
        if (original == null)
            original = new LocaleString();
        original.putAll(update);
        return original;
    }

    private LocaleStringUtils() {
        throw new AssertionError("LocaleStringUtils should not be instantiated.");
    }
}
