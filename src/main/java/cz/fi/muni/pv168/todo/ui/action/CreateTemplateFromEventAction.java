package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.dialog.TemplateDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.UUID;

public class CreateTemplateFromEventAction extends AbstractAction {
    private final JTable todoTable;
    private final JTable templateTable;
    private final MainWindow mainWindow;
    private final ListModel<Category> categoryListModel;
    private final ListModel<TimeUnit> timeUnitListModel;
    private final Validator<Template> templateValidator;

    public CreateTemplateFromEventAction(JTable todoTable, JTable templateTable, MainWindow mainWindow, ListModel<Category> categoryListModel, ListModel<TimeUnit> timeUnitListModel) {
        super("Save as template", Icons.ADD_ICON);
        this.todoTable = todoTable;
        this.templateTable = templateTable;
        this.mainWindow = mainWindow;
        this.categoryListModel = categoryListModel;
        this.timeUnitListModel = timeUnitListModel;
        this.templateValidator = Objects.requireNonNull(mainWindow.getTemplateValidator());
        putValue(SHORT_DESCRIPTION, "Save as template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl T"));
        putValue(Action.SMALL_ICON, Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = todoTable.getSelectedRows();

        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }

        var eventTableModel = mainWindow.getEventTableModel();
        var templateTableModel = mainWindow.getTemplateTableModel();
        int modelRow = todoTable.convertRowIndexToModel(selectedRows[0]);
        var event = eventTableModel.getEntity(modelRow);

        var dialog = new TemplateDialog(mainWindow.getCategoryCrudService(), createPrefilledTemplate(event), categoryListModel, timeUnitListModel, true, templateValidator);
        dialog.show(templateTable, "Save as template")
                .ifPresent(templateTableModel::addRow);
        mainWindow.refreshTemplateListModel();
    }

    private Template createPrefilledTemplate(Event event) {

        return new Template(
                UUID.randomUUID(),
                event.getName(),
                event.getName(),
                event.getCategory(),
                event.getStartTime(),
                event.getTimeUnit(),
                event.getDuration(),
                event.getDescription()
        );
    }

}
