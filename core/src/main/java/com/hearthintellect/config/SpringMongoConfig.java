package com.hearthintellect.config;

import com.hearthintellect.dao.*;
import com.hearthintellect.dao.morphia.*;
import com.hearthintellect.morphia.converters.EnumOrdinalConverter;
import com.hearthintellect.morphia.converters.LocalDateTimeConverter;
import com.hearthintellect.morphia.converters.LocaleStringConverter;
import com.hearthintellect.morphia.converters.ZonedDateTimeConverter;
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
    public static final String DATABASE_NAME = "hearthstone";
    /** Name of package where the mapping classes are */
    public static final String PACKAGE_NAME = "com.hearthintellect.model";

    @Bean
    public Morphia morphia() {
        Morphia morphia = new Morphia();
        morphia.mapPackage(PACKAGE_NAME);

        morphia.getMapper().getConverters().removeConverter(new EnumConverter());
        morphia.getMapper().getConverters().addConverter(new EnumOrdinalConverter());
        morphia.getMapper().getConverters().addConverter(new LocalDateTimeConverter());
        morphia.getMapper().getConverters().addConverter(new ZonedDateTimeConverter());
        morphia.getMapper().getConverters().addConverter(new LocaleStringConverter());

        return morphia;
    }

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient();
    }

    @Bean
    public Datastore datastore(MongoClient mongoClient) {
        Datastore datastore = morphia().createDatastore(mongoClient, DATABASE_NAME);
        datastore.ensureIndexes();
        return datastore;
    }

    @Bean
    public CardRepository cardRepository(Datastore datastore) {
        CardRepositoryImpl repo = new CardRepositoryImpl();
        repo.setDatastore(datastore);
        return repo;
    }

    @Bean
    public DeckRepository deckRepository(Datastore datastore) {
        DeckRepositoryImpl repo = new DeckRepositoryImpl();
        repo.setDatastore(datastore);
        return repo;
    }

    @Bean
    public MechanicRepository mechanicRepository(Datastore datastore) {
        MechanicRepositoryImpl repo = new MechanicRepositoryImpl();
        repo.setDatastore(datastore);
        return repo;
    }

    @Bean
    public PatchRepository patchRepository(Datastore datastore) {
        PatchRepositoryImpl repo = new PatchRepositoryImpl();
        repo.setDatastore(datastore);
        return repo;
    }

    @Bean
    public UserRepository userRepository(Datastore datastore) {
        UserRepositoryImpl repo = new UserRepositoryImpl();
        repo.setDatastore(datastore);
        return repo;
    }

}
