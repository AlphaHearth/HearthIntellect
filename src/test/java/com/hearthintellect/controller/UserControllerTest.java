package com.hearthintellect.controller;

import com.hearthintellect.model.Token;
import com.hearthintellect.model.User;
import com.hearthintellect.utils.Message;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

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
        getWithAssertion("/users/" + username, 200, testUser);
    }

    @Test
    public void testGettingNotExistedUser() throws Exception {
        String username = "NOT_REALLY_EXIST";
        Message expectedMessage = new Message(404, "User with given ID `" + username + "` does not exist.");
        getWithAssertion("/users/" + username, 404, expectedMessage);
    }

    @Test
    public void testCreatingNotExistedUser() throws Exception {
        testUser.setUsername("SomethingInteresting.");
        postWithAssertion("/users", testUser, 201, null);
    }

    @Test
    public void testCreatingExistedUser() throws Exception {
        postWithAssertion("/users", testUser, 403, null);
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

        putWithAssertion("/users/" + testUser.getUsername() + "?token=" + token.getID(), testUser, 201, null);
        getWithAssertion("/users/" + testUser.getUsername(), 200, expectedUser);
        postWithAssertion("/login", new TokenController.LoginRequest(testUser.getUsername(), oldPassword), 200, null);
    }

    @Test
    public void testUpdatingExistedUserWithInvalidToken() throws Exception {
        Token token = testTokens.stream()
                .filter((t) -> t.getExpireTime().isBefore(LocalDateTime.now()))
                .findFirst().get();
        testUser = testUsers.stream()
                .filter((u) -> u.getUsername().equals(token.getUsername()))
                .findFirst().get();

        putWithAssertion("/users/" + testUser.getUsername() + "?token=" + token.getID(), testUser, 401, null);
    }

    @Test
    public void testUpdatingNotExistedUser() throws Exception {
        String testUsername = "SomethingStrange";
        Token token = testTokens.stream()
                .filter((t) -> t.getExpireTime().isAfter(LocalDateTime.now()))
                .findFirst().get();
        putWithAssertion("/users/" + testUsername + "?token=" + token.getID(), testUser, 404, null);
    }
}
