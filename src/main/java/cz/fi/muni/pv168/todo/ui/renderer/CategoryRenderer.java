package cz.fi.muni.pv168.todo.ui.renderer;

import cz.fi.muni.pv168.todo.entity.Category;

import javax.swing.JLabel;

public class CategoryRenderer extends AbstractRenderer<Category> {

    public CategoryRenderer() {
        super(Category.class);
    }

    @Override
    protected void updateLabel(JLabel label, Category value) {
        label.setText(value.getName());
    }
}
