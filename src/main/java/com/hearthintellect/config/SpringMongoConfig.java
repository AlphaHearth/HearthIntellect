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
import java.util.Collections;
import java.util.Properties;

/**
 * Spring configuration class for Morphia
 */
@Configuration
@ComponentScan("com.hearthintellect.repository.morphia")
public class SpringMongoConfig {
    private static final Logger LOG = LoggerFactory.getLogger(SpringMongoConfig.class);

    /** Name of package where the mapping classes are */
    private static final String MODEL_PACKAGE_NAME = "com.hearthintellect.model";

    private static final String CONFIG_PATH = ".hearth";
    private static final String DB_CONFIG = "db.conf";

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 27017;
    private static final String DEFAULT_DATABASE = "hearth";
    private static final String DEFAULT_USERNAME = "";
    private static final String DEFAULT_PASSWORD = "";

    @Bean
    public Properties dbConfig() {
        String userHome = System.getProperty("user.home");
        Path dbConfigPath = Paths.get(userHome, CONFIG_PATH, DB_CONFIG);
        Properties properties = new Properties();
        if (Files.exists(dbConfigPath)) {
            try {
                properties.load(Files.newInputStream(dbConfigPath));
                return properties;
            } catch (IOException ex) {
                LOG.warn("Exception occurred when trying to load database config file: ", ex);
            }
        }
        LOG.info("Database config file could not be loaded. Using default setting...");
        return properties;
    }

    @Bean
    public String mongoDatabase(Properties dbConfig) {
        if (dbConfig.containsKey("Database")) {
            if (LOG.isDebugEnabled())
                LOG.debug("Found customized database name `{}`.", dbConfig.getProperty("Database"));
            return dbConfig.getProperty("Database");
        }
        LOG.debug("Customized database name not found. Using default database name `{}`.", DEFAULT_DATABASE);
        return DEFAULT_DATABASE;
    }

    @Bean
    public String mongoHost(Properties dbConfig) {
        if (dbConfig.containsKey("Host")) {
            if (LOG.isDebugEnabled())
                LOG.debug("Found customized host `{}`.", dbConfig.getProperty("Host"));
            return dbConfig.getProperty("Host");
        }
        LOG.debug("Customized host not found. Using default host `{}`.", DEFAULT_HOST);
        return DEFAULT_HOST;
    }

    @Bean
    public Integer mongoPort(Properties dbConfig) {
        if (dbConfig.containsKey("Port")) {
            if (LOG.isDebugEnabled())
                LOG.debug("Found customized port `{}`.", dbConfig.getProperty("Port"));
            try {
                return Integer.parseInt(dbConfig.getProperty("Port"));
            } catch (NumberFormatException ex) {
                LOG.warn("Failed to parse the customized port config `{}` as Integer.", dbConfig.getProperty("Port"));
            }
        }
        LOG.debug("Customized port not found. Using default port `{}`.", DEFAULT_PORT);
        return DEFAULT_PORT;
    }

    @Bean
    public String mongoUsername(Properties dbConfig) {
        if (dbConfig.containsKey("Username")) {
            if (LOG.isDebugEnabled())
                LOG.debug("Found customized username `{}`.", dbConfig.getProperty("Username"));
            return dbConfig.getProperty("Username");
        }
        LOG.debug("Customized username not found. Using default username `{}`.", DEFAULT_USERNAME);
        return DEFAULT_USERNAME;
    }

    @Bean
    public String mongoPassword(Properties dbConfig) {
        if (dbConfig.containsKey("Password")) {
            if (LOG.isDebugEnabled())
                LOG.debug("Found customized password `{}`.", dbConfig.getProperty("Password"));
            return dbConfig.getProperty("Password");
        }
        LOG.debug("Customized password not found. Using default password `{}`.", DEFAULT_PASSWORD);
        return DEFAULT_PASSWORD;
    }

    @Bean
    public MongoClient mongoClient(String mongoHost, Integer mongoPort, String mongoDatabase,
                                   String mongoUsername, String mongoPassword) {
        return new MongoClient(
                new ServerAddress(mongoHost, mongoPort),
                Collections.singletonList(MongoCredential.createCredential(
                        mongoUsername, mongoDatabase, mongoPassword.toCharArray()
                ))
        );
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
    public Datastore datastore(Morphia morphia, MongoClient mongoClient, String mongoDatabase) {
        Datastore datastore = morphia.createDatastore(mongoClient, mongoDatabase);
        datastore.ensureIndexes();
        return datastore;
    }
}
