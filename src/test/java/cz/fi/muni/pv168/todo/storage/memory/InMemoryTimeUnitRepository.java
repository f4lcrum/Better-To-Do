package cz.fi.muni.pv168.todo.storage.memory;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.TimeUnitRepository;
import java.util.Collection;

public class InMemoryTimeUnitRepository extends InMemoryRepository<TimeUnit> implements TimeUnitRepository {
    public InMemoryTimeUnitRepository(Collection<TimeUnit> initEntities) {
        super(initEntities);
    }
}
