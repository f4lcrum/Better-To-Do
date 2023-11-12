package cz.fi.muni.pv168.todo.ui.renderer;

import cz.fi.muni.pv168.todo.business.entity.Template;

import javax.swing.JLabel;

public class TemplateRenderer extends AbstractRenderer<Template> {
    public TemplateRenderer() {
        super(Template.class);
    }

    @Override
    protected void updateLabel(JLabel label, Template value) {
        label.setText(value.getName());
    }
}
