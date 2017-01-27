package com.hearthintellect.controller;

import com.hearthintellect.exception.DuplicatePatchException;
import com.hearthintellect.exception.PatchNotFoundException;
import com.hearthintellect.model.Card;
import com.hearthintellect.model.HistoryCard;
import com.hearthintellect.model.Patch;
import com.hearthintellect.repository.CardRepository;
import com.hearthintellect.repository.PatchRepository;
import com.hearthintellect.repository.TokenRepository;
import com.hearthintellect.utils.CreatedMessage;
import com.hearthintellect.utils.LocaleStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/patches")
public class PatchAdminController {

    private final CardRepository cardRepository;
    private final PatchRepository patchRepository;
    private final TokenRepository tokenRepository;

    @Autowired
    public PatchAdminController(CardRepository cardRepository, PatchRepository patchRepository,
                                TokenRepository tokenRepository) {
        this.cardRepository = cardRepository;
        this.patchRepository = patchRepository;
        this.tokenRepository = tokenRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreatedMessage> createPatch(@RequestBody Patch patch,
                                                      @RequestParam(defaultValue = "") String token) {
        tokenRepository.adminVerify(token);
        int buildNum = patch.getBuildNum();
        if (patchRepository.exists(buildNum))
            throw new DuplicatePatchException(buildNum);
        patchRepository.save(patch);
        return ResponseEntity.created(URI.create("/patches/" + buildNum))
                .body(new CreatedMessage("/patches/" + buildNum, "Patch with ID `" + buildNum + "` was created."));
    }

    @RequestMapping(path = "/{buildNum}", method = RequestMethod.DELETE)
    public ResponseEntity deletePatch(@PathVariable int buildNum,
                                      @RequestParam(defaultValue = "") String token) {
        tokenRepository.adminVerify(token);
        if (!patchRepository.exists(buildNum))
            throw new PatchNotFoundException(buildNum);
        patchRepository.delete(buildNum);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{buildNum}", method = { RequestMethod.PUT, RequestMethod.PATCH })
    public ResponseEntity<CreatedMessage> updatePatch(@PathVariable int buildNum, @RequestBody Patch patch,
                                                      @RequestParam(defaultValue = "") String token) {
        tokenRepository.adminVerify(token);
        Patch patchInDB = patchRepository.findOne(buildNum);
        if (patchInDB == null)
            throw new PatchNotFoundException(buildNum);

        // Update ID if set
        if (patch.getBuildNum() != 0 && patch.getBuildNum() != buildNum) {
            if (patchRepository.exists(patch.getBuildNum()))
                throw new DuplicatePatchException(patch.getBuildNum());
            patchInDB.setBuildNum(patch.getBuildNum());
        }

        // Update other fields
        if (patch.getPatchCode() != null)
            patchInDB.setPatchCode(patch.getPatchCode());
        if (patch.getReleaseDate() != null)
            patchInDB.setReleaseDate(patch.getReleaseDate());
        patchInDB.setReleaseNote(LocaleStringUtils.merge(patchInDB.getReleaseNote(), patch.getReleaseNote()));

        patchRepository.save(patchInDB);

        // Update reference to this patch if ID is changed
        if (patch.getBuildNum() != 0 && patch.getBuildNum() != buildNum) {
            for (Card card : cardRepository.findAll()) {
                if (card.getAddedPatch() != null && card.getAddedPatch().getBuildNum() == buildNum)
                    card.setAddedPatch(patchInDB);
                if (card.getSincePatch() != null && card.getSincePatch().getBuildNum() == buildNum)
                    card.setSincePatch(patchInDB);
                for (HistoryCard version : card.getVersions())
                    if (version.getSincePatch() != null && version.getSincePatch().getBuildNum() == buildNum)
                        version.setSincePatch(patchInDB);
                cardRepository.save(card);
            }
            patchRepository.delete(buildNum);
        }

        return ResponseEntity.created(URI.create("/patches/" + buildNum))
                .body(new CreatedMessage("/patches/" + buildNum, "Patch with ID `" + buildNum + "` was updated."));
    }

}
