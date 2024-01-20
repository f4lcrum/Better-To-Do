package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;

import java.util.ArrayList;
import java.util.List;

public class CategoryListModel extends RefreshableListModel<Category> {

    private List<Category> categories;
    private final CategoryCrudService categoryCrudService;

    public CategoryListModel(CategoryCrudService categoryCrudService) {
        this.categoryCrudService = categoryCrudService;
        this.categories = new ArrayList<>(categoryCrudService.findAllWithDefault());
    }

    @Override
    public int getSize() {
        return categories.size();
    }

    @Override
    public Category getElementAt(int index) {
        return categories.get(index);
    }

    @Override
    public void refresh() {
        this.categories = new ArrayList<>(categoryCrudService.findAll());
        fireContentsChanged(this, 0, getSize() - 1);
    }
}
