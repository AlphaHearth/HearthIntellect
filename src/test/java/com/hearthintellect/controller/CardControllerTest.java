package com.hearthintellect.controller;

import com.hearthintellect.model.Card;
import com.hearthintellect.utils.Message;
import com.hearthintellect.utils.SortUtils;
import org.apache.commons.collections4.IterableUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;

public class CardControllerTest extends ControllerTest {

    @Test
    public void testCardListing() throws Exception {
        String testOrder = "cost";
        PageRequest testPage = new PageRequest(1, 2, SortUtils.parseSort(testOrder));

        getWithAssertion(
                "/cards?pageNum=" + testPage.getPageNumber() + "&pageSize=" + testPage.getPageSize() + "&order=" + testOrder,
                200, IterableUtils.toList(cardRepository.findAll(testPage)));
    }

    @Test
    @Ignore
    public void testCardIDExistenceQueryWithExistedCard() {
        for (Card card : testCards)
            headWithAssertion("/cards/" + card.getID(), 200);
    }

    @Test
    @Ignore
    public void testCardIDExistenceQueryWithNotExistedCard() {
        String testCardID = "NOT_REALLY_EXIST";
        headWithAssertion("/cards/" + testCardID, 404);
    }

    @Test
    public void testGettingExistedCard() {
        Card testCard = testCards.get(0);
        String testCardID = testCard.getCardId();

        getWithAssertion("/cards/" + testCardID, 200, testCard);
    }

    @Test
    public void testGettingNotExistedCard() {
        String testCardID = "NOT_REALLY_EXIST";
        Message expectedMessage = entityNotFoundMessage("Card", testCardID);
        getWithAssertion("/cards/" + testCardID, 404, expectedMessage);
    }
}
