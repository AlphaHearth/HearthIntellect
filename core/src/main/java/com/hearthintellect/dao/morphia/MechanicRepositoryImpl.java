package com.hearthintellect.dao.morphia;

import com.hearthintellect.dao.MechanicRepository;
import com.hearthintellect.model.Mechanic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MechanicRepositoryImpl extends MorphiaRepository<Mechanic> implements MechanicRepository {
    private static final Logger LOG = LoggerFactory.getLogger(MechanicRepositoryImpl.class);

    @Override
    public Iterable<Mechanic> findAllByName(String name) {
        return createQuery().field("name").contains(name);
    }

    @Override
    protected Class<Mechanic> getEntityClass() {
        return Mechanic.class;
    }

}
