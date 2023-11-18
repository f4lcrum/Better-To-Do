package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Objects;

public class ExportAction extends AbstractAction {

    private final Component parent;

    public ExportAction(Component parent) {
        super("Export", Icons.EXPORT_ICON);
        this.parent = Objects.requireNonNull(parent);

        putValue(SHORT_DESCRIPTION, "Exports employees to a file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_X);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int dialogResult = fileChooser.showSaveDialog(parent);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String exportFile = fileChooser.getSelectedFile().getAbsolutePath();
            var filter = fileChooser.getFileFilter();

            JOptionPane.showMessageDialog(parent, "Export has successfully finished.");
        }
    }
}
