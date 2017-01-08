package com.hearthintellect.controller;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.Token;
import com.hearthintellect.util.ResourceUtils;
import com.hearthintellect.utils.CreatedMessage;
import com.hearthintellect.utils.DeletedMessage;
import com.hearthintellect.utils.LocaleString;
import com.hearthintellect.utils.Message;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Locale;

public class CardAdminControllerTest extends ControllerTest {

    private Card newCard;

    @Before
    public void setUp() {
        super.setUp();
        newCard = ResourceUtils.readResrouceJsonAsEntity("new_card.json", Card.class, gson);
    }

    @Test
    @Ignore
    public void testCreatingCardWithoutToken() {
        Message expectedMessage = new Message(401, "Token cannot be empty.");
        postWithAssertion("/cards", newCard, 401, expectedMessage);
    }

    @Test
    @Ignore
    public void testCreatingCardWithNonAdminToken() {
        Token testToken = testTokens.get(0);
        Message expectedMessage = new Message(401, "The given token `" + testToken.getID() + "` is invalid for this API or has expired.");
        postWithAssertion("/cards?token=" + testToken.getID(), newCard, 401, expectedMessage);
    }

    @Test
    @Ignore
    public void testCreatingNotExistedCard() {
        Message expectedMessage = new CreatedMessage("/cards/" + newCard.getID(),
                "Card with ID `" + newCard.getID() + "` was created.");
        postWithAssertion("/cards?token=" + adminTokenID, newCard, 201, expectedMessage);
    }

    @Test
    @Ignore
    public void testCreatingExistedCard() {
        Card testCard = testCards.get(0);
        Message expectedMessage = new Message(403, "Card with given ID `" + testCard.getID() + "` already exists.");
        postWithAssertion("/cards?token=" + adminTokenID, testCard, 403, expectedMessage);
    }

    @Test
    @Ignore
    public void testDeletingCardWithoutToken() {
        Message expectedMessage = new Message(401, "Token cannot be empty.");
        deleteWithAssertion("/cards/" + newCard.getID(), 401, expectedMessage);
    }

    @Test
    @Ignore
    public void testDeletingCardWithNonAdminToken() {
        Token testToken = testTokens.get(0);
        Message expectedMessage = new Message(401, "The given token `" + testToken.getID() + "` is invalid for this API or has expired.");
        deleteWithAssertion("/cards/" + newCard.getID() + "?token=" + adminTokenID, 401, expectedMessage);
    }

    @Test
    @Ignore
    public void testDeletingNotExistedCard() {
        Message expectedMessage = new Message(404, "Card with given ID `" + newCard.getID() + "` does not exist.");
        deleteWithAssertion("/cards/" + newCard.getID() + "?token=" + adminTokenID, 404, expectedMessage);
    }

    @Test
    @Ignore
    public void testDeletingExistedCard() {
        Card testCard = testCards.get(0);
        Message expectedMessage = new DeletedMessage("Card with ID `" + testCard.getID() + "` was deleted.");
        deleteWithAssertion("/cards/" + testCard.getID() + "?token=" + adminTokenID, 204, expectedMessage);
        headWithAssertion("/cards/" + testCard.getID(), 404);
    }

    @Test
    @Ignore
    public void testUpdatingCardWithoutToken() {
        Card testCard = testCards.get(0);
        Message expectedMessage = new Message(401, "Token cannot be empty.");
        putWithAssertion("/cards/" + testCard.getID(), testCard, 401, expectedMessage);
    }

    @Test
    @Ignore
    public void testUpdatingCardWithNonAdminToken() {
        Token testToken = testTokens.get(0);
        Message expectedMessage = new Message(401, "The given token `" + testToken.getID() + "` is invalid for this API or has expired.");
        Card testCard = testCards.get(0);
        putWithAssertion("/cards/" + testCard.getID() + "?token=" + testToken.getID(), testCard, 401, expectedMessage);
    }

    @Test
    @Ignore
    public void testUpdatingNotExistedCard() {
        Message expectedMessage = new Message(404, "Card with given ID `" + newCard.getID() + "` does not exist.");
        putWithAssertion("/cards/" + newCard.getID() + "?token=" + adminTokenID, newCard, 404, expectedMessage);
    }

    @Test
    @Ignore
    public void testUpdatingExistedCard() {
        Card testCard = testCards.get(0);
        Card sentCardBody = new Card();

        sentCardBody.setName(new LocaleString());
        sentCardBody.getName().put(Locale.US, "Something strange");
        testCard.getName().put(Locale.US, "Something strange");
        sentCardBody.getName().put(Locale.CHINA, "");
        testCard.getName().put(Locale.CHINA, "");
        sentCardBody.setCost(3);
        testCard.setCost(3);

        // TODO Add test on more fields

        Message expectedMessage = new CreatedMessage("/cards/" + testCard.getID(),
                "Card with ID `" + testCard.getID() + "` was updated.");
        putWithAssertion("/cards/" + testCard.getID() + "?token=" + adminTokenID, sentCardBody, 201, expectedMessage);
        getWithAssertion("/cards/" + testCard.getID(), 200, testCard);
    }
}
