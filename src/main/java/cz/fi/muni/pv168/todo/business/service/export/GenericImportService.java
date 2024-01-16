package cz.fi.muni.pv168.todo.business.service.export;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import cz.fi.muni.pv168.todo.business.service.export.batch.BatchImporter;
import cz.fi.muni.pv168.todo.business.service.export.batch.BatchOperationException;
import cz.fi.muni.pv168.todo.business.service.export.format.Format;
import cz.fi.muni.pv168.todo.business.service.export.format.FormatMapping;

import java.util.Collection;

/**
 * Generic synchronous implementation of the {@link ImportService}.
 */
public class GenericImportService implements ImportService {

    private final CrudService<Event> eventCrudService;
    private final CrudService<Category> categoryCrudService;
    private final CrudService<Template> templateCrudService;
    private final CrudService<TimeUnit> timeUnitCrudService;
    private final FormatMapping<BatchImporter> importers;

    public GenericImportService(CrudService<Event> eventCrudService,
                                CrudService<Category> categoryCrudService,
                                CrudService<Template> templateCrudService,
                                CrudService<TimeUnit> timeUnitCrudService,
                                Collection<BatchImporter> importers) {
        this.eventCrudService = eventCrudService;
        this.categoryCrudService = categoryCrudService;
        this.templateCrudService = templateCrudService;
        this.timeUnitCrudService = timeUnitCrudService;
        this.importers = new FormatMapping<>(importers);
    }

    @Override
    public void importData(String filePath) {
        eventCrudService.deleteAll();
        templateCrudService.deleteAll();
        categoryCrudService.deleteAll();
        timeUnitCrudService.deleteAll();

        var batch = getImporter(filePath).importBatch(filePath);

        batch.timeUnits().forEach(this::createTimeUnit);
        batch.categories().forEach(this::createCategory);
        batch.templates().forEach(this::createTemplate);
        batch.events().forEach(this::createEvent);
    }

    private void createCategory(Category category) {
        categoryCrudService.create(category)
                .intoException();
    }

    private void createTemplate(Template template) {
        templateCrudService.create(template)
                .intoException();
    }

    private void createEvent(Event event) {
        eventCrudService.create(event)
                .intoException();
    }

    private void createTimeUnit(TimeUnit timeUnit) {
        timeUnitCrudService.create(timeUnit)
                .intoException();
    }

    @Override
    public Collection<Format> getFormats() {
        return importers.getFormats();
    }

    private BatchImporter getImporter(String filePath) {
        var extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        var importer = importers.findByExtension(extension);
        if (importer == null) {
            throw new BatchOperationException("Extension %s has no registered formatter".formatted(extension));
        }

        return importer;
    }
}