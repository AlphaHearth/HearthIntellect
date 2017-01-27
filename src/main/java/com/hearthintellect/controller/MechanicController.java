package com.hearthintellect.controller;

import com.hearthintellect.model.Mechanic;
import com.hearthintellect.repository.MechanicRepository;
import com.hearthintellect.utils.SortUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hearthintellect.exception.Exceptions.mechanicNotFoundException;

@RestController
@RequestMapping("/mechanics")
public class MechanicController {

    private final MechanicRepository mechanicRepository;

    @Autowired
    public MechanicController(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Mechanic> listMechanics() {
        Iterable<Mechanic> requestedPage = mechanicRepository.findAll(SortUtils.parseSort("mechanicId"));
        return IterableUtils.toList(requestedPage);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Mechanic getMechanic(@PathVariable String id) {
        Mechanic mechanic = mechanicRepository.findOne(id);
        if (mechanic == null)
            throw mechanicNotFoundException(id);
        return mechanic;
    }
}
