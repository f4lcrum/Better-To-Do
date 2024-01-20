package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.ui.async.DeleteActionSwingWorker;
import cz.fi.muni.pv168.todo.ui.main.MainWindowTemplate;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public class DeleteTemplateAction extends AbstractAction {

    private final JTable templateTable;
    private final MainWindowTemplate mainWindowTemplate;
    private final Runnable refresh;

    public DeleteTemplateAction(JTable templateTable, MainWindowTemplate mainWindowTemplate, Runnable refresh) {
        super("Delete template", Icons.DELETE_ICON);
        this.templateTable = templateTable;
        this.mainWindowTemplate = mainWindowTemplate;
        this.refresh = refresh;
        putValue(SHORT_DESCRIPTION, "Deletes selected template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        putValue(Action.SMALL_ICON, Icons.DELETE_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var templateTableModel = mainWindowTemplate.getTableModel();
        var stream = Arrays.stream(templateTable.getSelectedRows())
                .map(templateTable::convertRowIndexToModel)
                .boxed()
                .sorted(Comparator.reverseOrder());
        new DeleteActionSwingWorker<>(templateTableModel, refresh, stream).execute();
    }
}
