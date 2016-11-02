package com.hearthintellect.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration for the {@code core} module
 */
@Configuration
@Import(SpringMongoConfig.class)
public class SpringCoreConfig {

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }

}
