package cz.fi.muni.pv168.todo.storage.export;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.EntityAlreadyExistsException;
import cz.fi.muni.pv168.todo.business.service.crud.EventCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.TemplateCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.TimeUnitCrudService;
import cz.fi.muni.pv168.todo.business.service.export.GenericImportService;
import cz.fi.muni.pv168.todo.business.service.validation.CategoryValidator;
import cz.fi.muni.pv168.todo.business.service.validation.EventValidator;
import cz.fi.muni.pv168.todo.business.service.validation.TemplateValidator;
import cz.fi.muni.pv168.todo.business.service.validation.TimeUnitValidator;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationException;
import cz.fi.muni.pv168.todo.io.exception.SnapshotIOException;
import cz.fi.muni.pv168.todo.io.imports.JsonImporter;
import cz.fi.muni.pv168.todo.storage.memory.InMemoryCategoryRepository;
import cz.fi.muni.pv168.todo.storage.memory.InMemoryEventRepository;
import cz.fi.muni.pv168.todo.storage.memory.InMemoryTemplateRepository;
import cz.fi.muni.pv168.todo.storage.memory.InMemoryTimeUnitRepository;
import java.awt.Color;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class GenericImportServiceIntegrationTest
{
    private static final Path PROJECT_ROOT = Paths.get(System.getProperty("project.basedir", "")).toAbsolutePath();
    private static final Path TEST_RESOURCES = PROJECT_ROOT.resolve(Path.of("src", "test", "resources"));

    private EventCrudService eventCrudService;
    private TemplateCrudService templateCrudService;
    private CategoryCrudService categoryCrudService;
    private TimeUnitCrudService timeUnitCrudService;

    private GenericImportService genericImportService;

    @BeforeEach
    void setUp() {
        var eventRepository = new InMemoryEventRepository(List.of());
        var eventValidator = new EventValidator();
        eventCrudService = new EventCrudService(eventRepository, eventValidator);

        var templateRepository = new InMemoryTemplateRepository(List.of());
        var templateValidator = new TemplateValidator();
        templateCrudService = new TemplateCrudService(templateRepository, templateValidator);

        var categoryRepository = new InMemoryCategoryRepository(List.of());
        var categoryValidator = new CategoryValidator();
        categoryCrudService = new CategoryCrudService(categoryRepository, categoryValidator);

        var timeUnitRepository = new InMemoryTimeUnitRepository(List.of());
        var timeUnitValidator = new TimeUnitValidator();
        timeUnitCrudService = new TimeUnitCrudService(timeUnitRepository, timeUnitValidator);

        genericImportService = new GenericImportService(
                eventCrudService,
                categoryCrudService,
                templateCrudService,
                timeUnitCrudService,
                List.of(new JsonImporter())
        );
    }

    @Test
    void importNothing() {
        Path importFilePath = TEST_RESOURCES.resolve("empty.json");
        genericImportService.importData(importFilePath.toString());

        assertThat(eventCrudService.findAll())
                .isEmpty();
        assertThat(templateCrudService.findAll())
                .isEmpty();
        assertThat(categoryCrudService.findAll())
                .isEmpty();
        assertThat(timeUnitCrudService.findAll())
                .isEmpty();
    }

    @Test
    void singleCategory() {
        Path importFilePath = TEST_RESOURCES.resolve("single-category.json");
        genericImportService.importData(importFilePath.toString());

        assertThat(categoryCrudService.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(
                        new Category(
                                UUID.fromString("120be0a2-16ec-44b4-aad6-5e6826d9e532"),
                                "TestCategory",
                                Color.PINK
                        )
                );
    }

    @Test
    void multipleCategories() {
        Path importFilePath = TEST_RESOURCES.resolve("multiple-categories.json");
        genericImportService.importData(importFilePath.toString());

        assertThat(categoryCrudService.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        new Category(
                                UUID.fromString("37a00d72-30ba-4a84-81ba-64393163918d"),
                                "First Category",
                                Color.BLACK
                        ),
                        new Category(
                                UUID.fromString("4d5164d8-5ed4-4694-9165-5119a0ece612"),
                                "Second Category",
                                Color.PINK
                        )
                );
    }

    @Test
    void invalidNameFails() {
        Path importFilePath = TEST_RESOURCES.resolve("invalid-name-category.json");

        var stringPath = importFilePath.toString();
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> genericImportService.importData(stringPath))
                .withMessageContaining("'Category name' length is not between 2 (inclusive) and 150 (inclusive)");
    }

    @Test
    void invalidColorFails() {
        Path importFilePath = TEST_RESOURCES.resolve("invalid-color-category.json");

        var stringPath = importFilePath.toString();
        assertThatExceptionOfType(SnapshotIOException.class)
                .isThrownBy(() -> genericImportService.importData(stringPath));
    }

    @Test
    void duplicateGuidFails() {
        Path importFilePath = TEST_RESOURCES.resolve("duplicate-guid-categories.json");

        var stringPath = importFilePath.toString();
        assertThatExceptionOfType(EntityAlreadyExistsException.class)
                .isThrownBy(() -> genericImportService.importData(stringPath))
                .withMessage("Category with given guid already exists: 120be0a2-16ec-44b4-aad6-5e6826d9e532");
    }
}
