package cz.fi.muni.pv168.todo.io.imports;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.business.service.export.batch.Batch;
import cz.fi.muni.pv168.todo.business.service.export.batch.BatchImporter;
import cz.fi.muni.pv168.todo.business.service.export.format.Format;
import cz.fi.muni.pv168.todo.io.AbstractImporterExporter;
import cz.fi.muni.pv168.todo.io.IoDatabaseSnapshot;
import cz.fi.muni.pv168.todo.io.entity.IoEntity;
import cz.fi.muni.pv168.todo.io.exception.SnapshotIOException;
import cz.fi.muni.pv168.todo.io.mapper.CategoryIoMapper;
import cz.fi.muni.pv168.todo.io.mapper.EventIoMapper;
import cz.fi.muni.pv168.todo.io.mapper.IoMapper;
import cz.fi.muni.pv168.todo.io.mapper.TemplateIoMapper;
import cz.fi.muni.pv168.todo.io.mapper.TimeUnitIoMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;

public class JsonImporter extends AbstractImporterExporter implements BatchImporter {

    private static final Format FORMAT = new Format("JSON", List.of("json"));

    @Override
    public Batch importBatch(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            IoDatabaseSnapshot ioSnapshot = getObjectMapper().readValue(bufferedReader, IoDatabaseSnapshot.class);

            return new Batch(
                    mapIoEntities(ioSnapshot.events(), new EventIoMapper()),
                    mapIoEntities(ioSnapshot.categories(), new CategoryIoMapper()),
                    mapIoEntities(ioSnapshot.templates(), new TemplateIoMapper()),
                    mapIoEntities(ioSnapshot.timeUnits(), new TimeUnitIoMapper())
            );
        } catch (Exception e) {
            throw new SnapshotIOException("Error occurred when importing the database", e);
        }
    }

    @Override
    public Format getFormat() {
        return FORMAT;
    }

    private <E extends Entity, M extends IoEntity> List<E> mapIoEntities(Collection<M> entities, IoMapper<E, M> mapper) {
        return entities.stream()
                .map(mapper::mapToBusiness)
                .toList();
    }
}

