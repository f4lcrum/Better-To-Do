package cz.fi.muni.pv168.todo.io.imports;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.io.AbstractImporterExporter;
import cz.fi.muni.pv168.todo.io.IoDatabaseSnapshot;
import cz.fi.muni.pv168.todo.io.entity.IoEntity;
import cz.fi.muni.pv168.todo.io.exception.SnapshotIOException;
import cz.fi.muni.pv168.todo.io.mapper.CategoryIoMapper;
import cz.fi.muni.pv168.todo.io.mapper.EventIoMapper;
import cz.fi.muni.pv168.todo.io.mapper.IoMapper;
import cz.fi.muni.pv168.todo.io.mapper.TemplateIoMapper;
import cz.fi.muni.pv168.todo.io.mapper.TimeUnitIoMapper;
import cz.fi.muni.pv168.todo.storage.sql.snapshot.DatabaseSnapshot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;

public class JsonImporter extends AbstractImporterExporter {

    public DatabaseSnapshot importDatabase(String filepath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            IoDatabaseSnapshot ioSnapshot = getObjectMapper().readValue(bufferedReader, IoDatabaseSnapshot.class);

            return new DatabaseSnapshot(
                    mapIoEntities(ioSnapshot.categories(), new CategoryIoMapper()),
                    mapIoEntities(ioSnapshot.events(), new EventIoMapper()),
                    mapIoEntities(ioSnapshot.templates(), new TemplateIoMapper()),
                    mapIoEntities(ioSnapshot.timeUnits(), new TimeUnitIoMapper())
            );
        } catch (Exception e) {
            throw new SnapshotIOException("Error occurred when importing the database", e);
        }
    }

    private <E extends Entity, M extends IoEntity> List<E> mapIoEntities(Collection<M> entities, IoMapper<E, M> mapper) {
        return entities.stream()
                .map(mapper::mapToBusiness)
                .toList();
    }
}
