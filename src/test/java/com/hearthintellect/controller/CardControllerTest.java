package com.hearthintellect.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hearthintellect.config.MockMongoConfig;
import com.hearthintellect.config.SpringWebConfig;
import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.model.Card;
import com.hearthintellect.model.Message;
import com.hearthintellect.util.ResourceUtils;
import com.hearthintellect.utils.Page;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
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
    private static final Type cardListType = new TypeToken<List<Card>>(){}.getType();

    @Autowired private Gson gson;
    @Autowired private CardRepository cardRepository;
    @Autowired private WebApplicationContext context;

    private MockMvc mockMvc;

    private List<Card> testCards;
    private Card testCard;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(context).build();

        testCards = gson.fromJson(ResourceUtils.readResource("testCards.json"), cardListType);
        testCard = testCards.get(0);
    }

    @Test
    public void testCardListing() throws Exception {
        String testOrder = "cost";
        Page testPage = new Page(2, 5);

        when(cardRepository.findAll(testOrder, testPage)).thenReturn(testCards);

        mockMvc.perform(
                    get("/cards?pageNum=" + testPage.getPageNum() + "&pageSize=" + testPage.getNumPerPage()
                        + "&order=" + testOrder).accept(MediaType.APPLICATION_JSON_UTF8)
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(testCards), true));
    }

    @Test
    public void testGettingExistedCard() throws Exception {
        String testCardID = testCard.getCardId();

        when(cardRepository.findById(testCardID)).thenReturn(testCard);

        mockMvc.perform(get("/cards/" + testCardID + "/").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(testCard), true));
    }

    @Test
    public void testGettingNotExistedCard() throws Exception {
        String testCardID = "Doesn't matter";

        when(cardRepository.findById(testCardID)).thenReturn(null);

        Message expectedMessage = new Message(404, "Card with given ID `" + testCardID + "` does not exist.");
        mockMvc.perform(get("/cards/" + testCardID).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(expectedMessage), true));
    }
}
