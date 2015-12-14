package com.hearthintellect.config;

import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.dao.MechanicRepository;
import com.hearthintellect.dao.mongo.CardRepositoryImpl;
import com.hearthintellect.dao.mongo.MechanicRepositoryImpl;
import com.hearthintellect.morphia.converters.EnumOrdinalConverter;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.converters.EnumConverter;
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

        morphia.getMapper().getConverters().removeConverter(new EnumConverter());
        morphia.getMapper().getConverters().addConverter(new EnumOrdinalConverter());

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
        CardRepository repo = new CardRepositoryImpl();
        repo.setDatastore(datastore());

        return repo;
    }

    @Bean
    public MechanicRepository mechanicRepository() {
        MechanicRepository repo = new MechanicRepositoryImpl();
        repo.setDatastore(datastore());

        return repo;
    }

}
