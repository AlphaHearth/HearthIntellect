package com.hearthintellect.crawler.scheduler;

import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.model.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class MongoCardScheduler extends QueueScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(MongoCardScheduler.class);

    private CardRepository cardRepository;
    private Iterator<Card> cardCursor;

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public Request poll(Task task) {
        if (cardCursor == null)
            cardCursor = cardRepository.findAll();

        lock.lock();
        if (cardCursor.hasNext()) {
            Card nextCard = cardCursor.next();
            String nextUrl = "http://www.hearthhead.com/card=" + nextCard.getHHID();
            LOG.debug("Next card fetched from MongoDB: " + nextCard.getName() + " - " + nextUrl);
            return new Request(nextUrl);
        }
        lock.unlock();

        LOG.debug("All cards in MongoDB are fetched, return new requests added while crawling");
        return super.poll(task);
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }

    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
}
