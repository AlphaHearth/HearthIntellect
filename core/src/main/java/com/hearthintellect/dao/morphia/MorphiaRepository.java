package com.hearthintellect.dao.morphia;

import com.hearthintellect.dao.Repository;
import com.hearthintellect.model.MongoEntity;
import com.hearthintellect.util.Page;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 * Common parent class for all Morphia Repository Implementation
 */
public abstract class MorphiaRepository<T extends MongoEntity> implements Repository<T> {

    private Datastore datastore;

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    protected abstract Class<T> getEntityClass();

    protected Query<T> createQuery() {
        return datastore.createQuery(getEntityClass());
    }

    protected Query<T> processOrderAndPage(Query<T> query, String order, Page page) {
        if (order != null)
            query.order(order);

        if (page != null)
            query.offset(page.getOffset()).limit(page.getNumPerPage());

        return query;
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
