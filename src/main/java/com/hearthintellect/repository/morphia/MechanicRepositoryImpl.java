package com.hearthintellect.repository.morphia;

import com.hearthintellect.model.Mechanic;
import com.hearthintellect.repository.MechanicRepository;
import com.hearthintellect.utils.Page;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MechanicRepositoryImpl extends MorphiaRepository<String, Mechanic> implements MechanicRepository {
    private static final Logger LOG = LoggerFactory.getLogger(MechanicRepositoryImpl.class);

    @Override
    public Iterable<Mechanic> findAll() {
        return createQuery();
    }

    @Override
    public Iterable<Mechanic> findAll(Page page, String order) {
        Query<Mechanic> query = createQuery();
        processOrderAndPage(query, order, page);

        return query;
    }

    @Override
    protected Class<Mechanic> getEntityClass() {
        return Mechanic.class;
    }

}
