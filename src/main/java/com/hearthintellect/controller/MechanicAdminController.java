package com.hearthintellect.controller;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.repository.CardRepository;
import com.hearthintellect.repository.MechanicRepository;
import com.hearthintellect.repository.TokenRepository;
import com.hearthintellect.utils.CreatedMessage;
import com.hearthintellect.utils.LocaleStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.hearthintellect.exception.Exceptions.duplicateMechanicException;
import static com.hearthintellect.exception.Exceptions.mechanicNotFoundException;

@RestController
@RequestMapping("/mechanics")
public class MechanicAdminController {

    private final CardRepository cardRepository;
    private final TokenRepository tokenRepository;
    private final MechanicRepository mechanicRepository;

    @Autowired
    public MechanicAdminController(CardRepository cardRepository, TokenRepository tokenRepository,
                                   MechanicRepository mechanicRepository) {
        this.cardRepository = cardRepository;
        this.tokenRepository = tokenRepository;
        this.mechanicRepository = mechanicRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreatedMessage> createMechanic(@RequestBody Mechanic mechanic,
                                                         @RequestParam(defaultValue = "") String token) {
        tokenRepository.adminVerify(token);
        String mechanicId = mechanic.getMechanicId();
        if (mechanicRepository.exists(mechanicId))
            throw duplicateMechanicException(mechanicId);
        mechanicRepository.save(mechanic);
        return ResponseEntity.created(URI.create("/mechanics/" + mechanicId))
                .body(new CreatedMessage("/mechanics/" + mechanicId, "Mechanic with ID `" + mechanicId + "` was created."));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMechanic(@PathVariable String id, @RequestParam(defaultValue = "") String token) {
        tokenRepository.adminVerify(token);
        if (!mechanicRepository.exists(id))
            throw mechanicNotFoundException(id);

        // TODO Return error message if this mechanic is referenced by some cards

        mechanicRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}", method = { RequestMethod.PUT, RequestMethod.PATCH })
    public ResponseEntity<CreatedMessage> updateMechanic(@PathVariable String id, @RequestBody Mechanic mechanic,
                                                         @RequestParam(defaultValue = "") String token) {
        tokenRepository.adminVerify(token);
        Mechanic mechanicInDB = mechanicRepository.findOne(id);
        if (mechanicInDB == null)
            throw mechanicNotFoundException(id);

        // Update ID if set
        if (StringUtils.isNotBlank(mechanic.getID()) && !id.equals(mechanic.getID())) {
            if (mechanicRepository.exists(mechanic.getID()))
                throw duplicateMechanicException(mechanic.getID());
            mechanicInDB.setID(mechanic.getID());
        }
        // Update other fields
        mechanicInDB.setName(LocaleStringUtils.merge(mechanicInDB.getName(), mechanic.getName()));
        mechanicInDB.setDescription(LocaleStringUtils.merge(mechanicInDB.getDescription(), mechanic.getDescription()));

        mechanicRepository.save(mechanicInDB);
        if (StringUtils.isNotBlank(mechanic.getID()) && !id.equals(mechanic.getID())) {
            for (Card card : cardRepository.findByMechanic(id)) {
                List<Mechanic> mechanics = card.getMechanics();
                for (int i = 0; i < mechanics.size(); i++) {
                    if (mechanics.get(i).getID().equals(id)) {
                        mechanics.set(i, mechanicInDB);
                        cardRepository.save(card);
                        break;
                    }
                }
            }
            mechanicRepository.delete(id);
        }
        return ResponseEntity.created(URI.create("/mechanics/" + mechanicInDB.getID()))
                .body(new CreatedMessage("/mechanics/" + mechanicInDB.getID(), "Mechanic with ID `" + id + "` was updated."));
    }
}
