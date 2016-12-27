package com.hearthintellect.config;

import com.hearthintellect.morphia.converters.LocalDateTimeConverter;
import com.hearthintellect.morphia.converters.LocaleStringConverter;
import com.hearthintellect.morphia.converters.ZonedDateTimeConverter;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/**
 * Spring configuration class for Morphia
 */
@Configuration
@ComponentScan("com.hearthintellect.dao.morphia")
public class SpringMongoConfig {
    private static final Logger LOG = LoggerFactory.getLogger(SpringMongoConfig.class);

    /** Name of the database */
    public static final String DATABASE_NAME = "hearth";
    /** Name of package where the mapping classes are */
    public static final String MODEL_PACKAGE_NAME = "com.hearthintellect.model";

    static final String CONFIG_PATH = ".hearth";
    static final String DB_CONFIG = "db.conf";

    static final String DEFAULT_HOST = "127.0.0.1";
    static final int DEFAULT_PORT = 27017;
    public static final String DEFAULT_DATABASE = "hearth";
    static final String DEFAULT_USERNAME = "";
    static final String DEFAULT_PASSWORD = "";

    @Bean
    public MongoClient mongoClient() {
        String userHome = System.getProperty("user.home");
        Path dbConfig = Paths.get(userHome, CONFIG_PATH, DB_CONFIG);
        if (Files.exists(dbConfig)) {
            try {
                Properties configs = new Properties();
                configs.load(Files.newInputStream(dbConfig));
                String host = configs.containsKey("Host") ? configs.getProperty("Host") : DEFAULT_HOST;
                int port = configs.containsKey("Port") ? Integer.parseInt(configs.getProperty("Port")) : DEFAULT_PORT;
                String username = configs.containsKey("Username") ? configs.getProperty("Username") : DEFAULT_USERNAME;
                char[] password = configs.containsKey("Password") ?
                        configs.getProperty("Password").toCharArray() : DEFAULT_PASSWORD.toCharArray();
                String database = configs.containsKey("Database") ? configs.getProperty("Database") : DEFAULT_DATABASE;

                LOG.debug("Connecting to {}:{} with username `{}`", host, port, username);
                MongoClient client = new MongoClient(
                        new ServerAddress(host, port),
                        Collections.singletonList(MongoCredential.createCredential(username, database, password))
                );
                Arrays.fill(password, '\0');
                return client;
            } catch (IOException e) {
                LOG.warn("Exception occurred when trying to load database config file: ", e);
            }
        }

        LOG.info("Database config file could not be loaded. Using default setting...");
        return new MongoClient();
    }

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
    public Datastore datastore(Morphia morphia, MongoClient mongoClient) {
        Datastore datastore = morphia.createDatastore(mongoClient, DATABASE_NAME);
        datastore.ensureIndexes();
        return datastore;
    }
}
