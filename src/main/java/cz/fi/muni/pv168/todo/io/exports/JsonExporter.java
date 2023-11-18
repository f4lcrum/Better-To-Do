package cz.fi.muni.pv168.todo.io.exports;

import cz.fi.muni.pv168.todo.io.AbstractImporterExporter;
import cz.fi.muni.pv168.todo.io.DatabaseSnapshot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JsonExporter extends AbstractImporterExporter {

    public boolean exportDatabase(String filepath, DatabaseSnapshot snapshot) {
        boolean passedSuccessfully = false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            String toExport = getObjectMapper().writeValueAsString(snapshot);
            bw.write(toExport);
            bw.flush();
            passedSuccessfully = true;
        } catch (IOException e) {
            System.err.println(e);
        }

        return passedSuccessfully;
    }
}