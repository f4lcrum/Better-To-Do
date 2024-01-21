package cz.fi.muni.pv168.todo.ui.action.strategy;


import cz.fi.muni.pv168.todo.business.service.export.GenericImportService;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import cz.fi.muni.pv168.todo.business.service.export.batch.BatchImporter;
import cz.fi.muni.pv168.todo.business.service.export.batch.BatchOperationException;
import cz.fi.muni.pv168.todo.business.service.export.format.Format;
import cz.fi.muni.pv168.todo.business.service.export.format.FormatMapping;
import cz.fi.muni.pv168.todo.io.DataManipulationException;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;

public class AppendImportStrategy implements ImportStrategy {

    private final CrudService<Event> eventCrudService;
    private final CrudService<Category> categoryCrudService;
    private final CrudService<Template> templateCrudService;
    private final CrudService<TimeUnit> timeUnitCrudService;
    private final FormatMapping<BatchImporter> importers;

    public AppendImportStrategy(CrudService<Event> eventCrudService,
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
    public boolean importData(String filePath) {
        var importer = getImporter(filePath);
        try {
            var batch = importer.importBatch(filePath);
            batch.timeUnits().forEach(this::createTimeUnitIfNotExists);
            batch.categories().forEach(this::createCategoryIfNotExists);
            batch.templates().forEach(this::createTemplateIfNotExists);
            batch.events().forEach(this::createEventIfNotExists);

        } catch (DataManipulationException | DataStorageException exception) {
            Logger.getLogger(GenericImportService.class.getName()).log(Level.SEVERE, "An error occurred during import!", exception);
            return false;
        }

        return true;
    }

    private void createEventIfNotExists(Event event) {
        if (eventCrudService.findByGuid(event.getGuid()).isEmpty()) {
            eventCrudService.create(event).intoException();
        }
    }

    private void createTemplateIfNotExists(Template template) {
        if (templateCrudService.findByGuid(template.getGuid()).isEmpty()) {
            templateCrudService.create(template).intoException();
        }
    }

    private void createCategoryIfNotExists(Category category) {
        if (categoryCrudService.findByGuid(category.getGuid()).isEmpty()) {
            categoryCrudService.create(category).intoException();
        }
    }

    private void createTimeUnitIfNotExists(TimeUnit timeUnit) {
        if (timeUnitCrudService.findByGuid(timeUnit.getGuid()).isEmpty()) {
            timeUnitCrudService.create(timeUnit).intoException();
        }
    }

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
