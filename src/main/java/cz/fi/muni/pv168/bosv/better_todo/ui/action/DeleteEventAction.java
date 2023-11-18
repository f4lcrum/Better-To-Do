package cz.fi.muni.pv168.bosv.better_todo.ui.action;

import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public class DeleteEventAction extends AbstractAction {
    private final JTable todoTable;

    public DeleteEventAction(JTable todoTable) {
        super("Delete event", Icons.DELETE_ICON);
        this.todoTable = todoTable;
        putValue(SHORT_DESCRIPTION, "Deletes selected event");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        putValue(Action.SMALL_ICON, Icons.DELETE_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var employeeTableModel = (TodoTableModel) todoTable.getModel();
        Arrays.stream(todoTable.getSelectedRows())
                // view row index must be converted to model row index
                .map(todoTable::convertRowIndexToModel)
                .boxed()
                // We need to delete rows in descending order to not change index of rows
                // which are not deleted yet
                .sorted(Comparator.reverseOrder());
    }
}
