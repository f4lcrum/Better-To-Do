package cz.fi.muni.pv168.todo.ui.action;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImportFileChooser {

    public static ImportDialogResult showImportDialog(Component parent) {
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
            result.setOverwrite(true);
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
            result.setOverwrite(false);
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
        private boolean overwrite;

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public boolean isOverwrite() {
            return overwrite;
        }

        public void setOverwrite(boolean overwrite) {
            this.overwrite = overwrite;
        }
    }
}
