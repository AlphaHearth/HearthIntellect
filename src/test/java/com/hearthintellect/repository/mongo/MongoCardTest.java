package com.hearthintellect.repository.mongo;

import com.hearthintellect.config.InMemoryMongoConfig;
import com.hearthintellect.model.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.hearthintellect.util.MongoMatchers.field;
import static com.hearthintellect.util.MongoMatchers.index;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

/**
 * Test cases for metadata of the created {@code cards} collection on the MongoDB database.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoryMongoConfig.class)
public class MongoCardTest extends MongoTest {

    @Test
    public void testCardCollectionIndexes() {
        assertThat(indexes, hasItem(index(field("set"))));
        assertThat(indexes, hasItem(index(field("type"))));
        assertThat(indexes, hasItem(index(field("quality"))));
        assertThat(indexes, hasItem(index(field("race"))));
        assertThat(indexes, hasItem(index(field("class"))));
    }

    @Override
    public Class<?> getEntityClass() {
        return Card.class;
    }
}
