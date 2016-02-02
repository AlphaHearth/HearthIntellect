package com.hearthintellect.crawler.scheduler;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.util.concurrent.locks.ReentrantLock;

public class CardScheduler implements Scheduler {
    private static final Logger LOG = LoggerFactory.getLogger(CardScheduler.class);

    private MongoCursor iter;
    private ReentrantLock lock = new ReentrantLock();

    public CardScheduler() {}

    public CardScheduler(MongoClient client) {
        MongoCollection<Document> cards = client.getDatabase("hearthstone").getCollection("cards");
        iter = cards.find().iterator();
    }

    @Override
    public void push(Request request, Task task) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Request poll(Task task) {
        lock.lock();
        if (iter.hasNext()) {
            Document doc = (Document) iter.next();
            int nextHHID = doc.getInteger("HHID");
            String nextUrl = "http://www.hearthhead.com/card=" + nextHHID;
            if (LOG.isDebugEnabled())
                LOG.debug("Next card to fetch: " + nextHHID);
            lock.unlock();
            return new Request(nextUrl);
        }
        lock.unlock();

        LOG.debug("All cards set for the scheduler were polled. Returning null");
        return null;
    }
}
