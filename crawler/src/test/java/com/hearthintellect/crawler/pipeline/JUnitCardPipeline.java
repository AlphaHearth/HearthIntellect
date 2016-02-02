package com.hearthintellect.crawler.pipeline;

import com.hearthintellect.model.CardQuote;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Card {@link Pipeline} for JUnit test cases
 */
public class JUnitCardPipeline extends MongoCardPipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        if (resultItems.get("HHID").equals(32)) {
            // http://www.hearthhead.com/card=32/alakir-the-windlord
            assertThat(resultItems.get("name"), is("Al'Akir the Windlord"));
            assertThat(resultItems.get("effect"), is("Windfury, Charge, Divine Shield, Taunt"));
            assertThat(resultItems.get("desc"), is("He is the weakest of the four Elemental Lords.  And the other three don't let him forget it."));
            assertThat(resultItems.get("mechanics"), hasItems(2, 5, 15, 17));

            List<CardQuote> quotes = resultItems.get("quotes");
            for (CardQuote quote : quotes) {
                switch (quote.getType()) {
                    case Play:
                        assertThat(quote.getLine(), is("Winds! Obey my command!"));
                        break;
                    case Attack:
                        assertThat(quote.getLine(), is("Like swatting insects!"));
                        break;
                    case Death:
                        assertThat(quote.getLine(), is(""));
                        break;
                    default:
                        assertFalse(true);
                }
            }
        } else if (resultItems.get("HHID").equals(447)) {
            // http://www.hearthhead.com/card=447/arcane-explosion
            assertThat(resultItems.get("name"), is("Arcane Explosion"));
            assertThat(resultItems.get("effect"), is("Deal 1 damage to all enemy minions."));
            assertThat(resultItems.get("desc"), is(""));
            assertThat(resultItems.get("mechanics"), hasItems(64, 66));
        }
    }
}
