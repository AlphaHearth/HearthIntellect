package com.hearthintellect.dao;

import com.hearthintellect.model.Mechanic;

public interface MechanicRepository {

    Mechanic findById(int mechanicId);
    Mechanic findByHHID(int mechanicHHID);

    Iterable<Mechanic> findAllByName(String name);

    void save(Mechanic mechanic);
    void update(Mechanic mechanic);
    void remove(Mechanic mechanic);

}
