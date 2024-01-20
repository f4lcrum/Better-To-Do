package cz.fi.muni.pv168.todo.storage.export;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
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
import cz.fi.muni.pv168.todo.io.imports.JsonImporter;
import cz.fi.muni.pv168.todo.storage.memory.InMemoryCategoryRepository;
import cz.fi.muni.pv168.todo.storage.memory.InMemoryEventRepository;
import cz.fi.muni.pv168.todo.storage.memory.InMemoryTemplateRepository;
import cz.fi.muni.pv168.todo.storage.memory.InMemoryTimeUnitRepository;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import cz.fi.muni.pv168.todo.ui.action.Importer;
import cz.fi.muni.pv168.todo.ui.workers.AsyncImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GenericImportServiceIntegrationTest {
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
    void singleTimeUnit() {
        Path importFilePath = TEST_RESOURCES.resolve("single-timeunit.json");
        genericImportService.importData(importFilePath.toString());

        assertThat(timeUnitCrudService.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(
                        new TimeUnit(
                                UUID.fromString("25aae21c-74f6-425d-8b52-b00cb6b34efb"),
                                true,
                                "Hours",
                                 1,
                                0
                        )
                );
    }

    @Test
    void multipleTimeUnits() {
        Path importFilePath = TEST_RESOURCES.resolve("multiple-timeunit.json");
        genericImportService.importData(importFilePath.toString());

        assertThat(timeUnitCrudService.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        new TimeUnit(
                                UUID.fromString("25aae21c-74f6-425d-8b52-b00cb6b34efb"),
                                true,
                                "Hours",
                                1,
                                0
                        ),
                        new TimeUnit(
                                UUID.fromString("0e587bc6-83a1-44ac-a911-0bfe0df998e4"),
                                true,
                                "Minutes",
                                2,
                                1
                        ),
                        new TimeUnit(
                                UUID.fromString("4253a7c1-0e78-4c24-b97c-810bc15100ae"),
                                false,
                                "Sprint",
                                3,
                                54
                        )
                );
    }

    @Test
    void singleTemplate() {
        Path importFilePath = TEST_RESOURCES.resolve("single-template.json");
        genericImportService.importData(importFilePath.toString());

        assertThat(templateCrudService.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(
                        new Template(
                                UUID.fromString("7ee351e5-a704-4685-9b02-017d28835ffb"),
                                "Work Template",
                                "Work Task",
                                new Category(
                                        UUID.fromString("158d4716-eec2-4e03-bc4c-450e31d5d38a"),
                                        "Work",
                                        new Color(255, 0, 0, 255)
                                ),
                                LocalTime.of(8, 0),
                                new TimeUnit(
                                        UUID.fromString("25aae21c-74f6-425d-8b52-b00cb6b34efb"),
                                        true,
                                        "Hours",
                                        1,
                                        0
                                ),
                                8,
                                "Template for work tasks"
                        )
                );
    }

    @Test
    void multipleTemplates() {
        Path importFilePath = TEST_RESOURCES.resolve("multiple-templates.json");
        genericImportService.importData(importFilePath.toString());

        assertThat(templateCrudService.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        new Template(
                                UUID.fromString("7ee351e5-a704-4685-9b02-017d28835ffb"),
                                "Work Template",
                                "Work Task",
                                new Category(
                                        UUID.fromString("158d4716-eec2-4e03-bc4c-450e31d5d38a"),
                                        "Work",
                                        new Color(255, 0, 0, 255)
                                ),
                                LocalTime.of(8, 0),
                                new TimeUnit(
                                        UUID.fromString("25aae21c-74f6-425d-8b52-b00cb6b34efb"),
                                        true,
                                        "Hours",
                                        1,
                                        0
                                ),
                                8,
                                "Template for work tasks"
                        ),
                        new Template(
                                UUID.fromString("f5c694b4-6790-4b50-847e-405da9c209a3"),
                                "Work Task",
                                "Work Task",
                                new Category(
                                        UUID.fromString("158d4716-eec2-4e03-bc4c-450e31d5d38a"),
                                        "Work",
                                        new Color(255, 0, 0, 255)
                                ),
                                LocalTime.of(12, 23),
                                new TimeUnit(
                                        UUID.fromString("25aae21c-74f6-425d-8b52-b00cb6b34efb"),
                                        true,
                                        "Hours",
                                        1,
                                        0
                                ),
                                8,
                                ""
                        )
                );
    }


    @Test
    void singleEvent() {
        Path importFilePath = TEST_RESOURCES.resolve("single-event.json");
        genericImportService.importData(importFilePath.toString());

        assertThat(eventCrudService.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(
                        new Event(
                                UUID.fromString("9a42961b-4bf4-4c00-9be8-dde332a03d7f"),
                                "Work Task",
                                new Category(
                                        UUID.fromString("158d4716-eec2-4e03-bc4c-450e31d5d38a"),
                                        "Work",
                                        new Color(255, 0, 0, 255)
                                ),
                                LocalDate.of(2024, 1, 24),
                                LocalTime.of(8, 0),
                                new TimeUnit(
                                        UUID.fromString("0e587bc6-83a1-44ac-a911-0bfe0df998e4"),
                                        true,
                                        "Minutes",
                                        0,
                                        1
                                ),
                                8,
                                ""
                        )
                );
    }

    @Test
    void multipleEvents() {
        Path importFilePath = TEST_RESOURCES.resolve("multiple-events.json");
        genericImportService.importData(importFilePath.toString());

        assertThat(eventCrudService.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(
                        new Event(
                                UUID.fromString("9a42961b-4bf4-4c00-9be8-dde332a03d7f"),
                                "Work Task",
                                new Category(
                                        UUID.fromString("158d4716-eec2-4e03-bc4c-450e31d5d38a"),
                                        "Work",
                                        new Color(255, 0, 0, 255)
                                ),
                                LocalDate.of(2024, 1, 24),
                                LocalTime.of(8, 0),
                                new TimeUnit(
                                        UUID.fromString("0e587bc6-83a1-44ac-a911-0bfe0df998e4"),
                                        true,
                                        "Minutes",
                                        0,
                                        1
                                ),
                                8,
                                ""
                        ),
                        new Event(
                                UUID.fromString("a5d4a9c1-6d25-4d9c-80f9-48573315b4cd"),
                                "Work Task",
                                new Category(
                                        UUID.fromString("e57c25c2-9106-48ed-a37c-613b72b7cdac"),
                                        "xadadasd",
                                        new Color(255, 153, 153, 255)
                                ),
                                LocalDate.of(2024, 2, 1),
                                LocalTime.of(12, 23),
                                new TimeUnit(
                                        UUID.fromString("25aae21c-74f6-425d-8b52-b00cb6b34efb"),
                                        true,
                                        "Hours",
                                        1,
                                        0
                                ),
                                8,
                                ""
                        )

                );
    }
    @Test
    void invalidFormatFails() {
        Path importFilePath = TEST_RESOURCES.resolve("invalid.format");

        Importer importer = new AsyncImporter(
                genericImportService,
                () -> {
                }
        );
        var stringPath = importFilePath.toString();
        assertThat(importer.acceptsFileFormat(stringPath)).isFalse();
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
        assertFalse(genericImportService.importData(stringPath));
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
