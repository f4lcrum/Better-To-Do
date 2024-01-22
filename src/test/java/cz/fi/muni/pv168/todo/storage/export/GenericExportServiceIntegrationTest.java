package cz.fi.muni.pv168.todo.storage.export;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.EventCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.TemplateCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.TimeUnitCrudService;
import cz.fi.muni.pv168.todo.business.service.export.GenericExportService;
import cz.fi.muni.pv168.todo.business.service.validation.CategoryValidator;
import cz.fi.muni.pv168.todo.business.service.validation.EventValidator;
import cz.fi.muni.pv168.todo.business.service.validation.TemplateValidator;
import cz.fi.muni.pv168.todo.business.service.validation.TimeUnitValidator;
import cz.fi.muni.pv168.todo.io.exports.JsonExporter;
import cz.fi.muni.pv168.todo.storage.memory.InMemoryCategoryRepository;
import cz.fi.muni.pv168.todo.storage.memory.InMemoryEventRepository;
import cz.fi.muni.pv168.todo.storage.memory.InMemoryTemplateRepository;
import cz.fi.muni.pv168.todo.storage.memory.InMemoryTimeUnitRepository;
import static org.assertj.core.api.Assertions.assertThat;
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class GenericExportServiceIntegrationTest {

    private static final Path PROJECT_ROOT = Paths.get(System.getProperty("project.basedir", "")).toAbsolutePath();
    private static final Path TEST_RESOURCES = PROJECT_ROOT.resolve(Path.of("src", "test", "resources"));
    private static final Path TEST_RESOURCES_EXPECTED = PROJECT_ROOT.resolve(
            Path.of("src", "test", "resources", "export", "expected")
    );

    private EventCrudService eventCrudService;
    private TemplateCrudService templateCrudService;
    private CategoryCrudService categoryCrudService;
    private TimeUnitCrudService timeUnitCrudService;
    private GenericExportService genericExportService;

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

        genericExportService = new GenericExportService(
                eventCrudService,
                categoryCrudService,
                templateCrudService,
                timeUnitCrudService,
                List.of(new JsonExporter())
        );
    }

    @Test
    void exportingEmptyDatabaseSucceeds() throws IOException {
        Path exportResultFilePath = TEST_RESOURCES.resolve("export/expected/empty-database-export-result.json");
        Path exportFilePath = TEST_RESOURCES.resolve("empty-database-export.json");
        genericExportService.exportData(exportFilePath.toString());

        assertThat(getFileContents(exportFilePath.toString()))
                .hasSameElementsAs(getFileContents(exportResultFilePath.toString()));
        Files.delete(exportFilePath);
    }

    @Test
    void exportingMultipleCategories() throws IOException {
        Category category1 = new Category(UUID.fromString("37a00d72-30ba-4a84-81ba-64393163918d"), "First Category", new Color(0, 0, 0, 255));
        Category category2 = new Category(UUID.fromString("4d5164d8-5ed4-4694-9165-5119a0ece612"), "Second Category", new Color(255, 175, 175, 255));
        categoryCrudService.create(category1);
        categoryCrudService.create(category2);

        Path exportResultFilePath = TEST_RESOURCES_EXPECTED.resolve("multiple-categories-result.json");
        Path exportFilePath = TEST_RESOURCES.resolve("exported-multiple-categories.json");
        genericExportService.exportData(exportFilePath.toString());

        assertThat(getFileContents(exportFilePath.toString()))
                .hasSameElementsAs(getFileContents(exportResultFilePath.toString()));
        Files.delete(exportFilePath);
    }

    @Test
    void exportingMultipleTimeUnits() throws IOException {
        TimeUnit timeUnit1 = new TimeUnit(UUID.fromString("25aae21c-74f6-425d-8b52-b00cb6b34efb"), true, "Hours", 1, 0);
        TimeUnit timeUnit2 = new TimeUnit(UUID.fromString("0e587bc6-83a1-44ac-a911-0bfe0df998e4"), true, "Minutes", 2, 1);
        TimeUnit timeUnit3 = new TimeUnit(UUID.fromString("4253a7c1-0e78-4c24-b97c-810bc15100ae"), false, "Sprint", 3, 54);
        timeUnitCrudService.create(timeUnit1);
        timeUnitCrudService.create(timeUnit2);
        timeUnitCrudService.create(timeUnit3);

        Path exportFilePath = TEST_RESOURCES.resolve("exported-multiple-timeunit.json");
        Path exportResultFilePath = TEST_RESOURCES_EXPECTED.resolve("multiple-timeunit-result.json");
        genericExportService.exportData(exportFilePath.toString());

        assertThat(getFileContents(exportFilePath.toString()))
                .hasSameElementsAs(getFileContents(exportResultFilePath.toString()));
        Files.delete(exportFilePath);
    }

    @Test
    void exportingMultipleTemplates() throws IOException {
        Category workCategory = new Category(UUID.fromString("37a00d72-30ba-4a84-81ba-64393163918d"), "Work Category", new Color(0, 0, 0, 255));
        TimeUnit hourUnit = new TimeUnit(UUID.fromString("0e587bc6-83a1-44ac-a911-0bfe0df998e4"), true, "Hours", 1, 0);
        Template template1 = new Template(UUID.fromString("7ee351e5-a704-4685-9b02-017d28835ffb"), "Work Template", "Work Task", workCategory, LocalTime.of(8, 0), hourUnit, 8, "Template for work tasks");
        Template template2 = new Template(UUID.fromString("f5c694b4-6790-4b50-847e-405da9c209a3"), "Home Template", "Home Task", workCategory, LocalTime.of(9, 30), hourUnit, 3, "Template for home tasks");
        templateCrudService.create(template1);
        templateCrudService.create(template2);

        Path exportFilePath = TEST_RESOURCES.resolve("exported-multiple-templates.json");
        Path exportResultFilePath = TEST_RESOURCES_EXPECTED.resolve("multiple-templates-result.json");
        genericExportService.exportData(exportFilePath.toString());

        assertThat(getFileContents(exportFilePath.toString()))
                .hasSameElementsAs(getFileContents(exportResultFilePath.toString()));
        Files.delete(exportFilePath);
    }

    @Test
    void exportingMultipleEvents() throws IOException {
        Category personalCategory = new Category(UUID.fromString("37a00d72-30ba-4a84-81ba-64393163918d"), "Personal", new Color(255, 200, 200));
        TimeUnit minuteUnit = new TimeUnit(UUID.fromString("0e587bc6-83a1-44ac-a911-0bfe0df998e4"), true, "Minute", 0, 30);
        Event event1 = new Event(UUID.fromString("9a42961b-4bf4-4c00-9be8-dde332a03d7f"), "Doctor Appointment", personalCategory, LocalDate.of(2024, 1, 20), LocalTime.of(10, 30), minuteUnit, 30, "General checkup");
        Event event2 = new Event(UUID.fromString("a5d4a9c1-6d25-4d9c-80f9-48573315b4cd"), "Team Meeting", personalCategory, LocalDate.of(2024, 1, 22), LocalTime.of(14, 0), minuteUnit, 60, "Weekly team sync");
        eventCrudService.create(event1);
        eventCrudService.create(event2);

        Path exportFilePath = TEST_RESOURCES.resolve("exported-multiple-events.json");
        Path exportResultFilePath = TEST_RESOURCES_EXPECTED.resolve("multiple-events-result.json");
        genericExportService.exportData(exportFilePath.toString());

        assertThat(getFileContents(exportFilePath.toString()))
                .hasSameElementsAs(getFileContents(exportResultFilePath.toString()));
        Files.delete(exportFilePath);
    }

    @Test
    void exportingCategoriesAndEvents() throws IOException {
        Category personalCategory = new Category(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), false, "Personal", new Color(255, 200, 200));
        Category workCategory = new Category(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"), true, "Work", new Color(100, 100, 100));
        categoryCrudService.create(personalCategory);
        categoryCrudService.create(workCategory);

        TimeUnit minuteUnit = new TimeUnit(UUID.fromString("0e587bc6-83a1-44ac-a911-0bfe0df998e4"), true, "Minute", 0, 30);
        Event dentistAppointment = new Event(UUID.fromString("123e4567-e89b-12d3-a456-426614174010"), "Dentist Appointment", personalCategory, LocalDate.of(2024, 1, 20), LocalTime.of(10, 30), minuteUnit, 30, "Dental checkup");
        Event codeReview = new Event(UUID.fromString("123e4567-e89b-12d3-a456-426614174011"), "Code Review", workCategory, LocalDate.of(2024, 1, 22), LocalTime.of(14, 0), minuteUnit, 60, "Review team's code");
        eventCrudService.create(dentistAppointment);
        eventCrudService.create(codeReview);

        Path exportFilePath = TEST_RESOURCES.resolve("exported-categories-events.json");
        Path exportResultFilePath = TEST_RESOURCES_EXPECTED.resolve("categories-events-result.json");
        genericExportService.exportData(exportFilePath.toString());

        assertThat(getFileContents(exportFilePath.toString()))
                .hasSameElementsAs(getFileContents(exportResultFilePath.toString()));
        Files.delete(exportFilePath);
    }

    @Test
    void exportingTemplatesWithCategories() throws IOException {
        Category defaultCategory = new Category(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174002"),
                "Default",
                new Color(255, 255, 255)
        );
        categoryCrudService.create(defaultCategory);

        TimeUnit minuteUnit = new TimeUnit(UUID.fromString("0e587bc6-83a1-44ac-a911-0bfe0df998e4"), true, "Minute", 0, 30);
        Template morningRoutine = new Template(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174020"),
                "Morning Routine",
                "Morning Routine Event",
                defaultCategory,
                LocalTime.of(7, 0),
                minuteUnit,
                10,
                "Morning preparation routine"
        );
        Template eveningReview = new Template(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174021"),
                "Evening Review",
                "Evening Routine Event",
                defaultCategory,
                LocalTime.of(20, 0),
                minuteUnit,
                30,
                "Review of daily accomplishments"
        );
        templateCrudService.create(morningRoutine);
        templateCrudService.create(eveningReview);

        Path exportFilePath = TEST_RESOURCES.resolve("templates-categories-result.json");
        Path exportResultFilePath = TEST_RESOURCES_EXPECTED.resolve("templates-categories-result.json");
        genericExportService.exportData(exportFilePath.toString());

        assertThat(getFileContents(exportFilePath.toString()))
                .hasSameElementsAs(getFileContents(exportResultFilePath.toString()));
        Files.delete(exportFilePath);
    }

    @Test
    void exportingTimeUnitsWithCategories() throws IOException {
        Category urgentCategory = new Category(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174003"),
                "Urgent",
                new Color(255, 0, 0)
        );
        categoryCrudService.create(urgentCategory);

        TimeUnit shortBreak = new TimeUnit(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174030"),
                false,
                "Short Break",
                0,
                15);
        TimeUnit longBreak = new TimeUnit(UUID.fromString("123e4567-e89b-12d3-a456-426614174031"),
                true,
                "Long Break",
                0,
                60);
        timeUnitCrudService.create(shortBreak);
        timeUnitCrudService.create(longBreak);

        Path exportFilePath = TEST_RESOURCES.resolve("timeunits-categories-result.json");
        Path exportResultFilePath = TEST_RESOURCES_EXPECTED.resolve("timeunits-categories-result.json");
        genericExportService.exportData(exportFilePath.toString());

        assertThat(getFileContents(exportFilePath.toString()))
                .hasSameElementsAs(getFileContents(exportResultFilePath.toString()));
        Files.delete(exportFilePath);
    }

    @Test
    void exportingAllEntities() throws IOException {
        Category urgentCategory = new Category(UUID.fromString("123e4567-e89b-12d3-a456-426614174004"), true, "Urgent", new Color(255, 0, 0));
        categoryCrudService.create(urgentCategory);

        TimeUnit workHour = new TimeUnit(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174040"),
                true,
                "Work Hour",
                1,
                0
        );
        timeUnitCrudService.create(workHour);

        Template morningRoutine = new Template(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174050"),
                "Morning Routine", "Morning Routine Event",
                urgentCategory,
                LocalTime.of(6, 0),
                workHour,
                2,
                "Start the day with a productive morning routine"
        );
        templateCrudService.create(morningRoutine);

        Event teamMeeting = new Event(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174060"),
                "Team Meeting",
                urgentCategory,
                LocalDate.of(2024, 6, 15),
                LocalTime.of(9, 30),
                workHour,
                60,
                "Weekly team meeting to discuss project progress");
        eventCrudService.create(teamMeeting);

        Path exportFilePath = TEST_RESOURCES.resolve("exported-all-entities.json");
        Path exportResultFilePath = TEST_RESOURCES_EXPECTED.resolve("all-entities-result.json");
        genericExportService.exportData(exportFilePath.toString());

        assertThat(getFileContents(exportFilePath.toString()))
                .hasSameElementsAs(getFileContents(exportResultFilePath.toString()));
        Files.delete(exportFilePath);
    }

    private List<String> getFileContents(String filePath) throws IOException {
        try (var reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines().toList();
        }
    }
}
