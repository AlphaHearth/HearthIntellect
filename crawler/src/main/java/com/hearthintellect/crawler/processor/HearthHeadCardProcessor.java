package com.hearthintellect.crawler.processor;

import com.hearthintellect.config.SpringCrawlerConfig;
import com.hearthintellect.model.CardQuote;
import com.hearthintellect.util.LocaleString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * WebMagic PageProcessor for a HearthHead card page.
 * <p>
 * For example, see <code>http://www.hearthhead.com/card=32</code>
 */
public class HearthHeadCardProcessor implements PageProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(HearthHeadCardProcessor.class);

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    /**
     * Extracts card information from the given page.
     * <p>
     * Fields to be extracted include `name`, `effect`, `desc`, `mechanics`, `quotes`
     */
    @Override
    public void process(Page page) {
        if (page.getStatusCode() != 200)
            page.setSkip(true);

        // Extract `HHID`
        String HHID = page.getUrl().regex(".*\\.hearthhead\\.com/card=(\\d)/.*").toString();
        if (LOG.isDebugEnabled())
            LOG.debug("Processing HearthHead card page for HHID=" + HHID);
        page.putField("HHID", HHID);

        // Extract locale
        String locale = page.getUrl().regex(".*//(\\w)\\.hearthhead.*").toString();
        if (LOG.isDebugEnabled())
            LOG.debug("Subdomain of the page is `" + locale + "`");
        page.putField("locale", locale);

        // Extract `name`
        String name = page.getHtml().xpath("//*[@id=\"main-contents\"]/div[2]/h1/text")
                          .toString();
        if (LOG.isDebugEnabled())
            LOG.debug("The name of the card is `" + name + "`");
        page.putField("name", name);

        // Extract `desc` from `noscript` element
        String noscript = page.getHtml().xpath("//*[@id=\"main-contents\"]/div[2]/noscript")
                              .toString();
        int descStart = noscript.indexOf("<i>");
        int descEnd = noscript.indexOf("</i>", descStart);
        String desc = noscript.substring(descStart + 3, descEnd);
        if (LOG.isDebugEnabled())
            LOG.debug("The description of the card is `" + desc + "`");
        page.putField("desc", desc);

        // Extract card quotes from script and HTML elements
        List<CardQuote> quotes = new ArrayList<>();
        String script = page.getHtml().xpath("//*[@id=\"main-contents\"]/div[2]/script[2]").toString();
        int idx = 0;
        for (int i = 0; ; i++) {
            String cardSoundLink = page.getHtml().xpath("//*[@id=\"cardsound" + i + "\"]/source[1]/src").toString();
            if (cardSoundLink == null)
                break;
            String cardSoundType = page.getHtml().xpath("//*[@id=\"cardsoundlink" + i + "\"]/text").toString();

            LocaleString quote = new LocaleString();

            int temp = script.indexOf("'cardsound" + i + "');\n", idx);
            if (temp == -1) {
                if (LOG.isDebugEnabled())
                    LOG.debug("Quote line for card sound " + i + " not found: ");
                quote.put(Locale.getDefault(), "");
                quotes.add(new CardQuote(CardQuote.Type.valueOf(cardSoundType), quote, cardSoundLink));
                continue;
            }

            idx = temp;
            int firstNL = script.indexOf("\n", idx);
            int secondNL = script.indexOf("\n", firstNL + 1);
            int quoteStart = script.indexOf("<i>", firstNL);

            if (quoteStart == -1 || quoteStart >= secondNL) {
                if (LOG.isDebugEnabled())
                    LOG.debug("Quote line for card sound " + i + " not found: ");
                quote.put(Locale.getDefault(), "");
                quotes.add(new CardQuote(CardQuote.Type.valueOf(cardSoundType), quote, cardSoundLink));
                continue;
            }

            int quoteEnd = script.indexOf("</i>", quoteStart);
            String cardSoundLine = script.substring(quoteStart + 3, quoteEnd);

            if (LOG.isDebugEnabled())
                LOG.debug("Complete card sound fetched: \nType: " + cardSoundType + "\nLink: " + cardSoundLink + "\nLine: " + cardSoundLine);
            quote.put(Locale.getDefault(), cardSoundLine);
            quotes.add(new CardQuote(CardQuote.Type.valueOf(cardSoundType), quote, cardSoundLink));
        }
        page.putField("quotes", quotes);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        ApplicationContext context =
            new AnnotationConfigApplicationContext(SpringCrawlerConfig.class);

        Spider spider = (Spider) context.getBean("hearthheadCardSpider");
        spider.run();
    }
}
