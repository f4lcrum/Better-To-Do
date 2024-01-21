package cz.fi.muni.pv168.todo.ui.workers;

import cz.fi.muni.pv168.todo.business.service.export.ImportService;
import cz.fi.muni.pv168.todo.business.service.export.format.Format;
import cz.fi.muni.pv168.todo.ui.action.Importer;

import cz.fi.muni.pv168.todo.ui.action.strategy.ImportStrategy;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of asynchronous importer for UI.
 */
public class AsyncImporter implements Importer {

    private final ImportService importService;
    private final Runnable onFinish;
    private ImportStrategy strategy;

    public AsyncImporter(ImportService importService, Runnable onFinish) {
        this.importService = Objects.requireNonNull(importService);
        this.onFinish = onFinish;
    }

    @Override
    public void importData(String filePath) {
        var asyncWorker = new SwingWorker<Void, Void>() {
            boolean importSuccessful = false;

            @Override
            protected Void doInBackground() {
                importSuccessful = importService.importData(filePath);
                return null;
            }

            @Override
            protected void done() {
                if (!importSuccessful) {
                    JOptionPane.showMessageDialog(null, "Import failed, the input file is corrupted!");
                    return;
                }
                onFinish.run();
            }
        };
        asyncWorker.execute();
    }

    @Override
    public void setStrategy(ImportStrategy strategy) {
        this.strategy = strategy;
        importService.setStrategy(strategy);
    }

    @Override
    public Collection<Format> getFormats() {
        return importService.getFormats();
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
