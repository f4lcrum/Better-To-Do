package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.io.imports.JsonImporter;
import cz.fi.muni.pv168.todo.ui.action.strategy.AppendImportStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.ImportStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.OverwriteImportStrategy;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImportFileChooser {

    public static ImportDialogResult showImportDialog(Component parent, DependencyProvider dependencyProvider) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setControlButtonsAreShown(false);

        JDialog dialog = new JDialog();
        dialog.setTitle("Open");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.add(fileChooser, BorderLayout.CENTER);

        ImportDialogResult result = new ImportDialogResult();

        JPanel buttonPanel = new JPanel();
        JButton overwriteButton = new JButton("Import and Overwrite");
        overwriteButton.addActionListener(e -> {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile == null || !selectedFile.exists()) {
                JOptionPane.showMessageDialog(parent, "No file selected or the selected file does not exist!");
                return;
            }
            result.setFile(selectedFile);
            result.setImportStrategy(new OverwriteImportStrategy(
                    dependencyProvider.getEventCrudService(),
                    dependencyProvider.getCategoryCrudService(),
                    dependencyProvider.getTemplateCrudService(),
                    dependencyProvider.getTimeUnitCrudService(),
                    List.of(new JsonImporter()))
            );
            dialog.dispose();
        });

        JButton appendButton = new JButton("Import and Append");
        appendButton.addActionListener(e -> {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile == null || !selectedFile.exists()) {
                JOptionPane.showMessageDialog(parent, "No file selected or the selected file does not exist!");
                return;
            }
            result.setFile(selectedFile);
            result.setImportStrategy(new AppendImportStrategy(
                    dependencyProvider.getEventCrudService(),
                    dependencyProvider.getCategoryCrudService(),
                    dependencyProvider.getTemplateCrudService(),
                    dependencyProvider.getTimeUnitCrudService(),
                    List.of(new JsonImporter()))
            );
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(overwriteButton);
        buttonPanel.add(appendButton);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return result;
    }

    public static class ImportDialogResult {
        private File file;
        private ImportStrategy strategy;

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public ImportStrategy importStrategy() {
            return strategy;
        }

        public void setImportStrategy(ImportStrategy strategy) {
            this.strategy = strategy;
        }
    }
}
