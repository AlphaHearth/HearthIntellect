package com.hearthintellect.dao.mongo;

import com.hearthintellect.dao.Repository;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.model.MongoEntity;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 * Common parent class for all Morphia Repository Implementation
 */
public abstract class MorphiaRepository<T extends MongoEntity> implements Repository<T> {

    private Datastore datastore;

    protected void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }

    protected Datastore getDatastore() {
        return datastore;
    }

    protected abstract Class<T> getEntityClass();

    protected Query<T> createQuery() {
        return datastore.createQuery(getEntityClass());
    }

    @Override
    public T findById(Object id) {
        return createQuery().field("_id").equal(id).get();
    }

    @Override
    public void insert(T t) {
        datastore.save(t);
    }

    @Override
    public void update(T t) {
        datastore.updateFirst(
            createQuery().field("_id").equal(t.getId()),
            t,
            false
        );
    }

    @Override
    public void delete(T t) {
        datastore.delete(t);
    }

}
