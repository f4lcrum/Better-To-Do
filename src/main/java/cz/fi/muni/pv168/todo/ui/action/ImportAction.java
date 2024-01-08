package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.io.imports.JsonImporter;
import cz.fi.muni.pv168.todo.storage.sql.snapshot.DatabaseSnapshot;
import cz.fi.muni.pv168.todo.ui.panels.CategoryTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.TemplateTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.TimeUnitTablePanel;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Objects;

public final class ImportAction extends AbstractAction {

    private final EventTablePanel eventTablePanel;
    private final CategoryTablePanel categoryTablePanel;
    private final TemplateTablePanel templateTablePanel;
    private final TimeUnitTablePanel timeUnitTablePanel;
    private final DependencyProvider dependencyProvider;

    public ImportAction(
            EventTablePanel eventTablePanel,
            CategoryTablePanel categoryTablePanel,
            TemplateTablePanel templateTablePanel,
            TimeUnitTablePanel timeUnitTablePanel,
            DependencyProvider dependencyProvider
    ) {
        super("Import", Icons.IMPORT_ICON);
        this.eventTablePanel = Objects.requireNonNull(eventTablePanel);
        this.categoryTablePanel = Objects.requireNonNull(categoryTablePanel);
        this.templateTablePanel = Objects.requireNonNull(templateTablePanel);
        this.timeUnitTablePanel = Objects.requireNonNull(timeUnitTablePanel);
        this.dependencyProvider = Objects.requireNonNull(dependencyProvider);

        putValue(SHORT_DESCRIPTION, "Imports events from a file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int dialogResult = fileChooser.showOpenDialog(eventTablePanel);

        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String importFilePath = fileChooser.getSelectedFile().getAbsolutePath();

            JsonImporter jsonImporter = new JsonImporter();

            DatabaseSnapshot databaseSnapshot = jsonImporter.importDatabase(importFilePath);

            var categoryRepository = dependencyProvider.getCategoryRepository();
            var eventRepository = dependencyProvider.getEventRepository();
            var templateRepository = dependencyProvider.getTemplateRepository();
            var timeUnitRepository = dependencyProvider.getTimeUnitRepository();

            SwingWorker<Void, Void> updateDbAsync = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    eventRepository.deleteAll();
                    categoryRepository.deleteAll();
                    templateRepository.deleteAll();
                    timeUnitRepository.deleteAll();

                    databaseSnapshot.timeUnits().forEach(timeUnitRepository::create);
                    databaseSnapshot.categories().forEach(categoryRepository::create);
                    databaseSnapshot.events().forEach(eventRepository::create);
                    databaseSnapshot.templates().forEach(templateRepository::create);

                    eventTablePanel.refresh();
                    categoryTablePanel.refresh();
                    templateTablePanel.refresh();
                    timeUnitTablePanel.refresh();
                    return null;
                }

                @Override
                protected void done() {
                    JOptionPane.showMessageDialog(eventTablePanel, "Import was done");
                }
            };

            updateDbAsync.execute();
        }
    }
}
