package cz.fi.muni.pv168.todo.io.imports;

import com.fasterxml.jackson.databind.DatabindException;
import cz.fi.muni.pv168.todo.io.AbstractImporterExporter;
import cz.fi.muni.pv168.todo.io.DatabaseSnapshot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class JsonImporter extends AbstractImporterExporter {

    public Optional<DatabaseSnapshot> importDatabase(String filepath) {
        DatabaseSnapshot databaseSnapshot = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            databaseSnapshot = getObjectMapper().readValue(bufferedReader, DatabaseSnapshot.class);
        } catch (DatabindException dbe) {
            System.err.println(dbe);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        return databaseSnapshot == null ? Optional.empty() : Optional.of(databaseSnapshot);
    }

}
