package cz.fi.muni.pv168.todo.io.exports;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.business.service.export.batch.Batch;
import cz.fi.muni.pv168.todo.business.service.export.batch.BatchExporter;
import cz.fi.muni.pv168.todo.business.service.export.format.Format;
import cz.fi.muni.pv168.todo.io.AbstractImporterExporter;
import cz.fi.muni.pv168.todo.io.DataManipulationException;
import cz.fi.muni.pv168.todo.io.IoDatabaseSnapshot;
import cz.fi.muni.pv168.todo.io.entity.IoEntity;
import cz.fi.muni.pv168.todo.io.mapper.CategoryIoMapper;
import cz.fi.muni.pv168.todo.io.mapper.EventIoMapper;
import cz.fi.muni.pv168.todo.io.mapper.IoMapper;
import cz.fi.muni.pv168.todo.io.mapper.TemplateIoMapper;
import cz.fi.muni.pv168.todo.io.mapper.TimeUnitIoMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class JsonExporter extends AbstractImporterExporter implements BatchExporter {

    private static final Format FORMAT = new Format("JSON", List.of("json"));

    @Override
    public Format getFormat() {
        return FORMAT;
    }

    @Override
    public void exportBatch(Batch batch, String filePath) {
        IoDatabaseSnapshot ioSnapshot = new IoDatabaseSnapshot(
                mapIoEntities(batch.categories(), new CategoryIoMapper()),
                mapIoEntities(batch.events(), new EventIoMapper()),
                mapIoEntities(batch.templates(), new TemplateIoMapper()),
                mapIoEntities(batch.timeUnits(), new TimeUnitIoMapper())
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String jsonContent = getObjectMapper().writeValueAsString(ioSnapshot);
            writer.write(jsonContent);
        } catch (IOException e) {
            throw new DataManipulationException("Error occurred when exporting the database", e);
        }
    }

    private <E extends Entity, M extends IoEntity> List<M> mapIoEntities(Collection<E> entities, IoMapper<E, M> mapper) {
        return entities.stream()
                .map(mapper::mapToIo)
                .toList();
    }
}
