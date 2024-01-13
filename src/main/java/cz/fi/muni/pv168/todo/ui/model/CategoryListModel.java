package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;

import javax.swing.AbstractListModel;
import java.util.ArrayList;
import java.util.List;

public class CategoryListModel extends AbstractListModel<Category> {

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

    public void refresh() {
        this.categories = new ArrayList<>(categoryCrudService.findAll());
        fireContentsChanged(this, 0, getSize() - 1);
    }

}
