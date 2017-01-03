package com.hearthintellect.controller;

import com.hearthintellect.model.Card;
import com.hearthintellect.utils.Message;
import com.hearthintellect.utils.SortUtils;
import org.apache.commons.collections4.IterableUtils;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CardControllerTest extends ControllerTest {

    @Test
    public void testCardListing() throws Exception {
        String testOrder = "cost";
        PageRequest testPage = new PageRequest(1, 2, SortUtils.parseSort(testOrder));

        mockMvc.perform(
                    get("/cards?pageNum=" + testPage.getPageNumber() + "&pageSize=" + testPage.getPageSize()
                        + "&order=" + testOrder).accept(MediaType.APPLICATION_JSON_UTF8)
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(IterableUtils.toList(cardRepository.findAll(testPage))), true));
    }

    @Test
    public void testGettingExistedCard() throws Exception {
        Card testCard = testCards.get(0);
        String testCardID = testCard.getCardId();

        mockMvc.perform(get("/cards/" + testCardID + "/").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(testCard), true));
    }

    @Test
    public void testGettingNotExistedCard() throws Exception {
        String testCardID = "Doesn't matter";

        Message expectedMessage = new Message(404, "Card with given ID `" + testCardID + "` does not exist.");
        mockMvc.perform(get("/cards/" + testCardID).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(expectedMessage), true));
    }
}
