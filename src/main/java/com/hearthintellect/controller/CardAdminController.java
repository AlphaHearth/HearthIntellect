package com.hearthintellect.controller;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.model.Patch;
import com.hearthintellect.repository.CardRepository;
import com.hearthintellect.repository.MechanicRepository;
import com.hearthintellect.repository.PatchRepository;
import com.hearthintellect.repository.TokenRepository;
import com.hearthintellect.utils.CreatedMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;

import static com.hearthintellect.exception.Exceptions.*;
import static com.hearthintellect.utils.LocaleStringUtils.merge;

@RestController
@RequestMapping("/cards")
public class CardAdminController {
    private final TokenRepository tokenRepository;
    private final CardRepository cardRepository;
    private final PatchRepository patchRepository;
    private final MechanicRepository mechanicRepository;

    @Autowired
    public CardAdminController(TokenRepository tokenRepository, CardRepository cardRepository,
                               PatchRepository patchRepository, MechanicRepository mechanicRepository) {
        this.tokenRepository = tokenRepository;
        this.cardRepository = cardRepository;
        this.patchRepository = patchRepository;
        this.mechanicRepository = mechanicRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreatedMessage> createCard(@RequestBody Card card,
                                                     @RequestParam(defaultValue = "") String token) {
        tokenRepository.adminVerify(token);
        String cardId = card.getCardId();
        try {
            cardRepository.insert(card);
        } catch (DuplicateKeyException ex) {
            throw duplicateCardException(cardId);
        }
        return ResponseEntity.created(URI.create("/cards/" + cardId))
                .body(new CreatedMessage("/cards/" + cardId, "Card with ID `" + cardId + "` was created."));
    }

    @RequestMapping(path = "/{cardId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCard(@PathVariable String cardId,
                                     @RequestParam(defaultValue = "") String token) {
        tokenRepository.adminVerify(token);
        if (!cardRepository.exists(cardId))
            throw cardNotFoundException(cardId);
        cardRepository.delete(cardId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{cardId}", method = { RequestMethod.PUT, RequestMethod.PATCH })
    public ResponseEntity<CreatedMessage> updateCard(@PathVariable String cardId, @RequestBody Card card,
                                                     @RequestParam(defaultValue = "") String token) {
        tokenRepository.adminVerify(token);
        Card cardInDB = cardRepository.findOne(cardId);
        if (cardInDB == null)
            throw cardNotFoundException(cardId);
        // Update ID if set
        if (StringUtils.isNotBlank(card.getCardId()) && !cardId.equals(card.getCardId())) {
            if (cardRepository.exists(card.getCardId()))
                throw duplicateCardException(card.getCardId());
            cardInDB.setCardId(card.getCardId());
        }

        // TODO Update the logic for better support of unset primitive fields

        // Update other fields
        cardInDB.setName(merge(cardInDB.getName(), card.getName()));
        cardInDB.setEffect(merge(cardInDB.getEffect(), card.getEffect()));
        cardInDB.setFlavor(merge(cardInDB.getFlavor(), card.getFlavor()));
        cardInDB.setCost(card.getCost());
        cardInDB.setAttack(card.getAttack());
        cardInDB.setHealth(card.getHealth());
        cardInDB.setCollectible(card.isCollectible());
        if (card.getImageUrl() != null)
            cardInDB.setImageUrl(cardInDB.getImageUrl());
        if (card.getHeroClass() != null)
            cardInDB.setHeroClass(card.getHeroClass());
        if (card.getType() != null)
            cardInDB.setType(card.getType());
        if (card.getQuality() != null)
            cardInDB.setQuality(card.getQuality());
        if (card.getSet() != null)
            cardInDB.setSet(card.getSet());
        if (card.getRace() != null)
            cardInDB.setRace(card.getRace());
        if (card.getSincePatch() != null) {
            Patch referencedPatch = patchRepository.findOne(card.getSincePatch().getBuildNum());
            if (referencedPatch == null)
                throw patchNotFoundException(card.getSincePatch().getBuildNum());
            cardInDB.setSincePatch(referencedPatch);
        }
        if (card.getAddedPatch() != null) {
            Patch referencedPatch = patchRepository.findOne(card.getAddedPatch().getBuildNum());
            if (referencedPatch == null)
                throw patchNotFoundException(card.getAddedPatch().getBuildNum());
            cardInDB.setAddedPatch(referencedPatch);
        }

        if (card.getMechanics() != null) {
            cardInDB.setMechanics(new ArrayList<>(card.getMechanics()));
            for (Mechanic mechanic : card.getMechanics()) {
                Mechanic mechanicInDB = mechanicRepository.findOne(mechanic.getMechanicId());
                if (mechanicInDB == null)
                    throw mechanicNotFoundException(mechanic.getMechanicId());
                cardInDB.getMechanics().add(mechanicInDB);
            }
        }

        if (card.getQuotes() != null)
            cardInDB.setQuotes(card.getQuotes());
        if (card.getVersions() != null)
            cardInDB.setVersions(card.getVersions());

        if (StringUtils.isNotBlank(card.getCardId()) && !cardId.equals(card.getCardId()))
            cardRepository.delete(cardId);
        cardRepository.save(cardInDB);
        return ResponseEntity.created(URI.create("/cards/" + cardInDB.getCardId()))
                .body(new CreatedMessage("/cards/" + cardInDB.getCardId(), "Card with ID `" + cardId + "` was updated."));
    }
}
