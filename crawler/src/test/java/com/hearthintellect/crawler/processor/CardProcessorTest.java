package com.hearthintellect.crawler.processor;

import com.hearthintellect.config.CrawlerTestConfig;
import com.hearthintellect.model.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test cases for WebMagic {@link Card} processors
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CrawlerTestConfig.class)
public class CardProcessorTest {

    @Test
    public void testHearthHeadCardProcessor() {

    }

}
