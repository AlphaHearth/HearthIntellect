package com.hearthintellect.dao.morphia;

import com.hearthintellect.dao.Repository;
import com.hearthintellect.model.MongoEntity;
import com.hearthintellect.utils.Page;
import com.hearthintellect.utils.Sort;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 * Common parent class for all Morphia Repository Implementation
 */
public abstract class MorphiaRepository<S, T extends MongoEntity<S>> implements Repository<S, T> {

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

    /**
     * <p>
     *   Process the given {@link Query} with the given {@code order} String and {@link Page}.
     * </p>
     * * <p>
     *     For the syntax of the {@code order} string, see {@link Sort#of(String)}.
     * </p>
     * <p>
     *     If the given {@code order} is {@code null}, no sorting will be applied.
     * </p>
     * <p>
     *     If the given {@code page} is {@code null}, no pagination will be applied.
     * </p>
     *
     * @param query the {@code Query} to be processed
     * @param order the given string representing a sorting order
     * @param page a {@link Page} instance describing the designated page
     * @return the given {@code Query} after being processed
     *
     * @see Page
     * @see Sort#of(String)
     */
    protected Query<T> processOrderAndPage(Query<T> query, String order, Page page) {
        if (order != null)
            query.order(order);

        if (page != null)
            query.offset(page.getOffset()).limit(page.getNumPerPage());

        return query;
    }

    @Override
    public T findById(S id) {
        return createQuery().field("_id").equal(id).get();
    }

    @Override
    public void insert(T t) {
        datastore.save(t);
    }

    @Override
    public void update(T t) {
        datastore.save(t);
    }

    @Override
    public void delete(T t) {
        datastore.delete(t);
    }

}
