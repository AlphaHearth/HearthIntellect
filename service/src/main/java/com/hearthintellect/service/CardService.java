package com.hearthintellect.service;

import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.model.Card;
import com.hearthintellect.utils.Page;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;

import static com.hearthintellect.utils.RsResponseUtils.notFound;
import static com.hearthintellect.utils.RsResponseUtils.ok;

/**
 * JAX-RS service class for {@link Card}.
 */
@Produces(MediaType.APPLICATION_JSON)
public class CardService {
    private static final Logger LOG = LoggerFactory.getLogger(CardService.class);

    private CardRepository cardRepository;

    @GET
    @Path("/cards")
    public Response listCards(@DefaultValue("0") @QueryParam("pageNum") int pageNum,
                              @DefaultValue("20") @QueryParam("pageSize") int pageSize,
                              @QueryParam("order") String order) {
        Iterator<Card> cards = cardRepository.findAll(order, new Page(pageNum, pageSize));
        JSONArray result = new JSONArray();
        while (cards.hasNext())
            result.put(cards.next().toBriefJson());
        return ok(result);
    }

    @GET
    @Path("/cards/{cardId: [0-9]+}")
    public Response getCard(@DefaultValue("-1") @PathParam("cardId") int cardId) {
        Card card = cardRepository.findById(cardId);
        if (card != null)
            return ok(card.toJson());
        return notFound();
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }
    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
}
