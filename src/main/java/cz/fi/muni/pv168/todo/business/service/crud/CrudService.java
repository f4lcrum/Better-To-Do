package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;

import java.util.List;

/**
 * Service for creation, read, update, and delete operations.
 *
 * @param <T> entity type.
 * @author Vojtěch Sassmann
 */
public interface CrudService<T extends Entity> {

    /**
     * Find all entities.
     */
    List<T> findAll();

    /**
     * Validate and store the given {@code newEntity}.
     *
     * @throws EntityAlreadyExistsException if there is already an existing entity with the same guid
     */
    ValidationResult create(T newEntity);

    /**
     * Updates the given {@code entity}.
     */
    ValidationResult update(T entity);

    /**
     * Delete entity with given guid.
     */
    void deleteByGuid(String guid);

    /**
     * Delete all entities.
     */
    void deleteAll();
}
