package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.service.export.ImportService;
import cz.fi.muni.pv168.todo.ui.panels.TablePanel;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.ui.workers.AsyncImporter;
import cz.fi.muni.pv168.todo.util.Filter;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Objects;

public final class ImportAction extends AbstractAction {

    private final TablePanel<Event> eventTablePanel;
    private final Importer importer;

    public ImportAction(
            TablePanel<Event> eventTablePanel,
            ImportService importService,
            Runnable callback) {
        super("Import", Icons.IMPORT_ICON);
        this.eventTablePanel = Objects.requireNonNull(eventTablePanel);
        this.importer = new AsyncImporter(
                importService,
                () -> {
                    callback.run();
                    JOptionPane.showMessageDialog(eventTablePanel, "Import was done");
                }
        );

        putValue(SHORT_DESCRIPTION, "Imports employees from a file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        importer.getFormats().forEach(f -> fileChooser.addChoosableFileFilter(new Filter(f)));

        int dialogResult = fileChooser.showOpenDialog(eventTablePanel);

        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            File importFile = fileChooser.getSelectedFile();
            String filePath = importFile.getAbsolutePath();

            if (!importFile.exists()) {
                JOptionPane.showMessageDialog(eventTablePanel, "The selected file does not exist!");
                return;
            }

            if (!importer.acceptsFileFormat(filePath)) {
                JOptionPane.showMessageDialog(
                        eventTablePanel,
                        String.format("This file format is not supported, only %s is!", String.join(", ", importer.getSupportedFileExtensions()))
                );
                return;
            }

            importer.importData(filePath);
        }
    }
}
