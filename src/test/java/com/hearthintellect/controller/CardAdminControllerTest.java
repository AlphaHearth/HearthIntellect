package com.hearthintellect.controller;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.Token;
import com.hearthintellect.util.ResourceUtils;
import com.hearthintellect.utils.LocaleString;
import com.hearthintellect.utils.Message;
import org.junit.Before;
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
    public void testCreatingCardWithoutToken() {
        Message expectedMessage = emptyTokenMessage();
        postWithAssertion("/cards", newCard, 400, expectedMessage);
    }

    @Test
    public void testCreatingCardWithNonAdminToken() {
        Token testToken = testTokens.get(0);
        Message expectedMessage = invalidTokenMessage(testToken.getID());
        postWithAssertion("/cards?token=" + testToken.getID(), newCard, 401, expectedMessage);
    }

    @Test
        public void testCreatingNotExistedCard() {
        Message expectedMessage = entityCreatedMessage("Card", newCard.getID(), "/cards/" + newCard.getID());
        postWithAssertion("/cards?token=" + adminTokenID, newCard, 201, expectedMessage);
        getWithAssertion("/cards/" + newCard.getID(), 200, newCard);
    }

    @Test
        public void testCreatingExistedCard() {
        Card testCard = testCards.get(0);
        Message expectedMessage = duplicateEntityMessage("Card", testCard.getID());
        postWithAssertion("/cards?token=" + adminTokenID, testCard, 403, expectedMessage);
    }

    @Test
        public void testDeletingCardWithoutToken() {
        Message expectedMessage = emptyTokenMessage();
        deleteWithAssertion("/cards/" + newCard.getID(), 400, expectedMessage);
    }

    @Test
        public void testDeletingCardWithNonAdminToken() {
        Token testToken = testTokens.get(0);
        Message expectedMessage = invalidTokenMessage(testToken.getID());
        deleteWithAssertion("/cards/" + newCard.getID() + "?token=" + testToken.getID(), 401, expectedMessage);
    }

    @Test
        public void testDeletingNotExistedCard() {
        Message expectedMessage = entityNotFoundMessage("Card", newCard.getID());
        deleteWithAssertion("/cards/" + newCard.getID() + "?token=" + adminTokenID, 404, expectedMessage);
    }

    @Test
        public void testDeletingExistedCard() {
        Card testCard = testCards.get(0);
        deleteWithAssertion("/cards/" + testCard.getID() + "?token=" + adminTokenID, 204, null);
        headWithAssertion("/cards/" + testCard.getID(), 404);
    }

    @Test
        public void testUpdatingCardWithoutToken() {
        Card testCard = testCards.get(0);
        Message expectedMessage = emptyTokenMessage();
        putWithAssertion("/cards/" + testCard.getID(), testCard, 400, expectedMessage);
    }

    @Test
        public void testUpdatingCardWithNonAdminToken() {
        Token testToken = testTokens.get(0);
        Message expectedMessage = invalidTokenMessage(testToken.getID());
        Card testCard = testCards.get(0);
        putWithAssertion("/cards/" + testCard.getID() + "?token=" + testToken.getID(), testCard, 401, expectedMessage);
    }

    @Test
        public void testUpdatingNotExistedCard() {
        Message expectedMessage = entityNotFoundMessage("Card", newCard.getID());
        putWithAssertion("/cards/" + newCard.getID() + "?token=" + adminTokenID, newCard, 404, expectedMessage);
    }

    @Test
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
        sentCardBody.setCollectible(testCard.getCollectible());

        // TODO Add test on more fields

        Message expectedMessage = entityUpdatedMessage("Card", testCard.getID(), "/cards/" + testCard.getID());
        putWithAssertion("/cards/" + testCard.getID() + "?token=" + adminTokenID, sentCardBody, 201, expectedMessage);
        getWithAssertion("/cards/" + testCard.getID(), 200, testCard);
    }
}
