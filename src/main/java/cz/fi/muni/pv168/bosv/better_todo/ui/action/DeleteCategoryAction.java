package cz.fi.muni.pv168.bosv.better_todo.ui.action;

import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public class DeleteCategoryAction extends AbstractAction {
    private final JTable categoryTable;

    public DeleteCategoryAction(JTable categoryTable) {
        super("Delete", Icons.DELETE_ICON);
        this.categoryTable = categoryTable;
        putValue(SHORT_DESCRIPTION, "Deletes selected category");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if there is no existing model of such category
        var categoryTableModel = (TodoTableModel) categoryTable.getModel();
        Arrays.stream(categoryTable.getSelectedRows())
                // view row index must be converted to model row index
                .map(categoryTable::convertRowIndexToModel)
                .boxed()
                // We need to delete rows in descending order to not change index of rows
                // which are not deleted yet
                .sorted(Comparator.reverseOrder());
    }
}
