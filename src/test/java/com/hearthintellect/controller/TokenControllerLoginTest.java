package com.hearthintellect.controller;

import com.hearthintellect.model.User;
import org.junit.Before;
import org.junit.Test;

public class TokenControllerLoginTest extends ControllerTest {

    private User testUser;

    @Before
    public void setUp() {
        super.setUp();
        testUser = testUsers.get(0);
    }

    @Test
    public void testLoggingInWithValidCredential() throws Exception {
        TokenController.LoginRequest body = new TokenController.LoginRequest(testUser.getUsername(), testUser.getPassword());
        postWithAssertion("/login", body, 200, null);
    }

    @Test
    public void testLoggingWithInvalidCredential() throws Exception {
        TokenController.LoginRequest body = new TokenController.LoginRequest(testUser.getUsername(), "Wrong Password");
        postWithAssertion("/login", body, 401, null);
    }

}
