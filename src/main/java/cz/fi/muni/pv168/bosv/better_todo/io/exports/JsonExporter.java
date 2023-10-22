package cz.fi.muni.pv168.bosv.better_todo.io.exports;

import cz.fi.muni.pv168.bosv.better_todo.io.AbstractImporterExporter;
import cz.fi.muni.pv168.bosv.better_todo.io.DatabaseSnapshot;
import lombok.NonNull;

import java.io.*;

public class JsonExporter extends AbstractImporterExporter {

    public boolean exportDatabase(@NonNull String filepath, @NonNull DatabaseSnapshot snapshot) {
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
