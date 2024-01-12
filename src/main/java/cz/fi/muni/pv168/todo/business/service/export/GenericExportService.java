package cz.fi.muni.pv168.todo.business.service.export;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import cz.fi.muni.pv168.todo.business.service.export.batch.Batch;
import cz.fi.muni.pv168.todo.business.service.export.batch.BatchExporter;
import cz.fi.muni.pv168.todo.business.service.export.batch.BatchOperationException;
import cz.fi.muni.pv168.todo.business.service.export.format.Format;
import cz.fi.muni.pv168.todo.business.service.export.format.FormatMapping;

import java.util.Collection;

public class GenericExportService implements ExportService {

    private final CrudService<Event> eventCrudService;
    private final CrudService<Category> categoryCrudService;
    private final CrudService<Template> templateCrudService;
    private final CrudService<TimeUnit> timeUnitCrudService;
    private final FormatMapping<BatchExporter> exporters;

    public GenericExportService(
            CrudService<Event> eventCrudService,
            CrudService<Category> categoryCrudService,
            CrudService<Template> templateCrudService,
            CrudService<TimeUnit> timeUnitCrudService,
            Collection<BatchExporter> exporters
    ) {
        this.eventCrudService = eventCrudService;
        this.categoryCrudService = categoryCrudService;
        this.templateCrudService = templateCrudService;
        this.timeUnitCrudService = timeUnitCrudService;
        this.exporters = new FormatMapping<>(exporters);
    }

    @Override
    public Collection<Format> getFormats() {
        return exporters.getFormats();
    }

    @Override
    public void exportData(String filePath) {
        var exporter = getExporter(filePath);

        var batch = new Batch(
                eventCrudService.findAll(),
                categoryCrudService.findAll(),
                templateCrudService.findAll(),
                timeUnitCrudService.findAll()
        );
        exporter.exportBatch(batch, filePath);
    }

    private BatchExporter getExporter(String filePath) {
        var extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        var importer = exporters.findByExtension(extension);
        if (importer == null)
            throw new BatchOperationException("Extension %s has no registered formatter".formatted(extension));
        return importer;
    }
}
