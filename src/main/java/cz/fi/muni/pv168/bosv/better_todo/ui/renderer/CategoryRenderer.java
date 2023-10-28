package cz.fi.muni.pv168.bosv.better_todo.ui.renderer;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import lombok.NonNull;

import javax.swing.*;

public class CategoryRenderer extends AbstractRenderer<Category> {
    public CategoryRenderer() {
        super(Category.class);
    }

    @Override
    protected void updateLabel(@NonNull JLabel label, @NonNull Category value) {
        label.setText(value.getName());
    }
}
