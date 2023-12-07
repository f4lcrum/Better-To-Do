package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.TimeUnitRepository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.entity.TimeUnitEntity;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.EntityMapper;
import cz.muni.fi.pv168.employees.storage.sql.dao.DataAccessObject;

import java.util.List;
import java.util.Optional;

public class TimeUnitSqlRepository implements TimeUnitRepository {

    private final DataAccessObject<TimeUnitEntity> timeUnitDao;
    private final EntityMapper<TimeUnitEntity, TimeUnit> timeUnitEntityMapper;

    public TimeUnitSqlRepository(
            DataAccessObject<TimeUnitEntity> timeUnitDao,
            EntityMapper<TimeUnitEntity, TimeUnit> timeUnitEntityMapper) {
        this.timeUnitDao = timeUnitDao;
        this.timeUnitEntityMapper = timeUnitEntityMapper;
    }

    @Override
    public List<TimeUnit> findAll() {
        return timeUnitDao
                .findAll()
                .stream()
                .map(timeUnitEntityMapper::mapToBusiness)
                .toList();
    }

    @Override
    public void create(TimeUnit newEntity) {
        timeUnitDao.create(
                timeUnitEntityMapper
                        .mapNewEntityToDatabase(newEntity)
        );
    }

    @Override
    public void update(TimeUnit entity) {
        var existingTimeUnit = timeUnitDao.findById(entity.getGuid().toString())
                .orElseThrow(() -> new DataStorageException("Time unit not found, id: " + entity.getGuid()));
        var updatedTimeUnit = timeUnitEntityMapper
                .mapExistingEntityToDatabase(entity, existingTimeUnit.id());

        timeUnitDao.update(updatedTimeUnit);
    }

    @Override
    public void deleteByGuid(String guid) {
        timeUnitDao.deleteById(guid);
    }

    @Override
    public void deleteAll() {
        timeUnitDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return timeUnitDao.existsByGuid(guid);
    }

    @Override
    public Optional<TimeUnit> findByGuid(String guid) {
        return timeUnitDao
                .findById(guid)
                .map(timeUnitEntityMapper::mapToBusiness);
    }
}
