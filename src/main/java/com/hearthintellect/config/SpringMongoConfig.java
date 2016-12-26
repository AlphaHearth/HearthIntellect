package com.hearthintellect.config;

import com.hearthintellect.morphia.converters.LocalDateTimeConverter;
import com.hearthintellect.morphia.converters.LocaleStringConverter;
import com.hearthintellect.morphia.converters.ZonedDateTimeConverter;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration class for Morphia
 */
@Configuration
@ComponentScan("com.hearthintellect.dao.morphia")
public class SpringMongoConfig {

    /** Name of the database */
    public static final String DATABASE_NAME = "hearthstone";
    /** Name of package where the mapping classes are */
    public static final String MODEL_PACKAGE_NAME = "com.hearthintellect.model";

    @Bean
    public Morphia morphia() {
        Morphia morphia = new Morphia();
        morphia.mapPackage(MODEL_PACKAGE_NAME);

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
    public Datastore datastore(Morphia morphia, MongoClient mongoClient) {
        Datastore datastore = morphia.createDatastore(mongoClient, DATABASE_NAME);
        datastore.ensureIndexes();
        return datastore;
    }
}
