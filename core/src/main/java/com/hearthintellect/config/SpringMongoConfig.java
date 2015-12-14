package com.hearthintellect.config;

import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.dao.DeckRepository;
import com.hearthintellect.dao.MechanicRepository;
import com.hearthintellect.dao.UserRepository;
import com.hearthintellect.dao.mongo.CardRepositoryImpl;
import com.hearthintellect.dao.mongo.DeckRepositoryImpl;
import com.hearthintellect.dao.mongo.MechanicRepositoryImpl;
import com.hearthintellect.dao.mongo.UserRepositoryImpl;
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
    @Bean
    public String databaseName() {
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
    public MongoClient mongoClient() {
        return new MongoClient();
    }

    @Bean
    public Datastore datastore() {
        Datastore datastore = morphia().createDatastore(mongoClient(), databaseName());
        datastore.ensureIndexes();

        return datastore;
    }

    @Bean
    public CardRepository cardRepository() {
        CardRepositoryImpl repo = new CardRepositoryImpl();
        repo.setDatastore(datastore());

        return repo;
    }

    @Bean
    public DeckRepository deckRepository() {
        DeckRepositoryImpl repo = new DeckRepositoryImpl();
        repo.setDatastore(datastore());

        return repo;
    }

    @Bean
    public MechanicRepository mechanicRepository() {
        MechanicRepositoryImpl repo = new MechanicRepositoryImpl();
        repo.setDatastore(datastore());

        return repo;
    }

    @Bean
    public UserRepository userRepository() {
        UserRepositoryImpl repo = new UserRepositoryImpl();
        repo.setDatastore(datastore());

        return repo;
    }

}
