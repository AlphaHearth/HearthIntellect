package com.hearthintellect.crawler.pipeline;

import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.model.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MongoCardPipeline implements Pipeline {
    private static final Logger LOG = LoggerFactory.getLogger(MongoCardPipeline.class);

    private CardRepository cardRepository;

    @Override
    public void process(ResultItems resultItems, Task task) {
        String HHID = resultItems.get("HHID");

        Card card = cardRepository.findByHHID(Integer.valueOf(HHID));

        if (card == null) {
            LOG.debug("Received card that does not exist in database, ignoring...");
            return;
        }

        card.setDesc(resultItems.get("desc"));
        card.setQuotes(resultItems.get("quotes"));

        cardRepository.update(card);
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }

    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
}
