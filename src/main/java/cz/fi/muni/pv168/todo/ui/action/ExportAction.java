package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.io.exports.JsonExporter;
import cz.fi.muni.pv168.todo.storage.sql.snapshot.DatabaseSnapshot;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Objects;

public final class ExportAction extends AbstractAction {

    private final Component parent;

    private final DependencyProvider dependencyProvider;

    public ExportAction(Component parent, DependencyProvider dp) {
        super("Export", Icons.EXPORT_ICON);
        this.parent = Objects.requireNonNull(parent);
        this.dependencyProvider = dp;

        putValue(SHORT_DESCRIPTION, "Exports events to a file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_X);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        var eventRepository = dependencyProvider.getEventRepository();
        var categoryRepository = dependencyProvider.getCategoryRepository();
        var templateRepository = dependencyProvider.getTemplateRepository();
        var timeUnitRepository = dependencyProvider.getTimeUnitRepository();

        var jsonExporter = new JsonExporter();

        int dialogResult = fileChooser.showSaveDialog(parent);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String exportFile = fileChooser.getSelectedFile().getAbsolutePath();

            SwingWorker<Void, Void> exportDbAsync = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    DatabaseSnapshot databaseSnapshot = new DatabaseSnapshot(
                            categoryRepository.findAll(),
                            eventRepository.findAll(),
                            templateRepository.findAll(),
                            timeUnitRepository.findAll()
                    );

                    jsonExporter.exportDatabase(exportFile, databaseSnapshot);
                    return null;
                }

                @Override
                protected void done() {
                    JOptionPane.showMessageDialog(parent, "Export has successfully finished.");
                }
            };

            exportDbAsync.execute();
        }
    }
}
