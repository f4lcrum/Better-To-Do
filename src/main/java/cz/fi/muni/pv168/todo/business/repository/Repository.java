package cz.fi.muni.pv168.todo.business.repository;

import cz.fi.muni.pv168.todo.business.entity.Entity;

import java.util.List;
import java.util.Optional;

/**
 * Represents a repository for any entity.
 *
 * @param <T> the type of the entity.
 * @author Vojtěch Sassmann
 */
public interface Repository<T extends Entity> {

    /**
     * Find all entities.
     */
    List<T> findAll();

    /**
     * Persist given {@code newEntity}.
     */
    void create(T newEntity);

    /**
     * Update given {@code entity}.
     */
    void update(T entity);

    /**
     * Delete all entities.
     */
    void deleteAll();
}
