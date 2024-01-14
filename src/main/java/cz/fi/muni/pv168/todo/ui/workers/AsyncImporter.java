package cz.fi.muni.pv168.todo.ui.workers;

import cz.fi.muni.pv168.todo.business.service.export.ImportService;
import cz.fi.muni.pv168.todo.business.service.export.format.Format;
import cz.fi.muni.pv168.todo.ui.action.Importer;

import javax.swing.SwingWorker;
import java.util.Collection;
import java.util.Objects;

/**
 * Implementation of asynchronous importer for UI.
 */
public class AsyncImporter implements Importer {

    private final ImportService importService;
    private final Runnable onFinish;

    public AsyncImporter(ImportService importService, Runnable onFinish) {
        this.importService = Objects.requireNonNull(importService);
        this.onFinish = onFinish;
    }

    @Override
    public void importData(String filePath) {
        var asyncWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                importService.importData(filePath);
                return null;
            }

            @Override
            protected void done() {
                super.done();
                onFinish.run();
            }
        };
        asyncWorker.execute();
    }

    @Override
    public Collection<Format> getFormats() {
        return importService.getFormats();
    }

}
