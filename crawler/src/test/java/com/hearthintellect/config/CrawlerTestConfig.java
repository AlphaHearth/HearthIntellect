package com.hearthintellect.config;

import com.hearthintellect.crawler.processor.JUnitCardPipeline;
import com.hearthintellect.dao.CardRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Spring configuration file for crawler test cases
 */
@Configuration
@Import(SpringCoreConfig.class)
public class CrawlerTestConfig extends SpringCrawlerConfig {

    @Bean
    @Override
    public Pipeline cardPipeline(CardRepository cardRepository) {
        JUnitCardPipeline pipeline = new JUnitCardPipeline();
        pipeline.setCardRepository(cardRepository);

        return pipeline;
    }

}
