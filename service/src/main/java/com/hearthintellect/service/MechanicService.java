package com.hearthintellect.service;

import com.hearthintellect.dao.MechanicRepository;
import com.hearthintellect.model.Mechanic;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;

/**
 * JAX-RS service class for {@link Mechanic}.
 */
@Produces("application/json")
public class MechanicService {
    private static final Logger LOG = LoggerFactory.getLogger(MechanicService.class);

    @Autowired
    private MechanicRepository mechanicRepository;

    @GET
    @Path("/mechanics")
    public String listMechanics() {
        JSONArray jsonArr = new JSONArray();
        for (Mechanic mechanic : mechanicRepository.findAll())
            jsonArr.put(mechanic.toJson());

        return jsonArr.toString();
    }

    public MechanicRepository getMechanicRepository() {
        return mechanicRepository;
    }

    public void setMechanicRepository(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

}
