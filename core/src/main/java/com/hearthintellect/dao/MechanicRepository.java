package com.hearthintellect.dao;

import com.hearthintellect.model.Mechanic;

public interface MechanicRepository {

    Mechanic findById(int mechanicId);

    Iterable<Mechanic> findAllByName(String name);

    void save(Mechanic mechanic);
    void update(Mechanic mechanic);
    void remove(Mechanic mechanic);

}
