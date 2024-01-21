package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import cz.fi.muni.pv168.todo.ui.dialog.EventDialog;
import cz.fi.muni.pv168.todo.ui.main.MainWindowTemplate;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.UUID;

public class CreateTemplateFromEventAction extends AbstractAction {
    private final JTable eventTable;
    private final MainWindowTemplate mainWindowTemplate;
    private final CrudService<Template> service;
    public EventDialog dialog;

    public CreateTemplateFromEventAction(JTable eventTable, DependencyProvider dependencyProvider, MainWindowTemplate mainWindowTemplate) {
        super("Save as template", Icons.ADD_ICON);
        this.eventTable = eventTable;
        this.mainWindowTemplate = mainWindowTemplate;
        this.service = dependencyProvider.getTemplateCrudService();
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
            mainWindowTemplate.refreshModel();
            JOptionPane.showConfirmDialog(eventTable, "Template has been successfully created.", "Confirm dialog", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        }
    }

    public void setDialog(EventDialog dialog) {
        this.dialog = dialog;
    }

    private Template createPrefilledTemplate(Event event) {

        return new Template(
                UUID.randomUUID(),
                event.getName(),
                String.format("%s template", event.getName()),
                event.getCategory(),
                event.getStartTime(),
                event.getTimeUnit(),
                event.getDuration(),
                event.getDescription()
        );
    }
}