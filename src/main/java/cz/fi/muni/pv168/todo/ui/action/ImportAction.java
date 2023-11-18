package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Objects;

public class ImportAction extends AbstractAction {
    private final EventTablePanel eventTablePanel;

    public ImportAction(EventTablePanel eventTablePanel) {
        super("Import", Icons.IMPORT_ICON);
        this.eventTablePanel = Objects.requireNonNull(eventTablePanel);

        putValue(SHORT_DESCRIPTION, "Imports employees from a file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));


        int dialogResult = fileChooser.showOpenDialog(eventTablePanel);

        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            File importFile = fileChooser.getSelectedFile();

            JOptionPane.showMessageDialog(eventTablePanel, "Import was done");
        }
    }
}
