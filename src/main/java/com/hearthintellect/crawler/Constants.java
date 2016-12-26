package com.hearthintellect.crawler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * Constants for Hearthstone crawlers. Do not instantiate this class.
 */
public class Constants {

    /** All patch numbers of Hearthstone */
    public static final int[] PATCHES = { 3140, 3388, 3604, 3645, 3664, 3749, 3890, 3937, 4217, 4217, 4243, 4442,
        4458, 4482, 4944, 4973, 5170, 5314, 5435, 5506, 5834, 6024, 6141, 6284, 6485, 6898, 7234, 7628, 7785, 7835,
        8036, 8108, 8311, 8416, 8834, 9166, 9554, 9786, 10357, 10604, 10784, 10833, 10956, 11461, 11959, 12051,
        12105, 12266, 12574, 13030, 13619, 13714, 13740, 13807, 13921, 14406, 14830, 15181, 15300, 15590 };

    /** All supported locales of Hearthstone */
    public static final Locale[] LOCALES = {
        new Locale("de", "DE"), new Locale("en", "US"), new Locale("es", "ES"), new Locale("ex", "MX"),
        new Locale("fr", "FR"), new Locale("it", "IT"), new Locale("ja", "JP"), new Locale("ko", "KR"),
        new Locale("pl", "PL"), new Locale("pt", "BR"), new Locale("ru", "RU"), new Locale("th", "TH"),
        new Locale("zh", "CN"), new Locale("zh", "TW")
    };

    public static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    public static final Path DATA_PATH = Paths.get("data");
    public static final Path HEARTHSTONE_JSON_PATH = DATA_PATH.resolve("HearthstoneJson");
    public static final Path ASSETS_PATH = DATA_PATH.resolve("assets");

    public static String hearthstoneJsonUrl(int patchNumber) {
        return String.format("https://api.hearthstonejson.com/v1/%d/all/cards.json", patchNumber);
    }

    /**
     * Returns the HearthHead Card URL for the given HearthHead ID.
     *
     * @param hearthheadId the given HearthHead Card ID
     * @return the respective HearthHead Card URL
     */
    public static String hearthheadCardUrl(String hearthheadId) {
        return String.format("http://www.hearthhead.com/cards/%s", hearthheadId);
    }

    public static String hearthheadCardSoundUrl(String cardSoundId, boolean isMp3, Locale locale) {
        String formatSuffix = isMp3 ? "mp3" : "ogg";
        return String.format("http://media.services.zam.com/v1/media/byName/hs/sounds/%s%s/%s.%s",
            locale.getLanguage(), locale.getCountry().toLowerCase(), cardSoundId, formatSuffix);
    }

    public static String hearthheadCardImageUrl(String cardImageId, ImageType imageType, Locale locale) {
        switch (imageType) {
            case Medium: return String.format("http://media.services.zam.com/v1/media/byName/hs/cards/%s%s/pal/%s.png",
                locale.getLanguage(), locale.getCountry().toLowerCase(), cardImageId);
            case Original: return String.format("http://media.services.zam.com/v1/media/byName/hs/cards/%s%s/%s.png",
                locale.getLanguage(), locale.getCountry().toLowerCase(), cardImageId);
            case Premium: return String.format("http://media.services.zam.com/v1/media/byName/hs/cards/%s%s/animated/%s_premium.gif",
                locale.getLanguage(), locale.getCountry().toLowerCase(), cardImageId);
        }
        // Will never get here
        return null;
    }

    public enum ImageType {
        /** Static 200*300 png */
        Medium,
        /** Static 300*450 png */
        Original,
        /** 300*450 golden gif */
        Premium
    }
}
