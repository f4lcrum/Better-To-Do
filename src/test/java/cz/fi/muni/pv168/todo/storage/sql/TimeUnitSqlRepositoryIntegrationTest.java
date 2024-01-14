package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.TimeUnitRepository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.wiring.TestingDependencyProvider;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

final class TimeUnitSqlRepositoryIntegrationTest {

    private static final int INIT_TIMEUNITS_COUNT = 4;

    private DatabaseManager databaseManager;
    private TimeUnitRepository timeUnitRepository;

    @BeforeEach
    void setUp() {
        databaseManager = DatabaseManager.createTestInstance();
        var dependencyProvider = new TestingDependencyProvider(databaseManager);
        timeUnitRepository = dependencyProvider.getTimeUnitRepository();
    }

    @AfterEach
    void tearDown() {
        databaseManager.destroySchema();
    }

    @Test
    void timeUnitInitSucceeds() {
        Set<String> expectedTimeUnitNames = new HashSet<>(Set.of("Minute", "Hour", "Day", "Week"));
        final Collection<TimeUnit> retrievedTimeUnits;

        retrievedTimeUnits = timeUnitRepository.findAll();

        retrievedTimeUnits.forEach(timeUnit -> expectedTimeUnitNames.remove(timeUnit.getName()));

        // Expecting 4 initial time units - Minute, Hour, Day, Week
        assertEquals(INIT_TIMEUNITS_COUNT, retrievedTimeUnits.size());
        assertEquals(0, expectedTimeUnitNames.size());
    }

    @Test
    void createOneTimeUnitSucceeds() {
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Optional<TimeUnit> retrievedTimeUnit;

        timeUnitRepository.create(newTimeUnit);

        retrievedTimeUnit = assertDoesNotThrow(() -> timeUnitRepository.findByGuid(newTimeUnit.getGuid()));
        assertTrue(retrievedTimeUnit.isPresent());
        assertEquals(newTimeUnit, retrievedTimeUnit.get());
    }

    @Test
    void updateOfInsertedTimeUnitSucceeds() {
        final TimeUnit timeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final TimeUnit updatedTimeUnit = new TimeUnit(timeUnit.getGuid(), false, "UpdatedTestTu", 10, 210);
        final Optional<TimeUnit> updateResult;

        timeUnitRepository.create(timeUnit);
        assertDoesNotThrow(() -> timeUnitRepository.update(updatedTimeUnit));
        updateResult = assertDoesNotThrow(() -> timeUnitRepository.findByGuid(timeUnit.getGuid()));

        assertTrue(updateResult.isPresent());
        assertEquals(updatedTimeUnit, updateResult.get());
    }

    @Test
    void deleteByGuidOnExistingTimeUnitSucceeds() {
        final TimeUnit timeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);

        timeUnitRepository.create(timeUnit);

        assertDoesNotThrow(() -> timeUnitRepository.existsByGuid(timeUnit.getGuid()));
        assertDoesNotThrow(() -> timeUnitRepository.deleteByGuid(timeUnit.getGuid()));
        // Should throw exception since we cannot delete item twice (assuming it was deleted the first time)
        assertThrows(DataStorageException.class, () -> timeUnitRepository.deleteByGuid(timeUnit.getGuid()));
    }

    @Test
    void deleteAllSucceeds() {
        final TimeUnit timeUnit1 = new TimeUnit(UUID.randomUUID(), false, "TestTU1", 10, 120);
        final TimeUnit timeUnit2 = new TimeUnit(UUID.randomUUID(), false, "TestTU2", 101, 1201);
        final TimeUnit timeUnit3 = new TimeUnit(UUID.randomUUID(), false, "TestTU3", 1, 11);
        final Collection<TimeUnit> timeUnits;

        timeUnitRepository.create(timeUnit1);
        timeUnitRepository.create(timeUnit2);
        timeUnitRepository.create(timeUnit3);
        timeUnits = timeUnitRepository.findAll();
        assertDoesNotThrow(() -> timeUnitRepository.deleteAll());

        assertEquals(INIT_TIMEUNITS_COUNT + 3, timeUnits.size()); // Desired time units were added
        assertEquals(0, timeUnitRepository.findAll().size()); // All time units were removed
    }

    @Test
    void existsByGuidOnExistingTimeUnitSucceeds() {
        final TimeUnit timeUnit = new TimeUnit(UUID.randomUUID(), false, "Lecture", 0, 100);
        final boolean existsTimeUnit;

        timeUnitRepository.create(timeUnit);
        existsTimeUnit = timeUnitRepository.existsByGuid(timeUnit.getGuid());

        assertDoesNotThrow(() -> timeUnitRepository.findByGuid(timeUnit.getGuid())); // Time unit actually exists
        assertTrue(existsTimeUnit); // existsByGuid returns correct value

    }

    @Test
    void existsByGuidOnNonExistingTimeUnitFails() {
        final UUID randomUUID = UUID.fromString("d94ee5ef-0325-4ebe-a895-fa380c968f49");
        final boolean existsResult;

        existsResult = assertDoesNotThrow(() -> timeUnitRepository.existsByGuid(randomUUID));

        assertFalse(existsResult);
    }

    @Test
    void findByGuidOnExistingTimeUnitSucceeds() {
        final TimeUnit timeUnit = new TimeUnit(UUID.randomUUID(), false, "Lecture", 0, 100);
        final Optional<TimeUnit> retrievedTimeUnit;

        timeUnitRepository.create(timeUnit);
        retrievedTimeUnit = assertDoesNotThrow(() -> timeUnitRepository.findByGuid(timeUnit.getGuid()));

        assertTrue(retrievedTimeUnit.isPresent());
        assertEquals(timeUnit, retrievedTimeUnit.get());
    }
}
