package com.hearthintellect.controller;

import com.google.gson.Gson;
import com.hearthintellect.config.MockMongoConfig;
import com.hearthintellect.config.SpringWebConfig;
import com.hearthintellect.model.Card;
import com.hearthintellect.model.Message;
import com.hearthintellect.repository.CardRepository;
import com.hearthintellect.util.ResourceUtils;
import com.hearthintellect.util.TypeTokens;
import com.hearthintellect.utils.SortUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MockMongoConfig.class, SpringWebConfig.class })
public class CardControllerTest {

    @Autowired private Gson gson;
    @Autowired private CardRepository cardRepository;
    @Autowired private WebApplicationContext context;

    private MockMvc mockMvc;

    private List<Card> testCards;
    private Card testCard;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(context).build();

        testCards = gson.fromJson(ResourceUtils.readResource("testCards.json"), TypeTokens.cardListType);
        testCard = testCards.get(0);
    }

    @Test
    public void testCardListing() throws Exception {
        String testOrder = "cost";
        PageRequest testPage = new PageRequest(2, 5, SortUtils.parseSort(testOrder));

        when(cardRepository.findAll(testPage)).thenReturn(new PageImpl<>(testCards));

        mockMvc.perform(
                    get("/cards?pageNum=" + testPage.getPageNumber() + "&pageSize=" + testPage.getPageSize()
                        + "&order=" + testOrder).accept(MediaType.APPLICATION_JSON_UTF8)
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(testCards), true));
    }

    @Test
    public void testGettingExistedCard() throws Exception {
        String testCardID = testCard.getCardId();

        when(cardRepository.findOne(testCardID)).thenReturn(testCard);

        mockMvc.perform(get("/cards/" + testCardID + "/").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(testCard), true));
    }

    @Test
    public void testGettingNotExistedCard() throws Exception {
        String testCardID = "Doesn't matter";

        when(cardRepository.findOne(testCardID)).thenReturn(null);

        Message expectedMessage = new Message(404, "Card with given ID `" + testCardID + "` does not exist.");
        mockMvc.perform(get("/cards/" + testCardID).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(expectedMessage), true));
    }
}
