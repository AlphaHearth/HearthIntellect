package com.hearthintellect.controller;

import com.hearthintellect.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TokenControllerLoginTest extends ControllerTest {

    private User testUser;

    @Before
    public void setUp() {
        super.setUp();
        testUser = testUsers.get(0);
    }

    @Test
    public void testLoggingInWithValidCredential() throws Exception {
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(new TokenController.LoginRequest(testUser.getUsername(), testUser.getPassword())))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testLoggingWithInvalidCredential() throws Exception {
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(new TokenController.LoginRequest(testUser.getUsername(), "I\'m not a password.")))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

}
