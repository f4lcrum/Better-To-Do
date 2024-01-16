package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.service.export.format.Format;

import java.util.Collection;
import java.util.Set;

/**
 * Generic mechanism, allowing to export data to a file.
 */
public interface Exporter {

    /**
     * Exports data to a file.
     *
     * @param filePath absolute path of the export file (to be created or overwritten)
     */
    void exportData(String filePath);

    /**
     * Gets all available formats for export.
     */
    Collection<Format> getFormats();

    /**
     * Gets all supported file extensions
     */
    Set<String> getSupportedFileExtensions();

    /**
     * Checks whether the exporter is able to export into the file format denoted by the file extension.
     *
     * @param filePath absolute path of the export file (to be created or overwritten)
     */
    boolean acceptsFileFormat(String filePath);
}
