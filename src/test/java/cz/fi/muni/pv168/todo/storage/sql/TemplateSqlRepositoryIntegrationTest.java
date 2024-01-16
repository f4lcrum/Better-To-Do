package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.CategoryRepository;
import cz.fi.muni.pv168.todo.business.repository.TemplateRepository;
import cz.fi.muni.pv168.todo.business.repository.TimeUnitRepository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.wiring.TestingDependencyProvider;
import java.awt.Color;
import java.time.LocalTime;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public final class TemplateSqlRepositoryIntegrationTest {
    private DatabaseManager databaseManager;
    private TemplateRepository templateRepository;
    private CategoryRepository categoryRepository;
    private TimeUnitRepository timeUnitRepository;

    @BeforeEach
    void setUp() {
        databaseManager = DatabaseManager.createTestInstance();
        var dependencyProvider = new TestingDependencyProvider(databaseManager);
        templateRepository = dependencyProvider.getTemplateRepository();
        categoryRepository = dependencyProvider.getCategoryRepository();
        timeUnitRepository = dependencyProvider.getTimeUnitRepository();
    }

    @AfterEach
    void tearDown() {
        databaseManager.destroySchema();
    }

    @Test
    void templateInitSucceeds() {
        Collection<Template> templates = templateRepository.findAll();

        Collection<String> templateNames = templates.stream()
                .map(Template::getName)
                .toList();

        assertThat(templateNames).containsExactlyInAnyOrder("Work Template", "Personal Template", "Family Template");
    }

    @Test
    void createOneTemplateSucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestTemplate", Color.PINK);
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Optional<Template> retrievedCategory;
        final Template newTemplate = new Template(UUID.randomUUID(), "Test Template", "Test Task", newCategory, LocalTime.now(), newTimeUnit, 8, "Template for work tasks");

        categoryRepository.create(newCategory);
        timeUnitRepository.create(newTimeUnit);
        templateRepository.create(newTemplate);

        retrievedCategory = assertDoesNotThrow(() -> templateRepository.findByGuid(newTemplate.getGuid()));
        assertTrue(retrievedCategory.isPresent());
        assertEquals(newTemplate, retrievedCategory.get());
    }

    @Test
    void updateOfInsertedTemplateSucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestTemplate", Color.PINK);final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Template newTemplate = new Template(UUID.randomUUID(), "Test Template", "Test Task", newCategory, LocalTime.now(), newTimeUnit, 8, "Template for work tasks");
        final Template updateTemplate = new Template(newTemplate.getGuid(), "Updated Template", "Update Task", newCategory, LocalTime.now(), newTimeUnit, 8, "Upfated template");
        final Optional<Template> updateResult;

        categoryRepository.create(newCategory);
        timeUnitRepository.create(newTimeUnit);
        templateRepository.create(newTemplate);

        assertDoesNotThrow(() -> templateRepository.update(updateTemplate));
        
        updateResult = assertDoesNotThrow(() -> templateRepository.findByGuid(newTemplate.getGuid()));
        assertTrue(updateResult.isPresent());
        assertEquals(updateTemplate, updateResult.get());
    }

    @Test
    void deleteByGuidOnExistingTemplateSucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestTemplate", Color.PINK);
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Template newTemplate = new Template(UUID.randomUUID(), "Test Template", "Test Task", newCategory, LocalTime.now(), newTimeUnit, 8, "Template for work tasks");

        categoryRepository.create(newCategory);
        timeUnitRepository.create(newTimeUnit);
        templateRepository.create(newTemplate);

        assertDoesNotThrow(() -> templateRepository.existsByGuid(newTemplate.getGuid()));
        assertDoesNotThrow(() -> templateRepository.deleteByGuid(newTemplate.getGuid()));
        assertThrows(DataStorageException.class, () -> templateRepository.deleteByGuid(newTemplate.getGuid()));
    }

    @Test
    void deleteAllSucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestTemplate", Color.PINK);
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Template newTemplate1 = new Template(UUID.randomUUID(), "Test Template1", "Test Task1", newCategory, LocalTime.now(), newTimeUnit, 8, "Template1 for work tasks");
        final Template newTemplate2 = new Template(UUID.randomUUID(), "Test Template2", "Test Task2", newCategory, LocalTime.now(), newTimeUnit, 8, "Template2 for work tasks");
        final Template newTemplate3 = new Template(UUID.randomUUID(), "Test Template3", "Test Task3", newCategory, LocalTime.now(), newTimeUnit, 8, "Template3 for work tasks");
        final Collection<Template> templates;

        categoryRepository.create(newCategory);
        timeUnitRepository.create(newTimeUnit);
        templateRepository.create(newTemplate1);
        templateRepository.create(newTemplate2);
        templateRepository.create(newTemplate3);

        templates = templateRepository.findAll();
        assertDoesNotThrow(() -> templateRepository.deleteAll());
        assertEquals(3 + 3, templates.size()); // Desired time units were added
        assertEquals(0, templateRepository.findAll().size()); // All time units were removed
    }

    @Test
    void existsByGuidOnExistingTemplateSucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestTemplate", Color.PINK);
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Template template = new Template(UUID.randomUUID(), "Test Template1", "Test Task1", newCategory, LocalTime.now(), newTimeUnit, 8, "Template1 for work tasks");
        final boolean existsTemplate;

        categoryRepository.create(newCategory);
        timeUnitRepository.create(newTimeUnit);
        templateRepository.create(template);

        existsTemplate = templateRepository.existsByGuid(template.getGuid());
        assertDoesNotThrow(() -> templateRepository.findByGuid(template.getGuid())); // Time unit actually exists
        assertTrue(existsTemplate); // existsByGuid returns correct value
    }

    @Test
    void existsByGuidOnNonExistingTemplateFails() {
        final UUID randomUUID = UUID.fromString("d94ee5ef-0325-4ebe-a895-fa380c968f49");
        final boolean existsResult;

        existsResult = assertDoesNotThrow(() -> templateRepository.existsByGuid(randomUUID));
        assertFalse(existsResult);
    }

    @Test
    void findByGuidOnExistingTemplateSucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestTemplate", Color.PINK);
        final TimeUnit newTimeUnit = new TimeUnit(UUID.randomUUID(), false, "TestTU", 10, 120);
        final Template template = new Template(UUID.randomUUID(), "Test Template1", "Test Task1", newCategory, LocalTime.now(), newTimeUnit, 8, "Template1 for work tasks");
        final Optional<Template> retrievedTemplate;

        categoryRepository.create(newCategory);
        timeUnitRepository.create(newTimeUnit);
        templateRepository.create(template);

        retrievedTemplate = assertDoesNotThrow(() -> templateRepository.findByGuid(template.getGuid()));
        assertTrue(retrievedTemplate.isPresent());
        assertEquals(template, retrievedTemplate.get());
    }
}
