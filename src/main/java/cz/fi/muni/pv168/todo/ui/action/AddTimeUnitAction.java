package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.main.MainWindow;
import cz.fi.muni.pv168.todo.ui.dialog.TimeUnitDialog;
import cz.fi.muni.pv168.todo.ui.main.MainWindowTimeUnit;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import java.util.Objects;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.UUID;

public class AddTimeUnitAction extends AbstractAction {

    private final JTable timeUnitTable;
    private final Validator<TimeUnit> timeUnitValidator;
    private final MainWindowTimeUnit mainWindowTimeUnit;

    public AddTimeUnitAction(JTable timeUnitTable, DependencyProvider dependencyProvider, MainWindowTimeUnit mainWindowTimeUnit) {
        super("Add time unit", Icons.ADD_ICON);
        this.timeUnitTable = timeUnitTable;
        this.timeUnitValidator = Objects.requireNonNull(dependencyProvider.getTimeUnitValidator());
        this.mainWindowTimeUnit = mainWindowTimeUnit;
        putValue(SHORT_DESCRIPTION, "Adds new time unit");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        putValue(Action.SMALL_ICON, Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var timeUnitTableModel = mainWindowTimeUnit.getTableModel();
        var dialog = new TimeUnitDialog(createPrefilledTimeUnit(), false, timeUnitValidator);
        dialog.show(timeUnitTable, "Add Time Unit")
                .ifPresent(timeUnitTableModel::addRow);
        mainWindowTimeUnit.refreshModel();
    }

    private TimeUnit createPrefilledTimeUnit() {
        return new TimeUnit(
                UUID.randomUUID(),
                false,
                "Sprint",
                14 * 24, // 14 days,
                0
        );
    }
}
