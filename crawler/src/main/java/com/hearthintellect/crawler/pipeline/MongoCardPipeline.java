package com.hearthintellect.crawler.pipeline;

import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.dao.MechanicRepository;
import com.hearthintellect.model.Card;
import com.hearthintellect.util.LocaleString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Locale;

public class MongoCardPipeline implements Pipeline {
    private static final Logger LOG = LoggerFactory.getLogger(MongoCardPipeline.class);

    private CardRepository cardRepository;
    private MechanicRepository mechanicRepository;

    @Override
    public void process(ResultItems resultItems, Task task) {
        String HHID = resultItems.get("HHID");

        Card card = cardRepository.findByHHID(Integer.valueOf(HHID));

        if (card == null) {
            LOG.debug("Received card that does not exist in database. Creating a new entity...");
            card = new Card();
            processResults(card, resultItems);
            cardRepository.insert(card);
        } else {
            processResults(card, resultItems);
            cardRepository.update(card);
        }
    }

    private void processResults(Card card, ResultItems resultItems) {
        // Process `HHID`
        card.setHHID(resultItems.get("HHID"));

        // Process `locale`
        String localeStr = resultItems.get("locale");
        Locale locale;
        if (localeStr.trim().equalsIgnoreCase("cn"))
            locale = Locale.CHINA;
        else if (localeStr.trim().equalsIgnoreCase("ko"))
            locale = Locale.KOREAN;
        else if (localeStr.trim().equalsIgnoreCase("ru"))
            locale = new Locale("ru");
        else if (localeStr.trim().equalsIgnoreCase("pt"))
            locale = new Locale("pt");
        else if (localeStr.trim().equalsIgnoreCase("it"))
            locale = Locale.ITALIAN;
        else if (localeStr.trim().equalsIgnoreCase("fr"))
            locale = Locale.FRENCH;
        else if (localeStr.trim().equalsIgnoreCase("de"))
            locale = new Locale("de");
        else
            locale = Locale.ENGLISH;

        // Process `name`
        LocaleString name = card.getName();
        if (name == null)
            name = new LocaleString();
        name.put(locale, resultItems.get("name"));
        card.setName(name);

        // Process `effect`
        LocaleString effect = card.getEffect();
        if (effect == null)
            effect = new LocaleString();
        effect.put(locale, resultItems.get("effect"));
        card.setEffect(effect);

        // Process `description`
        LocaleString description = card.getDesc();
        if (description == null)
            description = new LocaleString();
        description.put(locale, resultItems.get("desc"));
        card.setDesc(description);

        // Process statistics
        card.setCost(resultItems.get("cost"));
        card.setAttack(resultItems.get("attack"));
        card.setHealth(resultItems.get("health"));
        card.setImageImageUrl(resultItems.get("imageUrl"));

    }

    public CardRepository getCardRepository() { return cardRepository; }
    public void setCardRepository(CardRepository cardRepository) { this.cardRepository = cardRepository; }
    public MechanicRepository getMechanicRepository() { return mechanicRepository; }
    public void setMechanicRepository(MechanicRepository mechanicRepository) { this.mechanicRepository = mechanicRepository; }
}
