package com.hearthintellect.crawler.processor;

import com.hearthintellect.crawler.pipeline.MongoCardPipeline;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Card {@link Pipeline} for JUnit test cases
 */
public class JUnitCardPipeline extends MongoCardPipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {

    }
}
