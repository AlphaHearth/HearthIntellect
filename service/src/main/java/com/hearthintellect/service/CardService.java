package com.hearthintellect.service;

import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.model.Card;
import com.hearthintellect.util.Page;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.Iterator;

/**
 * JAX-RS service class for {@link Card}.
 */
@Produces("application/json")
public class CardService {
    private static final Logger LOG = LoggerFactory.getLogger(CardService.class);

    @Autowired
    private CardRepository cardRepository;

    @GET
    @Path("/cards")
    public String listCards(@DefaultValue("0") @QueryParam("pageNum") int pageNum,
                                @DefaultValue("20") @QueryParam("pageSize") int pageSize,
                                @QueryParam("order") String order) {
        Iterator<Card> cards = cardRepository.findAll(order, new Page(pageNum, pageSize));
        JSONArray result = new JSONArray();
        while (cards.hasNext())
            result.put(cards.next().toJson());
        return result.toString();
    }

    @GET
    @Path("/cards/{cardId}")
    public String getCard(@DefaultValue("-1") @PathParam("cardId") int cardId) {
        if (cardId < 0)
            return listCards(0, 20, null);

        Card card = cardRepository.findById(cardId);
        return card.toJson().toString();
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }
    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
}
