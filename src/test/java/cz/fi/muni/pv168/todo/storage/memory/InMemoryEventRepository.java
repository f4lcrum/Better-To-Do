package cz.fi.muni.pv168.todo.storage.memory;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.repository.EventRepository;
import java.util.Collection;

public class InMemoryEventRepository extends InMemoryRepository<Event> implements EventRepository {
    public InMemoryEventRepository(Collection<Event> initEntities) {
        super(initEntities);
    }
}
