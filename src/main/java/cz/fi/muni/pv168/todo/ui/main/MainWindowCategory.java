package cz.fi.muni.pv168.todo.ui.main;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.ui.action.strategy.CategoryButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.todo.ui.model.Column;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.panels.CategoryTablePanel;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import java.awt.Color;
import java.util.List;
import java.util.function.BiConsumer;

public class MainWindowCategory extends MainWindowEntityImpl<Category> {

    public MainWindowCategory(DependencyProvider dependencyProvider, BiConsumer<Integer, Boolean> onSelectionChange, Runnable refresh) {
        this.tableModel = new TableModel<>(dependencyProvider.getCategoryCrudService(), List.of(
                new Column<>(" ", Color.class, Category::getColor),
                new Column<>("Name", String.class, Category::getName)
        ));
        this.listModel = new CategoryListModel(dependencyProvider.getCategoryCrudService());
        this.tablePanel = new CategoryTablePanel(tableModel, onSelectionChange);
        this.buttonTabStrategy = new CategoryButtonTabStrategy(tablePanel.getTable(), dependencyProvider, this, refresh);
        tablePanel.getTable().setComponentPopupMenu(MainWindowHelper.createPopupMenu((buttonTabStrategy)));
    }
}
