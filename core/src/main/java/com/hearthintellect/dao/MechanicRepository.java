package com.hearthintellect.dao;

import com.hearthintellect.model.Mechanic;
import com.hearthintellect.utils.Page;

public interface MechanicRepository extends Repository<String, Mechanic> {

    Iterable<Mechanic> findAll();

    Iterable<Mechanic> findAll(Page page, String order);

}
