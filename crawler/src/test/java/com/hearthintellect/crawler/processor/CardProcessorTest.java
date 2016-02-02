package com.hearthintellect.crawler.processor;

import com.hearthintellect.config.CrawlerTestConfig;
import com.hearthintellect.model.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.codecraft.webmagic.Spider;

/**
 * Test cases for WebMagic {@link Card} processors
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CrawlerTestConfig.class)
public class CardProcessorTest {

    @Autowired
    private Spider hearthheadCardSpider;

    @Test
    public void testHearthHeadCardProcessor() {
        hearthheadCardSpider.run();
    }

}
