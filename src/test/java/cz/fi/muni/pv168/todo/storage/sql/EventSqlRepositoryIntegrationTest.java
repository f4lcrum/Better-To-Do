package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.CategoryRepository;
import cz.fi.muni.pv168.todo.business.repository.EventRepository;
import cz.fi.muni.pv168.todo.business.repository.TimeUnitRepository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.wiring.TestingDependencyProvider;
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EventSqlRepositoryIntegrationTest {
    private DatabaseManager databaseManager;
    private EventRepository eventRepository;
    private CategoryRepository categoryRepository;
    private TimeUnitRepository timeUnitRepository;

    @BeforeEach
    void setUp() {
        databaseManager = DatabaseManager.createTestInstance();
        var dependencyProvider = new TestingDependencyProvider(databaseManager);
        eventRepository = dependencyProvider.getEventRepository();
        categoryRepository = dependencyProvider.getCategoryRepository();
        timeUnitRepository = dependencyProvider.getTimeUnitRepository();
    }

    @AfterEach
    void tearDown() {
        databaseManager.destroySchema();
    }

    @Test
    void eventInitSucceeds() {
        Collection<Event> events = eventRepository.findAll();

        Collection<String> eventNames = events.stream()
                .map(Event::getName)
                .toList();

        assertThat(eventNames).containsExactlyInAnyOrder("Meeting", "Gym", "Family Dinner");
    }

    @Test
    void createOneEventSucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestEvent", Color.PINK);
        categoryRepository.create(newCategory);
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Optional<Event> retrievedCategory;
        final Event newEvent = new Event(UUID.randomUUID(), "Test Event", newCategory, LocalDate.now(), LocalTime.now(), newTimeUnit, 8, "Event for work tasks");

        timeUnitRepository.create(newTimeUnit);
        eventRepository.create(newEvent);

        retrievedCategory = assertDoesNotThrow(() -> eventRepository.findByGuid(newEvent.getGuid()));
        assertTrue(retrievedCategory.isPresent());
        assertEquals(newEvent, retrievedCategory.get());
    }

    @Test
    void updateOfInsertedEventSucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestEvent", Color.PINK);
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Event newEvent = new Event(UUID.randomUUID(), "Test Event", newCategory, LocalDate.now(), LocalTime.now(), newTimeUnit, 8, "Event for work tasks");
        final Event updateEvent = new Event(newEvent.getGuid(), "Updated Event", newCategory, LocalDate.now(), LocalTime.now(), newTimeUnit, 8, "Upfated event");
        final Optional<Event> updateResult;

        categoryRepository.create(newCategory);
        timeUnitRepository.create(newTimeUnit);
        eventRepository.create(newEvent);

        assertDoesNotThrow(() -> eventRepository.update(updateEvent));

        updateResult = assertDoesNotThrow(() -> eventRepository.findByGuid(newEvent.getGuid()));
        assertTrue(updateResult.isPresent());
        assertEquals(updateEvent, updateResult.get());
    }

    @Test
    void deleteByGuidOnExistingEventSucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestEvent", Color.PINK);
        categoryRepository.create(newCategory);
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Event newEvent = new Event(UUID.randomUUID(), "Test Event", newCategory, LocalDate.now(), LocalTime.now(), newTimeUnit, 8, "Event for work tasks");

        timeUnitRepository.create(newTimeUnit);
        eventRepository.create(newEvent);

        assertDoesNotThrow(() -> eventRepository.existsByGuid(newEvent.getGuid()));
        assertDoesNotThrow(() -> eventRepository.deleteByGuid(newEvent.getGuid()));
        assertThrows(DataStorageException.class, () -> eventRepository.deleteByGuid(newEvent.getGuid()));
    }

    @Test
    void deleteAllSucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestEvent", Color.PINK);
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Event newEvent1 = new Event(UUID.randomUUID(), "Test Event1", newCategory, LocalDate.now(),LocalTime.now(), newTimeUnit, 8, "Event1 for work tasks");
        final Event newEvent2 = new Event(UUID.randomUUID(), "Test Event2", newCategory, LocalDate.now(),LocalTime.now(), newTimeUnit, 8, "Event2 for work tasks");
        final Event newEvent3 = new Event(UUID.randomUUID(), "Test Event3", newCategory, LocalDate.now(), LocalTime.now(), newTimeUnit, 8, "Event3 for work tasks");
        final Collection<Event> events;

        categoryRepository.create(newCategory);
        timeUnitRepository.create(newTimeUnit);
        eventRepository.create(newEvent1);
        eventRepository.create(newEvent2);
        eventRepository.create(newEvent3);

        events = eventRepository.findAll();
        assertDoesNotThrow(() -> eventRepository.deleteAll());
        assertEquals(3 + 3, events.size());
        assertEquals(0, eventRepository.findAll().size());
    }

    @Test
    void existsByGuidOnExistingEventSucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestEvent", Color.PINK);
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Event event = new Event(UUID.randomUUID(), "Test Event1", newCategory, LocalDate.now(), LocalTime.now(), newTimeUnit, 8, "Event1 for work tasks");
        final boolean existsEvent;

        categoryRepository.create(newCategory);
        timeUnitRepository.create(newTimeUnit);
        eventRepository.create(event);
        existsEvent = eventRepository.existsByGuid(event.getGuid());

        assertDoesNotThrow(() -> eventRepository.findByGuid(event.getGuid())); // Time unit actually exists
        assertTrue(existsEvent); // existsByGuid returns correct value
    }

    @Test
    void existsByGuidOnNonExistingEventFails() {
        final UUID randomUUID = UUID.fromString("d94ee5ef-0325-4ebe-a895-fa380c968f49");
        final boolean existsResult;

        existsResult = assertDoesNotThrow(() -> eventRepository.existsByGuid(randomUUID));
        assertFalse(existsResult);
    }

    @Test
    void findByGuidOnExistingEventSucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestEvent", Color.PINK);
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Event event = new Event(UUID.randomUUID(), "Test Event1", newCategory, LocalDate.now(), LocalTime.now(), newTimeUnit, 8, "Event1 for work tasks");
        final Optional<Event> retrievedEvent;

        categoryRepository.create(newCategory);
        timeUnitRepository.create(newTimeUnit);
        eventRepository.create(event);

        retrievedEvent = assertDoesNotThrow(() -> eventRepository.findByGuid(event.getGuid()));
        assertTrue(retrievedEvent.isPresent());
        assertEquals(event, retrievedEvent.get());
    }
}
