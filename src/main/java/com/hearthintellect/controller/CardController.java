package com.hearthintellect.controller;

import com.hearthintellect.controller.exception.CardNotFoundException;
import com.hearthintellect.model.Card;
import com.hearthintellect.repository.CardRepository;
import com.hearthintellect.utils.SortUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        Page<Card> requestedPage = cardRepository.findAll(new PageRequest(pageNum, pageSize, SortUtils.parseSort(order)));
        return IterableUtils.toList(requestedPage);
    }

    @RequestMapping(path = "/{cardId}", method = RequestMethod.GET)
    public Card getCard(@PathVariable String cardId) {
        Card card = cardRepository.findOne(cardId);
        if (card == null)
            throw new CardNotFoundException(cardId);
        return card;
    }
}
