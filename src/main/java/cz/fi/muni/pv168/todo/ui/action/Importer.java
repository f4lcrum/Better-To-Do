package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.service.export.format.Format;

import java.util.Collection;

public interface Importer {

    /**
     * Imports data from a file.
     *
     * @param filePath absolute path of the import file from
     */
    void importData(String filePath);

    /**
     * Gets all available formats for export.
     */
    Collection<Format> getFormats();
}
