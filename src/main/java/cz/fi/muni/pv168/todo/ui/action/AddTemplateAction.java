package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.MainWindowCategory;
import cz.fi.muni.pv168.todo.ui.dialog.TemplateDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import java.util.Objects;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalTime;
import java.util.UUID;

public final class AddTemplateAction extends AbstractAction {

    private final JTable templateTable;
    private final MainWindow mainWindow;
    private final MainWindowCategory mainWindowCategory;
    private final ListModel<Category> categoryListModel;
    private final ListModel<TimeUnit> timeUnitListModel;
    private final Validator<Template> templateValidator;

    public AddTemplateAction(JTable templateTable, MainWindow mainWindow, MainWindowCategory mainWindowCategory, ListModel<Category> categoryListModel,
                             ListModel<TimeUnit> timeUnitListModel) {
        super("Add template", Icons.ADD_ICON);
        this.templateTable = templateTable;
        this.mainWindow = mainWindow;
        this.mainWindowCategory = mainWindowCategory;
        this.categoryListModel = categoryListModel;
        this.timeUnitListModel = timeUnitListModel;
        this.templateValidator = Objects.requireNonNull(mainWindow.getTemplateValidator());
        putValue(SHORT_DESCRIPTION, "Adds new template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        putValue(Action.SMALL_ICON, Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var templateTableModel = mainWindow.getTemplateTableModel();
        var dialog = new TemplateDialog(mainWindowCategory.getCategoryCrudService(), createPrefilledTemplate(), categoryListModel, timeUnitListModel, false, templateValidator);
        dialog.show(templateTable, "Add Template")
                .ifPresent(templateTableModel::addRow);
        mainWindow.refreshTemplateListModel();
    }

    private Template createPrefilledTemplate() {
        return new Template(
                UUID.randomUUID(),
                "Restaurant",
                "Restaurant",
                new Category(UUID.randomUUID(), "Social", Color.BLACK),
                LocalTime.now(),
                new TimeUnit(UUID.randomUUID(), false, "CoffeeBreak", 0, 10),
                5,
                "Short description."
        );
    }
}
