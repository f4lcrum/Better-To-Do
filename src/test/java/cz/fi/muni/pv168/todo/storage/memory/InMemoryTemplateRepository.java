package cz.fi.muni.pv168.todo.storage.memory;

import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.repository.TemplateRepository;
import java.util.Collection;

public class InMemoryTemplateRepository extends InMemoryRepository<Template> implements TemplateRepository {
    public InMemoryTemplateRepository(Collection<Template> initEntities) {
        super(initEntities);
    }
}
