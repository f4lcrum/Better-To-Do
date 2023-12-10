package cz.fi.muni.pv168.todo.ui.listener;

import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.ui.dialog.EventDialog;

import javax.swing.JComboBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TemplateComboBoxItemListnener implements ItemListener {

    private final JComboBox<Template> templateJComboBox;
    private final EventDialog eventDialog;

    public TemplateComboBoxItemListnener(JComboBox<Template> templateJComboBox, EventDialog eventDialog) {
        super();
        this.templateJComboBox = templateJComboBox;
        this.eventDialog = eventDialog;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        eventDialog.SetTemplate(templateJComboBox.getItemAt(templateJComboBox.getSelectedIndex()));
    }
}
