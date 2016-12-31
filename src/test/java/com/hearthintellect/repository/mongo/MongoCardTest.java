package com.hearthintellect.repository.mongo;

import com.hearthintellect.model.Card;
import com.hearthintellect.repository.CardRepository;
import com.hearthintellect.util.ResourceUtils;
import com.hearthintellect.util.TypeTokens;
import com.mongodb.DBRef;
import org.bson.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.hearthintellect.util.MongoMatchers.field;
import static com.hearthintellect.util.MongoMatchers.index;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Test cases for metadata of the created {@code cards} collection on the MongoDB database.
 */
public class MongoCardTest extends MongoTest {

    @Autowired private CardRepository cardRepository;
    private Card testCard;

    public void setUp() {
        super.setUp();

        List<Card> testCards = gson.fromJson(ResourceUtils.readResource("testCards.json"), TypeTokens.cardListType);
        testCard = testCards.get(0);
        cardRepository.save(testCard);
    }

    @Test
    public void testCardCollectionIndexes() {
        assertThat(indexes, hasItem(index(field("set"))));
        assertThat(indexes, hasItem(index(field("type"))));
        assertThat(indexes, hasItem(index(field("quality"))));
        assertThat(indexes, hasItem(index(field("race"))));
        assertThat(indexes, hasItem(index(field("class"))));
    }

    @Test
    public void testCardCollectionFormat() {
        Document document = mongoCollection.find(new Document("_id", testCard.getCardId())).first();
        assertThat(document, notNullValue());

        // Test on primitive fields
        assertThat(document.getString("_id"), is(testCard.getCardId()));
        assertThat(document.getInteger("cost"), is(testCard.getCost()));
        assertThat(document.getInteger("attack"), is(testCard.getAttack()));
        assertThat(document.getInteger("health"), is(testCard.getHealth()));
        assertThat(document.getBoolean("collectible"), is(testCard.getCollectible()));

        // Test on enum fields
        assertThat(document.getString("quality"), is(testCard.getQuality().name()));
        assertThat(document.getString("type"), is(testCard.getType().name()));
        assertThat(document.getString("set"), is(testCard.getSet().name()));

        // Test on Patch fields
        DBRef sincePatch = document.get("sincePatch", com.mongodb.DBRef.class);
        assertThat(sincePatch.getCollectionName(), is("patches"));
        assertThat(sincePatch.getId(), is(testCard.getSincePatch().getBuildNum()));
        DBRef addedPatch = document.get("addedPatch", com.mongodb.DBRef.class);
        assertThat(addedPatch.getCollectionName(), is("patches"));
        assertThat(addedPatch.getId(), is(testCard.getAddedPatch().getBuildNum()));

        // Test on LocaleString fields
        Document name = document.get("name", Document.class);
        assertThat(name, notNullValue());
        for (Map.Entry<Locale, String> entry : testCard.getName().entrySet())
            assertThat(name.getString(entry.getKey().toString()), is(entry.getValue()));
        Document effect = document.get("effect", Document.class);
        assertThat(effect, notNullValue());
        for (Map.Entry<Locale, String> entry : testCard.getEffect().entrySet())
            assertThat(effect.getString(entry.getKey().toString()), is(entry.getValue()));
        Document flavor = document.get("flavor", Document.class);
        assertThat(flavor, notNullValue());
        for (Map.Entry<Locale, String> entry : testCard.getFlavor().entrySet())
            assertThat(flavor.getString(entry.getKey().toString()), is(entry.getValue()));
    }

    @Override
    public Class<?> getEntityClass() {
        return Card.class;
    }
}
