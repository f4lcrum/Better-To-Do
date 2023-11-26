package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Category;

import javax.swing.AbstractListModel;
import java.util.ArrayList;
import java.util.List;

public class CategoryListModel extends AbstractListModel<Category> {

    private final List<Category> categories;

    public CategoryListModel() {
        this.categories = new ArrayList<>();
    }

    public CategoryListModel(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public int getSize() {
        return categories.size();
    }

    @Override
    public Category getElementAt(int index) {
        return categories.get(index);
    }
}
