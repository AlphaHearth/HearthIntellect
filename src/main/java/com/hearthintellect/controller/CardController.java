package com.hearthintellect.controller;

import com.hearthintellect.controller.exception.CardNotFoundException;
import com.hearthintellect.model.Card;
import com.hearthintellect.repository.CardRepository;
import com.hearthintellect.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardRepository cardRepository;

    @Autowired
    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    // TODO Card Search Feature
    // 1. Search with card name
    // 2. Browsing filter. Supported fields include:
    //    - Set(ComboBox), Type(ComboBox), Race(ComboBox), Quality(CheckBox)
    //    - Cost, Attack, Health, Durability(Range)
    // 3. Cards with specific Mechanic(ID)

    @RequestMapping(method = RequestMethod.GET)
    public List<Card> listCards(
            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "order", defaultValue = "") String order) {
        return cardRepository.findAll(order, new Page(pageNum, pageSize));
    }

    @RequestMapping(path = "/{cardId}", method = RequestMethod.GET)
    public Card getCard(@PathVariable String cardId) {
        Card card = cardRepository.findById(cardId);
        if (card == null)
            throw new CardNotFoundException(cardId);
        return card;
    }
}
