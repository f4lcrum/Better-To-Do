package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
     * Find entity based on GUID.
     */
    Optional<T> findByGuid(UUID guid);

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
    void deleteByGuid(UUID guid);

    /**
     * Delete all entities.
     */
    void deleteAll();
}
