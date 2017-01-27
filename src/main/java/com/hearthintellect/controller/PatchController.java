package com.hearthintellect.controller;

import com.hearthintellect.exception.PatchNotFoundException;
import com.hearthintellect.model.Patch;
import com.hearthintellect.repository.PatchRepository;
import com.hearthintellect.utils.SortUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patches")
public class PatchController {

    private final PatchRepository patchRepository;

    @Autowired
    public PatchController(PatchRepository patchRepository) {
        this.patchRepository = patchRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Patch> listPatches(
            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "order", defaultValue = "-buildNum") String order) {
        Page<Patch> requestedPage =
                patchRepository.findAll(new PageRequest(pageNum, pageSize, SortUtils.parseSort(order)));
        return IterableUtils.toList(requestedPage);
    }

    @RequestMapping(path = "/{buildNum}", method = RequestMethod.GET)
    public Patch getPatch(@PathVariable int buildNum) {
        Patch patch = patchRepository.findOne(buildNum);
        if (patch == null)
            throw new PatchNotFoundException(buildNum);
        return patch;
    }
}
