package com.hearthintellect.repository.mongo;

import com.google.gson.Gson;
import com.hearthintellect.config.InMemoryMongoConfig;
import com.hearthintellect.config.SpringWebConfig;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoryMongoConfig.class)
public abstract class MongoTest {

    @Autowired protected String mongoDatabase;
    @Autowired protected MongoClient mongoClient;
    protected final Gson gson = new SpringWebConfig().gson();
    protected MongoCollection<Document> mongoCollection;
    protected List<Document> indexes;

    @Before
    public void setUp() {
        Class entityClass = getEntityClass();
        String collectionName = getEntityClass().getAnnotation(org.springframework.data.mongodb.core.mapping.Document.class).collection();
        mongoCollection = mongoClient.getDatabase(mongoDatabase).getCollection(collectionName);
        indexes = new ArrayList<>();
        mongoCollection.listIndexes().forEach(new Consumer<Document>() {
            @Override
            public void accept(Document document) {
                indexes.add(document);
            }
        });
    }

    public abstract Class<?> getEntityClass();
}
