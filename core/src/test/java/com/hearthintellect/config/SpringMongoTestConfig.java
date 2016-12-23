package com.hearthintellect.config;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration for JUnit test cases of the {@code core} module
 */
@Configuration
@Import(SpringMongoConfig.class)
public class SpringMongoTestConfig {

    @Bean
    public MongoClient mongoClient() {
        return new Fongo("In Memory Mongo").getMongo();
    }

}
