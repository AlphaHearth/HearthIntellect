package com.hearthintellect.service;

import com.google.gson.Gson;
import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.model.Card;
import com.hearthintellect.utils.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired private CardRepository cardRepository;
    @Autowired private Gson gson;

    // TODO Card Search Feature
    // 1. Search with card name  -> Text Search with MongoDB & Morphia
    // 2. Cards with specific Mechanic(ID)
    // 3. Browsing filter. Supported fields include:
    //    - Set(ComboBox), Type(ComboBox), Race(ComboBox), Quality(CheckBox)
    //    - Cost, Attack, Health, Durability(Range)

    @GET
    @Path("/cards")
    public Response listCards(@DefaultValue("0") @QueryParam("pageNum") int pageNum,
                              @DefaultValue("20") @QueryParam("pageSize") int pageSize,
                              @QueryParam("order") String order) {
        Iterator<Card> cards = cardRepository.findAll(order, new Page(pageNum, pageSize));
        return ok(gson.toJson(cards));
    }

    @GET
    @Path("/cards/{cardId: [0-9]+}")
    public Response getCard(@DefaultValue("") @PathParam("cardId") String cardId) {
        Card card = cardRepository.findById(cardId);
        if (card != null)
            return ok(gson.toJson(card));
        return notFound("Card with ID `" + cardId + "` does not exist.");
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }
    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
}
