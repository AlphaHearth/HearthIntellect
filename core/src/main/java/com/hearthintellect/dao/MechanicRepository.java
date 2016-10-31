package com.hearthintellect.dao;

import com.hearthintellect.model.Mechanic;
import com.hearthintellect.utils.Page;

public interface MechanicRepository extends Repository<Integer, Mechanic> {

    Iterable<Mechanic> findAll();

    Iterable<Mechanic> findAll(Page page, String order);

}
