package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataAccessObject;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.entity.TemplateEntity;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of {@link Repository} for {@link Template} entity using SQL database.
 *
 * @author Vojtech Sassmann
 */
public class TemplateSqlRepository implements Repository<Template> {

    private final DataAccessObject<TemplateEntity> templateDao;
    private final EntityMapper<TemplateEntity, Template> templateMapper;

    public TemplateSqlRepository(
            DataAccessObject<TemplateEntity> templateDao,
            EntityMapper<TemplateEntity, Template> templateMapper) {
        this.templateDao = templateDao;
        this.templateMapper = templateMapper;
    }

    @Override
    public List<Template> findAll() {
        return templateDao
                .findAll()
                .stream()
                .map(templateMapper::mapToBusiness)
                .toList();
    }

    @Override
    public void create(Template newEntity) {
        templateDao.create(templateMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Template entity) {
        var existingTemplate = templateDao.findById(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Department not found, guid: " + entity.getGuid()));
        var updatedDepartment = templateMapper.mapExistingEntityToDatabase(entity, existingTemplate.id());

        templateDao.update(updatedDepartment);
    }

    @Override
    public void deleteByGuid(UUID id) {
        templateDao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        templateDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(UUID id) {
        return templateDao.existsByGuid(id);
    }

    @Override
    public Optional<Template> findByGuid(UUID id) {
        return templateDao
                .findById(id)
                .map(templateMapper::mapToBusiness);
    }
}
