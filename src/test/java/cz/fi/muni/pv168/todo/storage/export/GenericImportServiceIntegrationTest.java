package cz.fi.muni.pv168.todo.storage.export;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.EventCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.TemplateCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.TimeUnitCrudService;
import cz.fi.muni.pv168.todo.business.service.export.GenericImportService;
import cz.fi.muni.pv168.todo.business.service.validation.CategoryValidator;
import cz.fi.muni.pv168.todo.business.service.validation.EventValidator;
import cz.fi.muni.pv168.todo.business.service.validation.TemplateValidator;
import cz.fi.muni.pv168.todo.business.service.validation.TimeUnitValidator;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class GenericImportServiceIntegrationTest
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
                        new Category(UUID.fromString("76c59af3-6e9a-4fcb-bd7e-d0a163ed8b45"), "TestCategory", Color.PINK)
                );
    }
}
