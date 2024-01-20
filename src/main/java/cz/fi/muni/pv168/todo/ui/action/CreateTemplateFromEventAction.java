package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.dialog.EventDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.UUID;

public class CreateTemplateFromEventAction extends AbstractAction {
    private final JTable eventTable;
    private final JTable templateTable;
    private final MainWindow mainWindow;
    private final Validator<Template> templateValidator;
    private final CrudService<Template> service;
    public EventDialog dialog;

    public CreateTemplateFromEventAction(JTable eventTable, JTable templateTable, MainWindow mainWindow, ListModel<Category> categoryListModel, ListModel<TimeUnit> timeUnitListModel, CrudService<Template> service) {
        super("Save as template", Icons.ADD_ICON);
        this.eventTable = eventTable;
        this.templateTable = templateTable;
        this.mainWindow = mainWindow;
        this.templateValidator = Objects.requireNonNull(mainWindow.getTemplateValidator());
        this.service = service;
        putValue(SHORT_DESCRIPTION, "Save as template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl T"));
        putValue(Action.SMALL_ICON, Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var event = dialog.validateAndGetEvent(eventTable, "Add event");
        if (event != null) {
            var template = createPrefilledTemplate(event);
            service.create(template);
            mainWindow.refreshTemplateListModel();
            JOptionPane.showConfirmDialog(eventTable, "Template has been successfully created.", "Confirm dialog", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        }
    }

    public void setDialog(EventDialog dialog) {
        this.dialog = dialog;
    }

    private Template createPrefilledTemplate(Event event) {

        return new Template(
                UUID.randomUUID(),
                String.format("%s template", event.getName()),
                event.getName(),
                event.getCategory(),
                event.getStartTime(),
                event.getTimeUnit(),
                event.getDuration(),
                event.getDescription()
        );
    }
}
