package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.service.export.ExportService;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.ui.workers.AsyncExporter;
import cz.fi.muni.pv168.todo.util.Filter;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Objects;

public final class ExportAction extends AbstractAction {

    private final Component parent;
    private final Exporter exporter;

    public ExportAction(Component parent, ExportService exportService) {
        super("Export", Icons.EXPORT_ICON);
        this.parent = Objects.requireNonNull(parent);
        this.exporter = new AsyncExporter(
                exportService,
                () -> JOptionPane.showMessageDialog(parent, "Export has successfully finished."));

        putValue(SHORT_DESCRIPTION, "Exports events to a file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_X);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        exporter.getFormats().forEach(f -> fileChooser.addChoosableFileFilter(new Filter(f)));

        int dialogResult = fileChooser.showSaveDialog(parent);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String exportFile = fileChooser.getSelectedFile().getAbsolutePath();
            var fileFilter = fileChooser.getFileFilter();
            if (fileFilter instanceof Filter filter) {
                exportFile = filter.decorate(exportFile);
            }

            exporter.exportData(exportFile);
        }
    }
}
