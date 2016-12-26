package com.hearthintellect;

import com.google.gson.Gson;
import com.hearthintellect.config.SpringWebConfig;
import com.hearthintellect.utils.LocaleString;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit test cases for the {@link Gson} instance used by whole system.
 */
public class GsonTest {

    private final Gson gson = new SpringWebConfig().gson();

    @Test
    public void testLocalDateConversion() {
        LocalDate testLocalDate = LocalDate.of(2000, 3, 12);
        String serializedLocalDate = gson.toJson(testLocalDate);
        assertThat(serializedLocalDate, is("\"2000-03-12\""));
        LocalDate deserializedLocalDate = gson.fromJson(serializedLocalDate, LocalDate.class);
        assertThat(deserializedLocalDate, is(testLocalDate));
    }

    @Test
    public void testLocalDateTimeConversion() {
        LocalDateTime testLocalDateTime = LocalDateTime.of(2000, 3, 12, 15, 23, 41);
        String serializedLocalDateTime = gson.toJson(testLocalDateTime);
        assertThat(serializedLocalDateTime, is("\"2000-03-12T15:23:41\""));
        LocalDateTime deserializedLocalDateTime = gson.fromJson(serializedLocalDateTime, LocalDateTime.class);
        assertThat(deserializedLocalDateTime, is(testLocalDateTime));
    }

    @Test
    public void testLocaleStringConversion() {
        LocaleString testLocaleString = new LocaleString();
        testLocaleString.put(Locale.US, "This is a test locale string.");
        testLocaleString.put(Locale.CHINA, "这是一个用来测试的 LocaleString。");

        String serializedLocaleString = gson.toJson(testLocaleString);
        System.out.println(serializedLocaleString);
        LocaleString deserializedLocaleString = gson.fromJson(serializedLocaleString, LocaleString.class);
        assertThat(deserializedLocaleString, is(testLocaleString));
    }
}
