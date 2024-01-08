package cz.fi.muni.pv168.todo.io.exports;

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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class JsonExporter extends AbstractImporterExporter {

    public void exportDatabase(String filepath, DatabaseSnapshot snapshot) {
        IoDatabaseSnapshot ioSnapshot = new IoDatabaseSnapshot(
                mapIoEntities(snapshot.categories(), new CategoryIoMapper()),
                mapIoEntities(snapshot.events(), new EventIoMapper()),
                mapIoEntities(snapshot.templates(), new TemplateIoMapper()),
                mapIoEntities(snapshot.timeUnits(), new TimeUnitIoMapper())
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            String jsonContent = getObjectMapper().writeValueAsString(ioSnapshot);
            writer.write(jsonContent);
        } catch (IOException e) {
            throw new SnapshotIOException("Error occurred when exporting the database", e);
        }
    }

    private <E extends Entity, M extends IoEntity> List<M> mapIoEntities(Collection<E> entities, IoMapper<E, M> mapper) {
        return entities.stream()
                .map(mapper::mapToIo)
                .toList();
    }
}
