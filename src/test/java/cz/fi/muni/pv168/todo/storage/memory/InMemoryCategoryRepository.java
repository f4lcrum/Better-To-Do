package cz.fi.muni.pv168.todo.storage.memory;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.repository.CategoryRepository;
import java.util.Collection;

public class InMemoryCategoryRepository extends InMemoryRepository<Category> implements CategoryRepository {
    public InMemoryCategoryRepository(Collection<Category> initEntities) {
        super(initEntities);
    }
}
