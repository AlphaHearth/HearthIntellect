package com.hearthintellect.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hearthintellect.json.LocalDateTimeTypeAdapter;
import com.hearthintellect.json.LocalDateTypeAdapter;
import com.hearthintellect.json.LocaleStringTypeAdapter;
import com.hearthintellect.utils.LocaleString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Spring configuration for the {@code core} module
 */
@Configuration
public class SpringCoreConfig {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocaleString.class, new LocaleStringTypeAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();
    }

}
