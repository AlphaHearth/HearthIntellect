package com.hearthintellect.controller;

import com.google.gson.Gson;
import com.hearthintellect.config.InMemoryMongoConfig;
import com.hearthintellect.config.SpringWebConfig;
import com.hearthintellect.model.*;
import com.hearthintellect.repository.*;
import com.hearthintellect.security.PasswordEncoder;
import com.hearthintellect.util.ResourceUtils;
import com.hearthintellect.util.TypeTokens;
import com.hearthintellect.utils.CreatedMessage;
import com.hearthintellect.utils.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { InMemoryMongoConfig.class, SpringWebConfig.class })
public abstract class ControllerTest {
    @Autowired protected Gson gson;
    @Autowired protected WebApplicationContext context;

    @Autowired protected CardRepository cardRepository;
    @Autowired protected UserRepository userRepository;
    @Autowired protected TokenRepository tokenRepository;
    @Autowired protected MechanicRepository mechanicRepository;
    @Autowired protected PatchRepository patchRepository;

    @Autowired protected PasswordEncoder passwordEncoder;

    MockMvc mockMvc;

    List<Patch> testPatches;
    List<Card> testCards;
    List<User> testUsers;
    List<Token> testTokens;
    List<Mechanic> testMechanics;

    String adminTokenID;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(context).build();

        testPatches = ResourceUtils.readResrouceJsonAsEntity("patches.json", TypeTokens.patchListType, gson);
        assertThat(testPatches.isEmpty(), is(false));
        patchRepository.save(testPatches);

        testCards = ResourceUtils.readResrouceJsonAsEntity("cards.json", TypeTokens.cardListType, gson);
        assertThat(testCards.isEmpty(), is(false));
        cardRepository.save(testCards);

        testUsers = ResourceUtils.readResrouceJsonAsEntity("users.json", TypeTokens.userListType, gson);
        assertThat(testUsers.isEmpty(), is(false));
        testUsers.forEach((u) -> {
            User userCopy = new User(u.getUsername(), u.getEmail(), u.getNickname(), u.getPassword());
            passwordEncoder.encodeUserPassword(userCopy);
            userRepository.save(userCopy);
        });

        testTokens = ResourceUtils.readResrouceJsonAsEntity("tokens.json", TypeTokens.tokenListType, gson);
        assertThat(testTokens.isEmpty(), is(false));
        tokenRepository.save(testTokens);
        Token adminToken = ResourceUtils.readResrouceJsonAsEntity("admin_token.json", Token.class, gson);
        assertThat(adminToken, notNullValue());
        tokenRepository.save(adminToken);
        tokenRepository.adminVerify(adminToken.getID());
        adminTokenID = adminToken.getID();

        testMechanics = ResourceUtils.readResrouceJsonAsEntity("mechanics.json", TypeTokens.mechanicListType, gson);
        assertThat(testTokens.isEmpty(), is(false));
        mechanicRepository.save(testMechanics);
        testMechanics.sort((m1, m2) -> m1.getMechanicId().compareTo(m2.getMechanicId()));
    }

    @After
    public void tearDown() {
        patchRepository.deleteAll();
        cardRepository.deleteAll();
        userRepository.deleteAll();
        tokenRepository.deleteAll();
        mechanicRepository.deleteAll();
    }

    protected void headWithAssertion(String url, int expectedStatus) {
        requestWithAssertion(HttpMethod.HEAD, url, null, expectedStatus, null);
    }

    protected void deleteWithAssertion(String url, int expectedStatus, Object expectedBody) {
        requestWithAssertion(HttpMethod.DELETE, url, null, expectedStatus, expectedBody);
    }

    protected void getWithAssertion(String url, int expectedStatus, Object expectedBody) {
        requestWithAssertion(HttpMethod.GET, url, null, expectedStatus, expectedBody);
    }

    protected void postWithAssertion(String url, Object requestBody, int expectedStatus, Object expectedBody) {
        requestWithAssertion(HttpMethod.POST, url, requestBody, expectedStatus, expectedBody);
    }

    protected void putWithAssertion(String url, Object requestBody, int expectedStatus, Object expectedBody) {
        requestWithAssertion(HttpMethod.PUT, url, requestBody, expectedStatus, expectedBody);
    }

    protected void requestWithAssertion(HttpMethod method, String url, Object requestBody,
                                      int expectedStatus, Object expectedBody) {
        try {
            MockHttpServletRequestBuilder requestBuilder = request(method, url).accept(MediaType.APPLICATION_JSON_UTF8);
            if (requestBody != null) {
                if (requestBody instanceof String)
                    requestBuilder.contentType(MediaType.APPLICATION_JSON_UTF8).content((String) requestBody);
                else
                    requestBuilder.contentType(MediaType.APPLICATION_JSON_UTF8).content(gson.toJson(requestBody));
            }
            ResultActions actions = mockMvc.perform(requestBuilder).andExpect(status().is(expectedStatus));
            if (expectedBody != null) {
                actions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
                if (expectedBody instanceof String)
                    actions.andExpect(content().json((String) expectedBody, true));
                else
                    actions.andExpect(content().json(gson.toJson(expectedBody), true));
            }
        } catch (Exception ex) {
            AssertionError err = new AssertionError("Exception occurred.", ex);
            err.setStackTrace(new StackTraceElement[0]);
            throw err;
        }
    }

    protected Message emptyTokenMessage() {
        return new Message(400, "Token must be provided to use this API.");
    }

    protected Message invalidTokenMessage(String tokenID) {
        return new Message(401, "The given token `" + tokenID + "` is invalid for this API or has expired.");
    }

    protected Message entityNotFoundMessage(String entityName, Object entityID) {
        return new Message(404, entityName + " with ID `" + entityID + "` does not exist.");
    }

    protected Message duplicateEntityMessage(String entityName, Object entityID) {
        return new Message(403, entityName + " with ID `" + entityID + "` already exists.");
    }

    protected Message entityCreatedMessage(String entityName, Object entityID, String entityUrl) {
        return new CreatedMessage(entityUrl, entityName + " with ID `" + entityID + "` was created.");
    }

    protected Message entityUpdatedMessage(String entityName, Object entityID, String entityUrl) {
        return new CreatedMessage(entityUrl, entityName + " with ID `" + entityID + "` was updated.");
    }
}
