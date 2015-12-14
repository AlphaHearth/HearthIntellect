package com.hearthintellect.config;

import com.hearthintellect.crawler.pipeline.MongoCardPipeline;
import com.hearthintellect.crawler.processor.HearthHeadCardProcessor;
import com.hearthintellect.crawler.scheduler.MongoCardScheduler;
import com.hearthintellect.dao.CardRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * Spring configuration file for WebMagic crawlers
 */
@Configuration
@Import(SpringCoreConfig.class)
public class SpringCrawlerConfig {

    @Bean
    public Pipeline cardPipeline(CardRepository cardRepository) {
        MongoCardPipeline pipeline = new MongoCardPipeline();
        pipeline.setCardRepository(cardRepository);

        return pipeline;
    }

    @Bean
    public Scheduler cardScheduler(CardRepository cardRepository) {
        MongoCardScheduler scheduler = new MongoCardScheduler();
        scheduler.setCardRepository(cardRepository);

        return scheduler;
    }

    @Bean
    public PageProcessor hearthheadCardProcessor() {
        return new HearthHeadCardProcessor();
    }

    @Bean
    public Spider hearthheadCardSpider(CardRepository cardRepository) {
        return Spider.create(hearthheadCardProcessor())
                     .setScheduler(cardScheduler(cardRepository))
                     .addPipeline(cardPipeline(cardRepository))
                     .thread(5);
    }

}
