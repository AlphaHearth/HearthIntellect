package com.hearthintellect.config;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration class for unit test cases of DAO classes.
 */
@Configuration
@Import(SpringMongoConfig.class)
public class InMemoryMongoConfig {

    @Bean
    public MongoClient mongoClient(String mongoHost, Integer mongoPort, String mongoDatabase,
                                   String mongoUsername, String mongoPassword) {
        return new Fongo("In Memory Mongo").getMongo();
    }
}
