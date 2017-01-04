package com.hearthintellect.controller;

import com.hearthintellect.model.Card;
import com.hearthintellect.utils.Message;
import com.hearthintellect.utils.SortUtils;
import org.apache.commons.collections4.IterableUtils;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;

public class CardControllerTest extends ControllerTest {

    @Test
    public void testCardListing() throws Exception {
        String testOrder = "cost";
        int testPageNum = 1;
        int testPageSize = 2;
        PageRequest testPage = new PageRequest(1, 2, SortUtils.parseSort(testOrder));

        getWithAssertion(
                "/cards?pageNum=" + testPage.getPageNumber() + "&pageSize=" + testPage.getPageSize() + "&order=" + testOrder,
                200, IterableUtils.toList(cardRepository.findAll(testPage)));
    }

    @Test
    public void testGettingExistedCard() throws Exception {
        Card testCard = testCards.get(0);
        String testCardID = testCard.getCardId();

        getWithAssertion("/cards/" + testCardID, 200, testCard);
    }

    @Test
    public void testGettingNotExistedCard() throws Exception {
        String testCardID = "NOT_REALLY_EXIST";
        Message expectedMessage = new Message(404, "Card with given ID `" + testCardID + "` does not exist.");
        getWithAssertion("/cards/" + testCardID, 404, expectedMessage);
    }
}
