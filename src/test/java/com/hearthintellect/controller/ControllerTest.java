package com.hearthintellect.controller;

import com.google.gson.Gson;
import com.hearthintellect.config.InMemoryMongoConfig;
import com.hearthintellect.config.SpringWebConfig;
import com.hearthintellect.model.Card;
import com.hearthintellect.model.Token;
import com.hearthintellect.model.User;
import com.hearthintellect.repository.CardRepository;
import com.hearthintellect.repository.TokenRepository;
import com.hearthintellect.repository.UserRepository;
import com.hearthintellect.security.PasswordEncoder;
import com.hearthintellect.util.ResourceUtils;
import com.hearthintellect.util.TypeTokens;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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

    @Autowired protected PasswordEncoder passwordEncoder;

    protected MockMvc mockMvc;

    protected List<Card> testCards;
    protected List<User> testUsers;
    protected List<Token> testTokens;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(context).build();

        cardRepository.deleteAll();
        testCards = ResourceUtils.readResrouceJsonAsEntity("cards.json", TypeTokens.cardListType, gson);
        assertThat(testCards.isEmpty(), is(false));
        cardRepository.save(testCards);

        userRepository.deleteAll();
        testUsers = ResourceUtils.readResrouceJsonAsEntity("users.json", TypeTokens.userListType, gson);
        assertThat(testUsers.isEmpty(), is(false));
        testUsers.forEach((u) -> {
            User userCopy = new User(u.getUsername(), u.getEmail(), u.getNickname(), u.getPassword());
            passwordEncoder.encodeUserPassword(userCopy);
            userRepository.save(userCopy);
        });

        tokenRepository.deleteAll();
        testTokens = ResourceUtils.readResrouceJsonAsEntity("tokens.json", TypeTokens.tokenListType, gson);
        assertThat(testTokens.isEmpty(), is(false));
        tokenRepository.save(testTokens);
    }
}
