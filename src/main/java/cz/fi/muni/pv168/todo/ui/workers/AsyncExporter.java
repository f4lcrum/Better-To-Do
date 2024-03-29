package cz.fi.muni.pv168.todo.ui.workers;

import cz.fi.muni.pv168.todo.business.service.export.ExportService;
import cz.fi.muni.pv168.todo.business.service.export.format.Format;
import cz.fi.muni.pv168.todo.ui.action.Exporter;

import javax.swing.SwingWorker;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of asynchronous exporter for UI.
 */
public class AsyncExporter implements Exporter {

    private final ExportService exportService;
    private final Runnable onFinish;

    public AsyncExporter(ExportService exportService, Runnable onFinish) {
        this.exportService = Objects.requireNonNull(exportService);
        this.onFinish = onFinish;
    }

    @Override
    public Collection<Format> getFormats() {
        return exportService.getFormats();
    }

    @Override
    public void exportData(String filePath) {
        var asyncWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                exportService.exportData(filePath);
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
    public Set<String> getSupportedFileExtensions() {
        return getFormats().stream()
                .flatMap(format -> format.extensions().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean acceptsFileFormat(String filePath) {
        String fileExtension = "";
        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            fileExtension = filePath.substring(i + 1);
        }
        return getSupportedFileExtensions().contains(fileExtension);
    }
}
