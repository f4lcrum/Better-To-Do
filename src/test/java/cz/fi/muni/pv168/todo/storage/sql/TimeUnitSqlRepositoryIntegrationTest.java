package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.TimeUnitRepository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.wiring.TestDependencyProvider;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    private DatabaseManager databaseManager;
    private TimeUnitRepository timeUnitRepository;
    private static final int INIT_TIMEUNITS_COUNT = 4;

    @BeforeEach
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
    void timeUnitInitSucceeds() {
        // Prepare
        Set<String> expectedTimeUnitNames = new HashSet<>(Set.of("Minute", "Hour", "Day", "Week"));
        final Collection<TimeUnit> retrievedTimeUnits;

        // Act
        retrievedTimeUnits = timeUnitRepository.findAll();

        // Remove all present time units
        retrievedTimeUnits.forEach(timeUnit -> expectedTimeUnitNames.remove(timeUnit.getName()));

        // Assert
        // Expecting 4 initial time units - Minute, Hour, Day, Week
        assertEquals(INIT_TIMEUNITS_COUNT, retrievedTimeUnits.size());
        assertEquals(0, expectedTimeUnitNames.size());
    }

    @Test
    void createOneTimeUnitSucceeds() {
        // Prepare
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), "TestTU", 10, 120);
        final Optional<TimeUnit> retrievedTimeUnit;

        // Act
        timeUnitRepository.create(newTimeUnit);

        // Assert
        retrievedTimeUnit = assertDoesNotThrow(() -> timeUnitRepository.findByGuid(newTimeUnit.getGuid()));
        assertTrue(retrievedTimeUnit.isPresent());
        assertEquals(newTimeUnit, retrievedTimeUnit.get());
    }

    @Test
    void updateOfInsertedTimeUnitSucceeds() {
        // Prepare
        final TimeUnit timeUnit = new TimeUnit(UUID.randomUUID(), "TestTU", 10, 120);
        final TimeUnit updatedTimeUnit = new TimeUnit(timeUnit.getGuid(), "UpdatedTestTu", 10, 210);
        final Optional<TimeUnit> updateResult;

        // Act
        timeUnitRepository.create(timeUnit);
        assertDoesNotThrow(() -> timeUnitRepository.update(updatedTimeUnit));
        updateResult = assertDoesNotThrow(() -> timeUnitRepository.findByGuid(timeUnit.getGuid()));

        // Assert
        assertTrue(updateResult.isPresent());
        assertNotEquals(timeUnit, updateResult.get());
        assertEquals(updatedTimeUnit, updateResult.get());
    }

    @Test
    void deleteByGuidOnExistingTimeUnitSucceeds() {
        // Prepare
        final TimeUnit timeUnit = new TimeUnit(UUID.randomUUID(), "TestTU", 10, 120);

        // Act
        timeUnitRepository.create(timeUnit);

        // Assert
        assertDoesNotThrow(() -> timeUnitRepository.existsByGuid(timeUnit.getGuid()));
        assertDoesNotThrow(() -> timeUnitRepository.deleteByGuid(timeUnit.getGuid()));
        // Should throw exception since we cannot delete item twice (assuming it was deleted the first time)
        assertThrows(DataStorageException.class, () -> timeUnitRepository.deleteByGuid(timeUnit.getGuid()));
    }

    @Test
    void deleteAllSucceeds() {
        // Prepare
        final TimeUnit timeUnit1 = new TimeUnit(UUID.randomUUID(), "TestTU1", 10, 120);
        final TimeUnit timeUnit2 = new TimeUnit(UUID.randomUUID(), "TestTU2", 101, 1201);
        final TimeUnit timeUnit3 = new TimeUnit(UUID.randomUUID(), "TestTU3", 1, 11);
        final Collection<TimeUnit> timeUnits;

        // Act
        timeUnitRepository.create(timeUnit1);
        timeUnitRepository.create(timeUnit2);
        timeUnitRepository.create(timeUnit3);
        timeUnits = timeUnitRepository.findAll();
        assertDoesNotThrow(() -> timeUnitRepository.deleteAll());

        // Assert
        assertEquals(INIT_TIMEUNITS_COUNT + 3, timeUnits.size()); // Desired time units were added
        assertEquals(0, timeUnitRepository.findAll().size()); // All time units were removed
    }

    @Test
    void existsByGuidOnExistingTimeUnitSucceeds() {
        // Prepare
        final TimeUnit timeUnit = new TimeUnit(UUID.randomUUID(), "Lecture", 0, 100);
        final boolean existsTimeUnit;

        // Act
        timeUnitRepository.create(timeUnit);
        existsTimeUnit = timeUnitRepository.existsByGuid(timeUnit.getGuid());

        // Asserts
        assertDoesNotThrow(() -> timeUnitRepository.findByGuid(timeUnit.getGuid())); // Time unit actually exists
        assertTrue(existsTimeUnit); // existsByGuid returns correct value

    }

    @Test
    void existsByGuidOnNonExistingTimeUnitFails() {
        // Prepare
        final UUID randomUUID = UUID.fromString("d94ee5ef-0325-4ebe-a895-fa380c968f49");
        final boolean existsResult;

        // Act
        existsResult = assertDoesNotThrow(() -> timeUnitRepository.existsByGuid(randomUUID));

        // Assert
        assertFalse(existsResult);
    }

    @Test
    void findByGuidOnExistingTimeUnitSucceeds() {
        // Prepare
        final TimeUnit timeUnit = new TimeUnit(UUID.randomUUID(), "Lecture", 0, 100);
        final Optional<TimeUnit> retrievedTimeUnit;

        // Act
        timeUnitRepository.create(timeUnit);
        retrievedTimeUnit = assertDoesNotThrow(() -> timeUnitRepository.findByGuid(timeUnit.getGuid()));

        // Assert
        assertTrue(retrievedTimeUnit.isPresent());
        assertEquals(timeUnit, retrievedTimeUnit.get());
    }
}
