package com.hearthintellect.controller;

import com.hearthintellect.model.Token;
import com.hearthintellect.utils.Message;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TokenControllerGetTest extends ControllerTest {
    private static final String NOT_EXISTED_TOKEN_ID = "Not Existed Token ID";

    private Token validToken;
    private Token expiredToken;

    @Before
    public void setUp() {
        super.setUp();

        testTokens.forEach((t) -> {
            if (t.getExpireTime().isAfter(LocalDateTime.now()))
                validToken = t;
            else
                expiredToken = t;
        });
        assertThat(validToken, notNullValue());
        assertThat(expiredToken, notNullValue());
    }

    @Test
    public void testGettingExistedToken() throws Exception {
        mockMvc.perform(get("/tokens/" + validToken.getID()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(validToken), true));
    }

    @Test
    public void testGettingExpiredToken() throws Exception {
        mockMvc.perform(get("/tokens/" + expiredToken.getID()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(
                        new Message(404, "Token with given ID `" + expiredToken.getID() + "` does not exist.")), true));
    }

    @Test
    public void testGettingNotExistedToken() throws Exception {
        mockMvc.perform(get("/tokens/" + NOT_EXISTED_TOKEN_ID).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(
                        new Message(404, "Token with given ID `" + NOT_EXISTED_TOKEN_ID + "` does not exist.")), true));
    }

}
