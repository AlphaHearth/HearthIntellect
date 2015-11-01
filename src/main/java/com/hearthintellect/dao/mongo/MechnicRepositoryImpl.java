package com.hearthintellect.dao.mongo;

import com.hearthintellect.dao.MechanicRepository;
import com.hearthintellect.model.Mechanic;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MechnicRepositoryImpl implements MechanicRepository {
    private static final Logger LOG = LoggerFactory.getLogger(MechnicRepositoryImpl.class);

    private Datastore datastore;

    public MechnicRepositoryImpl(Datastore datastore) {
        this.datastore = datastore;
    }

    private Query<Mechanic> createQuery() {
        return datastore.createQuery(Mechanic.class);
    }

    @Override
    public Mechanic findById(long mechanicId) {
        return createQuery().field("_id").equal(mechanicId).get();
    }

    @Override
    public Iterable<Mechanic> findAllByName(String name) {
        return createQuery().field("name").contains(name);
    }

    @Override
    public void save(Mechanic mechanic) {
        datastore.save(mechanic);
    }

    @Override
    public void update(Mechanic mechanic) {
        datastore.updateFirst(
            createQuery().field("_id").equal(mechanic.getMechanicId()),
            mechanic,
            false
        );
    }

    @Override
    public void remove(Mechanic mechanic) {
        datastore.delete(mechanic);
    }
}
