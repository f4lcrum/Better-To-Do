package cz.fi.muni.pv168.todo.business.service.export;

import cz.fi.muni.pv168.todo.business.service.export.batch.BatchOperationException;
import cz.fi.muni.pv168.todo.business.service.export.format.Format;

import cz.fi.muni.pv168.todo.ui.action.strategy.ImportStrategy;
import java.util.Collection;

/**
 * Generic mechanism, allowing to import data from a file.
 */
public interface ImportService {

    /**
     * Imports data from a file.
     *
     * @param filePath absolute path of the export file (to be created or overwritten)
     * @throws BatchOperationException if the import cannot be done
     */
    boolean importData(String filePath);

    void setStrategy(ImportStrategy strategy);

    /**
     * Gets all available formats for import.
     */
    Collection<Format> getFormats();
}
