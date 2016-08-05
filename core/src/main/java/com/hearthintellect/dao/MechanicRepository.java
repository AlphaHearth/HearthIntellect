package com.hearthintellect.dao;

import com.hearthintellect.model.Mechanic;
import com.hearthintellect.util.Page;

public interface MechanicRepository extends Repository<Mechanic> {

    Iterable<Mechanic> findAll();

    Iterable<Mechanic> findAll(Page page, String order);

}
