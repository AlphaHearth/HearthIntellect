package com.hearthintellect.controller;

import com.hearthintellect.model.Token;
import com.hearthintellect.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends ControllerTest {

    private User testUser;

    @Before
    public void setUp() {
        super.setUp();
        testUser = testUsers.get(0);
    }

    @Test
    public void testGettingExistedUser() throws Exception {
        String username = testUser.getUsername();
        testUser.setPassword(null);
        mockMvc.perform(get("/users/" + username).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(gson.toJson(testUser), true));
    }

    @Test
    public void testGettingNotExistedUser() throws Exception {
        mockMvc.perform(get("/users/NotReallyMatter").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreatingNotExistedUser() throws Exception {
        testUser.setUsername("SomethingInteresting.");
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(testUser)).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreatingExistedUser() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(testUser)).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdatingExistedUserWithValidToken() throws Exception {
        String newEmail = "mrdai@example.com";
        String newPassword = "Something interesting.";
        String oldPassword = testUser.getPassword();
        assertThat(newEmail, is(not(testUser.getEmail())));
        assertThat(newPassword, is(not(oldPassword)));

        Token token = testTokens.stream()
                .filter((t) -> t.getExpireTime().isAfter(LocalDateTime.now()))
                .findFirst().get();
        testUser = testUsers.stream()
                .filter((u) -> u.getUsername().equals(token.getUsername()))
                .findFirst().get();

        // Assert that not-provided fields will not be changed
        String oldNickname = testUser.getNickname();
        testUser.setNickname(null);
        // Assert that provided fields will be changed
        testUser.setEmail(newEmail);
        // Assert that password will not be changed
        testUser.setPassword(newPassword);

        User expectedUser = new User(testUser.getUsername(), newEmail, oldNickname, null);

        mockMvc.perform(put("/users/" + testUser.getUsername() + "?token=" + token.getID())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(testUser)).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/users/" + testUser.getUsername()).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(expectedUser), true));
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(new TokenController.LoginRequest(testUser.getUsername(), oldPassword)))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatingExistedUserWithInvalidToken() throws Exception {
        Token token = testTokens.stream()
                .filter((t) -> t.getExpireTime().isBefore(LocalDateTime.now()))
                .findFirst().get();
        testUser = testUsers.stream()
                .filter((u) -> u.getUsername().equals(token.getUsername()))
                .findFirst().get();

        mockMvc.perform(put("/users/" + testUser.getUsername() + "?token=" + token.getID())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(testUser)).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdatingNotExistedUser() throws Exception {
        String testUsername = "SomethingStrange";
        Token token = testTokens.stream()
                .filter((t) -> t.getExpireTime().isAfter(LocalDateTime.now()))
                .findFirst().get();
        mockMvc.perform(put("/users/" + testUsername + "?token=" + token.getID())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(testUser)).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }
}
