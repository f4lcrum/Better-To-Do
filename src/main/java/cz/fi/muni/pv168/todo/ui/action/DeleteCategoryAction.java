package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.ui.async.DeleteActionSwingWorker;
import cz.fi.muni.pv168.todo.ui.main.MainWindowCategory;
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
    private final MainWindowCategory mainWindowCategory;
    private final Runnable refresh;

    public DeleteCategoryAction(JTable categoryTable, MainWindowCategory mainWindowCategory, Runnable refresh) {
        super("Delete category", Icons.DELETE_ICON);
        this.categoryTable = categoryTable;
        this.mainWindowCategory = mainWindowCategory;
        this.refresh = refresh;
        putValue(SHORT_DESCRIPTION, "Deletes selected category");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        putValue(Action.SMALL_ICON, Icons.DELETE_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var categoryTableModel = mainWindowCategory.getTableModel();
        var stream = Arrays.stream(categoryTable.getSelectedRows())
                .map(categoryTable::convertRowIndexToModel)
                .boxed()
                .sorted(Comparator.reverseOrder());
        new DeleteActionSwingWorker<>(categoryTableModel, refresh, stream).execute();
    }
}
