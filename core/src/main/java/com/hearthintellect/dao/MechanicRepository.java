package com.hearthintellect.dao;

import com.hearthintellect.model.Mechanic;

public interface MechanicRepository extends Repository<Mechanic> {

    Iterable<Mechanic> findAllByName(String name);

}
