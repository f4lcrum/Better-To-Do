package cz.fi.muni.pv168.todo.ui.main;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.action.strategy.TemplateButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.model.Column;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.model.TemplateListModel;
import cz.fi.muni.pv168.todo.ui.panels.TemplateTablePanel;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.ListModel;
import java.awt.Color;
import java.util.List;
import java.util.function.BiConsumer;

public class MainWindowTemplate extends MainWindowEntityImpl<Template> {

    public MainWindowTemplate(DependencyProvider dependencyProvider, BiConsumer<Integer, Boolean> onSelectionChange,
                              ListModel<Category> categoryListModel, ListModel<TimeUnit> timeUnitListModel, Runnable refresh) {
        this.tableModel = new TableModel<>(dependencyProvider.getTemplateCrudService(), List.of(
                new Column<>(" ", Color.class, Template::getColour),
                new Column<>("Template name", String.class, Template::getName),
                new Column<>("Category", Category.class, Template::getCategory),
                new Column<>("Duration", String.class, Template::getDurationString)
        ));
        this.listModel = new TemplateListModel(dependencyProvider.getTemplateCrudService());
        this.tablePanel = new TemplateTablePanel(tableModel, onSelectionChange);
        this.buttonTabStrategy = new TemplateButtonTabStrategy(tablePanel.getTable(), dependencyProvider, categoryListModel, timeUnitListModel, this, refresh);
        tablePanel.getTable().setComponentPopupMenu(MainWindowHelper.createPopupMenu((buttonTabStrategy)));
    }
}
