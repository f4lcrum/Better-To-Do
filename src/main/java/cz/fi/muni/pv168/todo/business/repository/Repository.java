package cz.fi.muni.pv168.todo.business.repository;

import cz.fi.muni.pv168.todo.business.entity.Entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
     * Delete entity with given {@code id}.
     */
    void deleteById(UUID id);

    /**
     * Find entity with given {@code id}.
     *
     * @return optional with found entity, or empty optional if no entity with given {@code guid} is found
     */
    Optional<T> findById(UUID id);

    /**
     * Delete all entities.
     */
    void deleteAll();

    /**
     * Check if there is an existing Entity with given {@code id}
     *
     * @return true, if an Entity with given {@code} is found, false otherwise
     */
    boolean existsById(UUID id);

}
