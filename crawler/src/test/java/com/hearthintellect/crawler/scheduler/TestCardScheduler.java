package com.hearthintellect.crawler.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.util.concurrent.locks.ReentrantLock;

/**
 * {@link Scheduler} for generating test pages
 */
public class TestCardScheduler extends QueueScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(TestCardScheduler.class);

    private int[] candidateHHID = new int[]{ 32, 447, 530, 858, 1123, 1805, 1986, 1990, 1997, 2007, 2286, 2655, 2826 };
    private int iter = 0;
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public Request poll(Task task) {

        lock.lock();
        if (iter < candidateHHID.length) {
            int nextHHID = candidateHHID[iter++];
            lock.unlock();
            String nextUrl = "http://www.hearthhead.com/card=" + nextHHID;
            LOG.debug("Next card to fetch: " + nextHHID);
            return new Request(nextUrl);
        }
        lock.unlock();

        LOG.debug("All cards set for the scheduler were polled, return new requests added while crawling");
        return super.poll(task);
    }

}
