package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.business.service.export.batch.BatchOperationException;
import cz.fi.muni.pv168.todo.business.service.export.format.Format;
import java.util.Collection;
import javax.swing.AbstractAction;

public interface ImportStrategy {

    /**
     * Imports data from a file.
     *
     * @param filePath absolute path of the export file (to be created or overwritten)
     * @throws BatchOperationException if the import cannot be done
     */
    boolean importData(String filePath);

    /**
     * Gets all available formats for import.
     */
    Collection<Format> getFormats();

}
