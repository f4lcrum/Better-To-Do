package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.async.EditActionSwingWorker;
import cz.fi.muni.pv168.todo.ui.dialog.TimeUnitDialog;
import cz.fi.muni.pv168.todo.ui.main.MainWindowTimeUnit;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class EditTimeUnitAction extends AbstractAction {

    private final JTable timeUnitTable;
    private final Validator<TimeUnit> timeUnitValidator;
    private final MainWindowTimeUnit mainWindowTimeUnit;
    private final Runnable refresh;

    public EditTimeUnitAction(JTable timeUnitTable, DependencyProvider dependencyProvider, MainWindowTimeUnit mainWindowTimeUnit, Runnable refresh) {
        super("Edit time unit", Icons.EDIT_ICON);
        this.timeUnitTable = timeUnitTable;
        this.timeUnitValidator = Objects.requireNonNull(dependencyProvider.getTimeUnitValidator());
        this.mainWindowTimeUnit = mainWindowTimeUnit;
        this.refresh = refresh;
        putValue(SHORT_DESCRIPTION, "Edits selected event");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        putValue(Action.SMALL_ICON, Icons.EDIT_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = timeUnitTable.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (timeUnitTable.isEditing()) {
            timeUnitTable.getCellEditor().cancelCellEditing();
        }
        var timeUnitTableModel = mainWindowTimeUnit.getTableModel();
        int modelRow = timeUnitTable.convertRowIndexToModel(selectedRows[0]);
        var timeUnit = timeUnitTableModel.getEntity(modelRow);
        if (timeUnit.isDefault()) {
            return;
        }
        var dialog = new TimeUnitDialog(timeUnit, true, timeUnitValidator);
        dialog.show(timeUnitTable, "Edit Time Unit").ifPresent(entity -> new EditActionSwingWorker<>(timeUnitTableModel, refresh, entity).execute());
    }
}
