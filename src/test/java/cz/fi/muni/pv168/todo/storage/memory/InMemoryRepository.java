package cz.fi.muni.pv168.todo.storage.memory;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryRepository<T extends Entity> implements Repository<T> {

    protected Map<UUID, T> data = new HashMap<>();

    public InMemoryRepository(Collection<T> initEntities) {
        initEntities.forEach(this::create);
    }

    @Override
    public Optional<T> findByGuid(UUID guid) {
        if (guid == null) {
            throw new IllegalArgumentException("Guid cannot be null.");
        }
        return Optional.ofNullable(data.get(guid));
    }

    @Override
    public List<T> findAll() {
        return data.values().stream()
                .toList();
    }

    @Override
    public void create(T newEntity) {
        if (newEntity.getGuid() == null) {
            throw new IllegalArgumentException("Cannot persist entity without guid.");
        }
        data.put(newEntity.getGuid(), newEntity);

        System.out.println("[InMemoryStorage] Created entity: " + newEntity);
    }

    @Override
    public void update(T entity) {
        var entityOptional = findByGuid(entity.getGuid());
        if (entityOptional.isEmpty()) {
            throw new IllegalArgumentException("No existing entity found with given guid: " + entity.getGuid());
        }
        data.put(entity.getGuid(), entity);

        System.out.println("[InMemoryStorage] Updated entity: " + entity);
    }

    @Override
    public void deleteByGuid(UUID guid) {
        if (guid == null) {
            throw new IllegalArgumentException("Guid cannot be null.");
        }
        data.remove(guid);
        System.out.println("[InMemoryStorage] Deleted entity with guid: " + guid);
    }

    @Override
    public void deleteAll() {
        data = new HashMap<>();
    }

    @Override
    public boolean existsByGuid(UUID guid) {
        return data.containsKey(guid);
    }
}

