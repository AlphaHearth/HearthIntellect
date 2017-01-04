package com.hearthintellect.controller;

import com.hearthintellect.model.Token;
import com.hearthintellect.utils.Message;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TokenControllerGetTest extends ControllerTest {

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
        getWithAssertion("/tokens/" + validToken.getID(), 200, validToken);
    }

    @Test
    public void testGettingExpiredToken() throws Exception {
        Message expectedMessage = new Message(404, "Token with given ID `" + expiredToken.getID() + "` does not exist.");
        getWithAssertion("/tokens/" + expiredToken.getID(), 404, expectedMessage);
    }

    @Test
    public void testGettingNotExistedToken() throws Exception {
        String testTokenID = "NOT_REALLY_EXIST";
        Message expectedMessage = new Message(404, "Token with given ID `" + testTokenID + "` does not exist.");
        getWithAssertion("/tokens/" + testTokenID, 404, expectedMessage);
    }
}
