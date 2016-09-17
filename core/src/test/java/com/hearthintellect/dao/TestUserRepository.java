package com.hearthintellect.dao;

import com.hearthintellect.config.SpringCoreTestConfig;
import com.hearthintellect.config.SpringMongoConfig;
import com.hearthintellect.model.MongoEntity;
import com.hearthintellect.model.User;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for {@link UserRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfig.class)
public class TestUserRepository {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoClient client;

    private String userId;
    private String nickname = "Nickle Back";
    private String password = "I\'m a password";

    @Before
    public void setUp() {
        MongoCollection<Document> users =
            client.getDatabase(SpringMongoConfig.DATABASE_NAME).getCollection("users");

        // Find a unique random ID for test
        userId = UUID.randomUUID().toString();
        while (users.count(new Document("_id", userId)) != 0)
            userId = UUID.randomUUID().toString();
    }

    @After
    public void cleanUp() {
        MongoCollection<Document> users =
            client.getDatabase(SpringMongoConfig.DATABASE_NAME).getCollection("users");

        // Delete the inserted user
        users.deleteOne(new Document("_id", userId));
    }

    /**
     * Unit test for {@link Repository#insert(MongoEntity)} on {@link User} type
     */
    @Test
    public void testUserDaoInsert() {
        MongoCollection<Document> users =
            client.getDatabase(SpringMongoConfig.DATABASE_NAME).getCollection("users");

        User user = new User(userId, nickname, password);

        // Insert the user via the testing method
        userRepository.insert(user);

        // Verify the result
        Document userInDB = users.find(new Document("_id", userId)).first();
        assertNotNull(userInDB);
        assertThat(userInDB.getString("nickname"), is(nickname));
        assertThat(userInDB.getString("password"), is(password));
    }

    /**
     * Unit test for {@link Repository#delete(MongoEntity)} on {@link User} type
     * and {@link UserRepository#findByEmail(String)}
     */
    @Test
    public void testUserDelete() {
        MongoCollection<Document> users =
            client.getDatabase(SpringMongoConfig.DATABASE_NAME).getCollection("users");

        Document userDoc =
            new Document("_id", userId).append("nickname", nickname).append("password", password);
        users.insertOne(userDoc);

        // Delete the user via the testing method
        User user = new User(userId, "anything", "anything");
        userRepository.delete(user);

        assertThat(users.count(new Document("_id", userId)), is(0L));
    }

    /**
     * Unit test for {@link Repository#findById(Object)} on {@link User} type
     */
    @Test
    public void testUserFindById() {
        MongoCollection<Document> users =
            client.getDatabase(SpringMongoConfig.DATABASE_NAME).getCollection("users");

        Document userDoc =
            new Document("_id", userId).append("nickname", nickname).append("password", password);
        users.insertOne(userDoc);

        // Query via the testing method
        User user = userRepository.findById(userId);
        assertThat(user.getId(), is(userId));
        assertThat(user.getEmail(), is(userId));
        assertThat(user.getNickname(), is(nickname));
        assertThat(user.getPassword(), is(password));

        user = userRepository.findByEmail(userId);
        assertThat(user.getId(), is(userId));
        assertThat(user.getEmail(), is(userId));
        assertThat(user.getNickname(), is(nickname));
        assertThat(user.getPassword(), is(password));
    }

    /**
     * Unit test for {@link Repository#update(MongoEntity)} on {@link User} type
     */
    @Test
    public void testUserUpdate() {
        MongoCollection<Document> users =
            client.getDatabase(SpringMongoConfig.DATABASE_NAME).getCollection("users");

        Document userDoc =
            new Document("_id", userId).append("nickname", nickname).append("password", password);
        users.insertOne(userDoc);

        // Update via the testing method
        String newNickname = nickname.concat("Robert Peng");
        String newPassword = password.concat("This is a password");
        User user = new User(userId, newNickname, newPassword);
        userRepository.update(user);

        // Verify the result
        Document userInDB = users.find(new Document("_id", userId)).first();
        assertThat(userInDB, notNullValue());
        assertThat(userInDB.getString("_id"), is(userId));
        assertThat(userInDB.getString("nickname"), is(newNickname));
        assertThat(userInDB.getString("password"), is(newPassword));
    }

}
