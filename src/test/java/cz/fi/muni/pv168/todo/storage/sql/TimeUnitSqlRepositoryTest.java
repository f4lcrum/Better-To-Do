package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.TimeUnitRepository;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.wiring.TestDependencyProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

final class TimeUnitSqlRepositoryTest {

    private DatabaseManager databaseManager;
    private TimeUnitRepository timeUnitRepository;

    @BeforeEachranch

    void setUp() {
        databaseManager = DatabaseManager.createTestInstance();
        var dependencyProvider = new TestDependencyProvider(databaseManager);
        timeUnitRepository = dependencyProvider.getTimeUnitRepository();
    }

    @AfterEach
    void tearDown() {
        databaseManager.destroySchema();
    }

    @Test
    void findAllTimeUnitsSucceeds() {
        List<TimeUnit> storedTimeUnits = timeUnitRepository.findAll();

    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteByGuid() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void existsByGuid() {
    }

    @Test
    void findByGuid() {
    }
}