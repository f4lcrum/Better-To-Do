package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.async.AddActionSwingWorker;
import cz.fi.muni.pv168.todo.ui.dialog.TemplateDialog;
import cz.fi.muni.pv168.todo.ui.main.MainWindowTemplate;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public final class AddTemplateAction extends AbstractAction {

    private final JTable templateTable;
    private final MainWindowTemplate mainWindowTemplate;
    private final ListModel<Category> categoryListModel;
    private final ListModel<TimeUnit> timeUnitListModel;
    private final Validator<Template> templateValidator;
    private final CategoryCrudService categoryCrudService;
    private final Runnable refresh;

    public AddTemplateAction(JTable templateTable, MainWindowTemplate mainWindowTemplate, DependencyProvider dependencyProvider,
                             ListModel<Category> categoryListModel, ListModel<TimeUnit> timeUnitListModel, Runnable refresh) {
        super("Add template", Icons.ADD_ICON);
        this.templateTable = templateTable;
        this.mainWindowTemplate = mainWindowTemplate;
        this.categoryCrudService = dependencyProvider.getCategoryCrudService();
        this.categoryListModel = categoryListModel;
        this.timeUnitListModel = timeUnitListModel;
        this.templateValidator = Objects.requireNonNull(dependencyProvider.getTemplateValidator());
        this.refresh = refresh;
        putValue(SHORT_DESCRIPTION, "Adds new template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        putValue(Action.SMALL_ICON, Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var templateTableModel = mainWindowTemplate.getTableModel();
        var dialog = new TemplateDialog(categoryCrudService, createPrefilledTemplate(), categoryListModel, timeUnitListModel, false, templateValidator);
        dialog.show(templateTable, "Add Template").ifPresent(entity -> new AddActionSwingWorker<>(templateTableModel, refresh, entity).execute());
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
