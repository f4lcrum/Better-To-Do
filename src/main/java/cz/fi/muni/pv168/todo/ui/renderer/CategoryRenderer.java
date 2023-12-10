package cz.fi.muni.pv168.todo.ui.renderer;

import cz.fi.muni.pv168.todo.business.entity.Category;

import javax.swing.JLabel;

public class CategoryRenderer extends AbstractRenderer<Category> {

    public CategoryRenderer() {
        super(Category.class);
    }

    @Override
    protected void updateLabel(JLabel label, Category value) {
        if (value == null) {
            label.setText("");
            return;
        }
        label.setText(value.getName());
    }
}
