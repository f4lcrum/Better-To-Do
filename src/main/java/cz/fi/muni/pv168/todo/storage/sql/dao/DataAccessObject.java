package cz.fi.muni.pv168.todo.storage.sql.dao;


import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Generic interface for CRUD operations on entities.
 *
 * @param <E> type of the entity this DAO operates on
 * @author Vojtěch Sassmann
 */
public interface DataAccessObject<E> {

    /**
     * Creates a new entity using the underlying data source.
     *
     * @param entity entity to be persisted
     * @return Entity instance with set id
     * @throws IllegalArgumentException when the entity has already been persisted
     */
    E create(E entity);

    /**
     * Reads all entities from the underlying data source.
     *
     * @return collection of all entities known to the underlying data source
     */
    Collection<E> findAll();

    /**
     * Finds entity by ID.
     *
     * @param id entity id
     * @return either empty if not found or the entity instance
     */
    Optional<E> findById(UUID id);

    /**
     * Updates an entity using the underlying data source.
     *
     * @param entity entity to be deleted
     * @throws IllegalArgumentException when the entity has not been persisted yet
     */
    E update(E entity);

    /**
     * Deletes an entity using the underlying data source.
     *
     * @param id entity id to be deleted
     * @throws IllegalArgumentException when the entity has not been persisted yet
     * @throws DataStorageException     when anything goes wrong with the underlying data source
     */
    void deleteById(UUID id);

    /**
     * Deletes all entities from the underlying data source.
     *
     */
    void deleteAll();

    /**
     * Checks if entity with given guid exists in the underlying data source.
     *
     * @param id entity id
     * @return true if entity exists, false otherwise
     */
    boolean existsById(UUID id);

}
