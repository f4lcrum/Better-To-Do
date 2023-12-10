package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public class DeleteCategoryAction extends AbstractAction {

    private final JTable categoryTable;
    private final MainWindow mainWindow;
    public DeleteCategoryAction(JTable categoryTable, MainWindow mainWindow) {
        super("Delete category", Icons.DELETE_ICON);
        this.categoryTable = categoryTable;
        this.mainWindow = mainWindow;
        putValue(SHORT_DESCRIPTION, "Deletes selected category");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        putValue(Action.SMALL_ICON, Icons.DELETE_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var categoryTableModel = mainWindow.getCategoryTableModel();
        // Check if there is no existing model of such category
        Arrays.stream(categoryTable.getSelectedRows())
                // view row index must be converted to model row index
                .map(categoryTable::convertRowIndexToModel)
                .boxed()
                // We need to delete rows in descending order to not change index of rows
                // which are not deleted yet
                .sorted(Comparator.reverseOrder())
                .forEach(categoryTableModel::deleteRow);
    }
}
