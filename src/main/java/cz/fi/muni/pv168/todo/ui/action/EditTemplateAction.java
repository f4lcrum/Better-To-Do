package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.async.EditActionSwingWorker;
import cz.fi.muni.pv168.todo.ui.dialog.TemplateDialog;
import cz.fi.muni.pv168.todo.ui.main.MainWindowTemplate;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class EditTemplateAction extends AbstractAction {

    private final JTable templateTable;
    private final ListModel<Category> categoryListModel;
    private final ListModel<TimeUnit> timeUnitListModel;
    private final Validator<Template> templateValidator;
    private final CategoryCrudService categoryCrudService;
    private final MainWindowTemplate mainWindowTemplate;
    private final Runnable refresh;

    public EditTemplateAction(JTable templateTable, DependencyProvider dependencyProvider, MainWindowTemplate mainWindowTemplate,
                              ListModel<Category> categoryListModel, ListModel<TimeUnit> timeUnitListModel, Runnable refresh) {
        super("Edit template", Icons.EDIT_ICON);
        this.templateTable = templateTable;
        this.categoryListModel = categoryListModel;
        this.timeUnitListModel = timeUnitListModel;
        this.templateValidator = Objects.requireNonNull(dependencyProvider.getTemplateValidator());
        this.categoryCrudService = dependencyProvider.getCategoryCrudService();
        this.mainWindowTemplate = mainWindowTemplate;
        this.refresh = refresh;
        putValue(SHORT_DESCRIPTION, "Edits selected template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        putValue(Action.SMALL_ICON, Icons.EDIT_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = templateTable.getSelectedRows();

        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (templateTable.isEditing()) {
            templateTable.getCellEditor().cancelCellEditing();
        }

        var templateTableModel = mainWindowTemplate.getTableModel();
        int modelRow = templateTable.convertRowIndexToModel(selectedRows[0]);
        var template = templateTableModel.getEntity(modelRow);
        var dialog = new TemplateDialog(categoryCrudService, template, categoryListModel, timeUnitListModel, true, templateValidator);
        dialog.show(templateTable, "Edit Template").ifPresent(entity -> new EditActionSwingWorker<>(templateTableModel, refresh, entity).execute());
    }
}
