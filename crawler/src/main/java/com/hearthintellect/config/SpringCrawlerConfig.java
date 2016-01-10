package com.hearthintellect.config;

import com.hearthintellect.crawler.pipeline.MongoCardPipeline;
import com.hearthintellect.crawler.processor.HearthHeadCardProcessor;
import com.hearthintellect.crawler.scheduler.CardScheduler;
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
    public Scheduler cardScheduler() {
        CardScheduler scheduler = new CardScheduler();

        return scheduler;
    }

    @Bean
    public PageProcessor hearthheadCardProcessor() {
        return new HearthHeadCardProcessor();
    }

    @Bean
    public Spider hearthheadCardSpider(Scheduler cardScheduler,
                                       PageProcessor hearthheadCardProcessor,
                                       Pipeline cardPipeline) {
        return Spider.create(hearthheadCardProcessor)
                     .setScheduler(cardScheduler)
                     .addPipeline(cardPipeline)
                     .thread(5);
    }

}
