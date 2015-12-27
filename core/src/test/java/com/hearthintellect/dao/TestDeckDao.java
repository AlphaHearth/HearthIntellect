package com.hearthintellect.dao;

import com.hearthintellect.config.SpringMongoConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for {@link DeckRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMongoConfig.class)
public class TestDeckDao {
}
