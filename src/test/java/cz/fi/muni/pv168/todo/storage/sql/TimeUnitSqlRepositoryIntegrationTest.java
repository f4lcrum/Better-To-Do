package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.EventRepository;
import cz.fi.muni.pv168.todo.business.repository.TemplateRepository;
import cz.fi.muni.pv168.todo.business.repository.TimeUnitRepository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.wiring.TestingDependencyProvider;
import org.junit.jupiter.api.AfterEach;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public final class TimeUnitSqlRepositoryIntegrationTest {

    private static final int INIT_TIMEUNITS_COUNT = 4;

    private DatabaseManager databaseManager;
    private TimeUnitRepository timeUnitRepository;
    private EventRepository eventRepository;
    private TemplateRepository templateRepository;


    @BeforeEach
    public void setUp() {
        databaseManager = DatabaseManager.createTestInstance();
        var dependencyProvider = new TestingDependencyProvider(databaseManager);
        timeUnitRepository = dependencyProvider.getTimeUnitRepository();
        eventRepository = dependencyProvider.getEventRepository();
        templateRepository = dependencyProvider.getTemplateRepository();

    }

    @AfterEach
    public void tearDown() {
        databaseManager.destroySchema();
    }

    @Test
    public void timeUnitInitSucceeds() {
        Collection<TimeUnit> timeUnits = timeUnitRepository.findAll();

        Collection<String> unitNames = timeUnits.stream()
                .map(TimeUnit::getName).toList();

        assertThat(unitNames).containsExactlyInAnyOrder("Minute", "Hour", "Day", "Week");
    }

    @Test
    public void createOneTimeUnitSucceeds() {
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Optional<TimeUnit> retrievedTimeUnit;

        timeUnitRepository.create(newTimeUnit);

        retrievedTimeUnit = assertDoesNotThrow(() -> timeUnitRepository.findByGuid(newTimeUnit.getGuid()));
        assertTrue(retrievedTimeUnit.isPresent());
        assertEquals(newTimeUnit, retrievedTimeUnit.get());
    }

    @Test
    public void updateOfInsertedTimeUnitSucceeds() {
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

        assertDoesNotThrow(() -> eventRepository.deleteAll());
        assertDoesNotThrow(() -> templateRepository.deleteAll());
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
