package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.service.export.format.Format;

import cz.fi.muni.pv168.todo.ui.action.strategy.ImportStrategy;
import java.util.Collection;
import java.util.Set;

public interface Importer {

    /**
     * Imports data from a file.
     *
     * @param filePath absolute path of the import file from
     */
    void importData(String filePath);

    void setStrategy(ImportStrategy strategy);

    /**
     * Gets all available formats for export.
     */
    Collection<Format> getFormats();

    /**
     * Gets all supported file extensions
     */
    Set<String> getSupportedFileExtensions();

    /**
     * Checks whether the importer is able to export into the file format denoted by the file extension.
     *
     * @param filePath absolute path of the export file (to be created or overwritten)
     */
    boolean acceptsFileFormat(String filePath);
}
