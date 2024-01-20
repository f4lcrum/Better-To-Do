package cz.fi.muni.pv168.todo.storage.export;

import cz.fi.muni.pv168.todo.business.entity.Category;
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

    private List<String> getFileContents(String filePath) throws IOException {
        try (var reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines().toList();
        } catch (IOException ioe) {
            throw ioe;
        }
    }
}
