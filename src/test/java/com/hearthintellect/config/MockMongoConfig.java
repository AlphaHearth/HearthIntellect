package com.hearthintellect.config;

import com.hearthintellect.dao.CardRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

/**
 * Spring configuration classes for test cases of Controller classes.
 */
@Configuration
public class MockMongoConfig {

    @Bean
    public CardRepository cardRepository() {
        return mock(CardRepository.class);
    }

}
