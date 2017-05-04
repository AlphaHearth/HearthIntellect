package com.hearthintellect.repository;

import com.hearthintellect.config.InMemoryMongoConfig;
import com.hearthintellect.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoryMongoConfig.class)
public class UserRepositoryTest {
    private static final String TEST_ID = "TEST_USER";

    private @Autowired UserRepository userRepository;

    private User testUser;

    @Test
    public void testCreatingDuplicateUser() {
        testUser = new User(TEST_ID, "ccwindy@example.com", "", "");
        userRepository.save(testUser);
        testUser = new User(TEST_ID, "robert@example.com", "", "");
        try {
            userRepository.insert(testUser); // Exception on creating duplicate user should be thrown
            fail("Duplicate user was created!!!");
        } catch (Exception e) {
            assertThat(e, instanceOf(DuplicateKeyException.class));
        }
        testUser = userRepository.findOne(TEST_ID);
        assertThat(testUser.getEmail(), is("ccwindy@example.com"));
    }
}