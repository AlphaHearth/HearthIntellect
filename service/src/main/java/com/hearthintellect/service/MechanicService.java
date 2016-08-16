package com.hearthintellect.service;

import com.hearthintellect.dao.MechanicRepository;
import com.hearthintellect.model.Mechanic;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.hearthintellect.util.RsResponseUtils.ok;

/**
 * JAX-RS service class for {@link Mechanic}.
 */
@Produces(MediaType.APPLICATION_JSON)
public class MechanicService {
    private static final Logger LOG = LoggerFactory.getLogger(MechanicService.class);

    private MechanicRepository mechanicRepository;

    @GET
    @Path("/mechanics")
    public Response listMechanics() {
        JSONArray jsonArr = new JSONArray();
        for (Mechanic mechanic : mechanicRepository.findAll())
            jsonArr.put(mechanic.toJson());

        return ok(jsonArr);
    }

    public MechanicRepository getMechanicRepository() {
        return mechanicRepository;
    }

    public void setMechanicRepository(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

}
