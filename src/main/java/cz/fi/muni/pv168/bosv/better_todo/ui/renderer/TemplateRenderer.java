package cz.fi.muni.pv168.bosv.better_todo.ui.renderer;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Template;
import lombok.NonNull;

import javax.swing.*;

public class TemplateRenderer extends AbstractRenderer<Template> {
    public TemplateRenderer() {
        super(Template.class);
    }

    @Override
    protected void updateLabel(@NonNull JLabel label, @NonNull Template value) {
        label.setText(value.getName());
    }
}
