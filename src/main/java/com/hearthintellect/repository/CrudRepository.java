package com.hearthintellect.repository;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Customized version of {@link org.springframework.data.repository.CrudRepository CrudRepository} with the new
 * {@link #insert(Object)}} and {@link #insert(Iterable)} method.
 *
 * @author Robert Peng
 */
@NoRepositoryBean
public interface CrudRepository<T, ID extends Serializable> extends
    org.springframework.data.repository.CrudRepository<T, ID> {

    /**
     * Inserts the given entity. Use the returned instance for further operations as the insert operation
     * might have changed the entity instance completely.
     * <p></p>
     * Throws {@link DuplicateKeyException} if entity with the same ID already exists.
     *
     * @param entity the given entity to be inserted.
     * @return the inserted entity.
     * @throws DuplicateKeyException if entity with the same ID already exists.
     */
    <S extends T> S insert(S entity) throws DuplicateKeyException;

    /**
     * Inserts all given entities.
     *
     * @param entities the given entities.
     * @return the inserted entities.
     * @throws DuplicateKeyException if entity with the same ID as some given entity already exists.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    <S extends T> Iterable<S> insert(Iterable<S> entities) throws DuplicateKeyException;
}
