package com.hearthintellect.config;

import com.hearthintellect.crawler.pipeline.JUnitCardPipeline;
import com.hearthintellect.crawler.scheduler.CardScheduler;
import com.hearthintellect.crawler.scheduler.CardTestScheduler;
import com.hearthintellect.dao.CardRepository;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * Spring configuration file for crawler test cases
 */
@Configuration
@Import(SpringCoreConfig.class)
public class CrawlerTestConfig extends SpringCrawlerConfig {

    @Bean
    @Override
    public Scheduler cardScheduler(MongoClient mongoClient) {
        CardScheduler scheduler = new CardTestScheduler();

        return scheduler;
    }

    @Bean
    @Override
    public Pipeline cardPipeline(CardRepository cardRepository) {
        JUnitCardPipeline pipeline = new JUnitCardPipeline();
        pipeline.setCardRepository(cardRepository);

        return pipeline;
    }

}
