package cz.fi.muni.pv168.todo.ui;

import cz.fi.muni.pv168.todo.Main;
import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.action.strategy.CategoryButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.filter.components.FilterPanel;
import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterCategoryValues;
import cz.fi.muni.pv168.todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.todo.ui.model.Column;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.panels.CategoryTablePanel;
import cz.fi.muni.pv168.todo.util.Either;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.Color;
import java.util.List;
import java.util.function.BiConsumer;

public class MainWindowCategory {
    private final CategoryListModel categoryListModel;
    private final TableModel<Category> categoryTableModel;
    private final Validator<Category> categoryValidator;
    private final CategoryCrudService categoryCrudService;
    private final MainWindow mainWindow;
    private final CategoryButtonTabStrategy categoryButtonTabStrategy;
    private final CategoryTablePanel categoryTablePanel;
    private final JList<Either<SpecialFilterCategoryValues, Category>> categoryFilterList;
    private final JPanel categoryFilterPanel;

    public MainWindowCategory(DependencyProvider dependencyProvider, BiConsumer<Integer, Boolean> onSelectionChange, MainWindow mainWindow) {
        this.categoryCrudService = dependencyProvider.getCategoryCrudService();
        this.categoryTableModel = new TableModel<>(dependencyProvider.getCategoryCrudService(), List.of(
                new Column<>(" ", Color.class, Category::getColor),
                new Column<>("Name", String.class, Category::getName)
        ));
        this.categoryListModel = new CategoryListModel(dependencyProvider.getCategoryCrudService());
        this.categoryValidator = dependencyProvider.getCategoryValidator();
        this.categoryTablePanel = new CategoryTablePanel(this.categoryTableModel, onSelectionChange);
        this.mainWindow = mainWindow;
        this.categoryFilterList = FilterPanel.createCategoryFilter(mainWindow.getEventTableFilter(), this.categoryListModel);
        this.categoryFilterPanel = FilterPanel.createFilterPanel(this.categoryFilterList, "Category: ");
        this.categoryButtonTabStrategy = new CategoryButtonTabStrategy(categoryTablePanel.getTable(), this.mainWindow, this);
    }

    public void refreshModel() {
        categoryListModel.refresh();
        categoryTableModel.refresh();
    }

    public CategoryCrudService getCategoryCrudService() {
        return this.categoryCrudService;
    }
    public Validator<Category> getCategoryValidator() {
        return this.categoryValidator;
    }

    public TableModel<Category> getCategoryTableModel() {
        return this.categoryTableModel;
    }

    public CategoryListModel getCategoryListModel() {
        return this.categoryListModel;
    }

    public CategoryTablePanel getCategoryTablePanel() { return this.categoryTablePanel; }

    public JList<Either<SpecialFilterCategoryValues, Category>> getCategoryFilterList() {
        return this.categoryFilterList;
    }
    public JPanel getCategoryFilterPanel() {
        return this.categoryFilterPanel;
    }
    public CategoryButtonTabStrategy getCategoryButtonTabStrategy() {
        return this.categoryButtonTabStrategy;
    }

}
