package com.hearthintellect.config;

import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.dao.mongo.CardRepositoryImpl;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class for Morphia
 */
@Configuration
public class SpringMongoConfig {

    /** Name of the database */
    protected String databaseName() {
        return "hearthstone";
    }

    /** Name of package where the mapping classes are */
    protected String packageName() {
        return "com.hearthintellect.model";
    }

    @Bean
    public Morphia morphia() {
        Morphia morphia = new Morphia();
        morphia.mapPackage(packageName());

        return morphia;
    }

    @Bean
    public Datastore datastore() {
        Datastore datastore = morphia().createDatastore(new MongoClient(), databaseName());
        datastore.ensureIndexes();

        return datastore;
    }

    @Bean
    public CardRepository cardRepository() {
        return new CardRepositoryImpl(datastore());
    }

}
