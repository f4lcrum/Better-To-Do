package cz.fi.muni.pv168.todo.ui;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Status;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.action.CreateTemplateFromEventAction;
import cz.fi.muni.pv168.todo.ui.action.ExportAction;
import cz.fi.muni.pv168.todo.ui.action.ImportAction;
import cz.fi.muni.pv168.todo.ui.action.QuitAction;
import cz.fi.muni.pv168.todo.ui.action.strategy.ButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.CategoryButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.EventButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.TemplateButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.TimeUnitButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.filter.components.DurationFilterComponents;
import cz.fi.muni.pv168.todo.ui.filter.components.FilterPanel;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EventTableFilter;
import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterCategoryValues;
import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterStatusValues;
import cz.fi.muni.pv168.todo.ui.listener.PanelChangeListener;
import cz.fi.muni.pv168.todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.todo.ui.model.Column;
import cz.fi.muni.pv168.todo.ui.model.EventListModel;
import cz.fi.muni.pv168.todo.ui.model.StatusListModel;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.model.TemplateListModel;
import cz.fi.muni.pv168.todo.ui.model.TimeUnitListModel;
import cz.fi.muni.pv168.todo.ui.panels.CategoryTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.StatisticsPanel;
import cz.fi.muni.pv168.todo.ui.panels.TemplateTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.TimeUnitTablePanel;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.util.Either;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.util.List;

import static cz.fi.muni.pv168.todo.ui.filter.components.FilterPanel.createResetFiltersButton;
import static java.awt.Frame.MAXIMIZED_BOTH;

public class MainWindow {

    private final JFrame frame;
    private ButtonTabStrategy buttonTabStrategy;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final Action quitAction;
    private final Action exportAction;
    private final Action importAction;
    private final Action templateFromEventAction;
    private final EventListModel eventListModel;
    private final TemplateListModel templateListModel;
    private final CategoryListModel categoryListModel;
    private final TimeUnitListModel timeUnitListModel;
    private final JTabbedPane tabbedPane;
    private final JPanel statusFilterPanel;
    private final JPanel categoryFilterPanel;
    private final JPanel durationFilterPanel;
    private final JButton resetFiltersButton;
    private final TableModel<Event> eventTableModel;

    private final TableModel<Category> categoryTableModel;
    private final TableModel<TimeUnit> timeUnitTableModel;
    private final TableModel<Template> templateTableModel;
    private final Validator<Event> eventValidator;
    private final Validator<Category> categoryValidator;
    private final Validator<TimeUnit> timeUnitValidator;
    private final Validator<Template> templateValidator;

    private final StatisticsPanel statistics;

    TableRowSorter<TableModel<Event>> rowSorter;

    public MainWindow(DependencyProvider dependencyProvider) {
        this.eventTableModel = new TableModel<>(dependencyProvider.getEventCrudService(), List.of(
                new Column<>(" ", Color.class, Event::getColour),
                new Column<>("Name of the event", String.class, Event::getName),
                new Column<>("Start date and Time", LocalDateTime.class, Event::calculateStart),
                new Column<>("Category", Category.class, Event::getCategory),
                new Column<>("Status", Status.class, Event::getStatus),
                new Column<>("Duration (minutes)", String.class, Event::getDurationString)
        ));
        EventTablePanel eventTablePanel = new EventTablePanel(eventTableModel, this::changeActionsState);
        this.templateTableModel = new TableModel<>(dependencyProvider.getTemplateCrudService(), List.of(
                new Column<>(" ", Color.class, Template::getColour),
                new Column<>("Template name", String.class, Template::getName),
                new Column<>("Category", Category.class, Template::getCategory),
                new Column<>("Duration", String.class, Template::getDurationString)
        ));
        TemplateTablePanel templateTablePanel = new TemplateTablePanel(templateTableModel, this::changeActionsState);
        this.categoryTableModel = new TableModel<>(dependencyProvider.getCategoryCrudService(), List.of(
                new Column<>(" ", Color.class, Category::getColor),
                new Column<>("Name", String.class, Category::getName)
        ));
        CategoryTablePanel categoryTablePanel = new CategoryTablePanel(categoryTableModel, this::changeActionsState);
        this.timeUnitTableModel = new TableModel<>(dependencyProvider.getTimeUnitCrudService(), List.of(
                new Column<>("Name", String.class, TimeUnit::getName),
                new Column<>("Hour count", Long.class, TimeUnit::getHours),
                new Column<>("Minute count", Long.class, TimeUnit::getMinutes)
        ));
        TimeUnitTablePanel timeUnitTablePanel = new TimeUnitTablePanel(timeUnitTableModel, this::changeActionsState, this::changeTimeUnitActionsState);
        this.categoryListModel = new CategoryListModel(dependencyProvider.getCategoryCrudService());
        this.timeUnitListModel = new TimeUnitListModel(dependencyProvider.getTimeUnitCrudService());
        this.templateListModel = new TemplateListModel(dependencyProvider.getTemplateCrudService());
        this.statistics = new StatisticsPanel(dependencyProvider.getEventCrudService(), this);
        this.eventListModel = new EventListModel(dependencyProvider.getEventCrudService());
        this.eventValidator = dependencyProvider.getEventValidator();
        this.categoryValidator = dependencyProvider.getCategoryValidator();
        this.timeUnitValidator = dependencyProvider.getTimeUnitValidator();
        this.templateValidator = dependencyProvider.getTemplateValidator();
        var statusListModel = new StatusListModel();

        quitAction = new QuitAction();
        exportAction = new ExportAction(eventTablePanel, dependencyProvider);
        importAction = new ImportAction(eventTablePanel, categoryTablePanel, templateTablePanel, timeUnitTablePanel, dependencyProvider);
        templateFromEventAction = new CreateTemplateFromEventAction(eventTablePanel.getTable(), templateTablePanel.getTable(), this, categoryListModel, timeUnitListModel);

        this.rowSorter = new TableRowSorter<>(eventTableModel);
        rowSorter.toggleSortOrder(2); // 2 == 3rd column is start date, automatically sorts 3rd column

        var eventTableFilter = new EventTableFilter(rowSorter, statistics);
        eventTablePanel.getTable().setRowSorter(rowSorter);

        this.tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Events", eventTablePanel);
        tabbedPane.addTab("Templates", templateTablePanel);
        tabbedPane.addTab("Categories", categoryTablePanel);
        tabbedPane.addTab("Units", timeUnitTablePanel);
        tabbedPane.addChangeListener(e -> changeActionsState(0));

        JList<Either<SpecialFilterStatusValues, Status>> statusFilterList = FilterPanel.createStatusFilter(eventTableFilter, statusListModel);
        JList<Either<SpecialFilterCategoryValues, Category>> categoryFilterList = FilterPanel.createCategoryFilter(eventTableFilter, categoryListModel);
        this.statusFilterPanel = FilterPanel.createFilterPanel(statusFilterList, "Status: ");
        this.categoryFilterPanel = FilterPanel.createFilterPanel(categoryFilterList, "Category: ");
        DurationFilterComponents durationComponents = FilterPanel.createDurationFilter(eventTableFilter);
        this.durationFilterPanel = durationComponents.panel;
        this.resetFiltersButton = createResetFiltersButton(eventTableFilter);
        eventTableFilter.setUIComponents(durationComponents.minDurationField,
                durationComponents.maxDurationField,
                statusFilterList,
                categoryFilterList);

        var eventButtonTabStrategy = new EventButtonTabStrategy(eventTablePanel.getTable(), categoryListModel, timeUnitListModel, templateListModel, this);
        var templateButtonTabStrategy = new TemplateButtonTabStrategy(templateTablePanel.getTable(), categoryListModel, timeUnitListModel, this);
        var categoryButtonTabStrategy = new CategoryButtonTabStrategy(categoryTablePanel.getTable(), this);
        var timeUnitButtonTabStrategy = new TimeUnitButtonTabStrategy(timeUnitTablePanel.getTable(), this);
        this.buttonTabStrategy = eventButtonTabStrategy;

        // Apply popup menu bindings
        eventTablePanel.getTable().setComponentPopupMenu(createEventTablePopupMenu(eventButtonTabStrategy));
        categoryTablePanel.getTable().setComponentPopupMenu(createCategoryTablePopupMenu(categoryButtonTabStrategy));
        templateTablePanel.getTable().setComponentPopupMenu(createTemplateTablePopupMenu(templateButtonTabStrategy));
        timeUnitTablePanel.getTable().setComponentPopupMenu(createTimeUnitTablePopupMenu(timeUnitButtonTabStrategy));

        this.addButton = new JButton(Icons.ADD_ICON);
        this.editButton = new JButton(Icons.EDIT_ICON);
        this.deleteButton = new JButton(Icons.DELETE_ICON);

        this.addButton.setAction(this.buttonTabStrategy.getAddAction());
        this.editButton.setAction(this.buttonTabStrategy.getEditAction());
        this.deleteButton.setAction(this.buttonTabStrategy.getDeleteAction());

        var panelChangeListener = new PanelChangeListener(
                this,
                eventButtonTabStrategy,
                templateButtonTabStrategy,
                categoryButtonTabStrategy,
                timeUnitButtonTabStrategy,
                eventTablePanel,
                templateTablePanel,
                categoryTablePanel,
                timeUnitTablePanel);

        frame = createFrame();
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setJMenuBar(createMenuBar(panelChangeListener, eventButtonTabStrategy, templateButtonTabStrategy, categoryButtonTabStrategy, timeUnitButtonTabStrategy));
        frame.add(createToolbar(addButton, editButton, deleteButton, statusFilterPanel, categoryFilterPanel, durationFilterPanel), BorderLayout.BEFORE_FIRST_LINE);
        frame.add(this.statistics, BorderLayout.SOUTH);

        tabbedPane.addChangeListener(panelChangeListener);

        frame.pack();
        changeActionsState(0);
    }

    public void refreshEventModel() {
        eventListModel.refresh();
        eventTableModel.refresh();
        this.statistics.refresh();
        rowSorter.sort();
    }

    public void changeTimeUnitActionsState(boolean enabled) {
        editButton.getAction().setEnabled(enabled);
        deleteButton.getAction().setEnabled(enabled);
        editButton.setForeground(enabled ? Color.BLACK : Color.GRAY);
        deleteButton.setForeground(enabled ? Color.BLACK : Color.GRAY);
    }

    private void changeActionsState(int selectedItemsCount) {
        editButton.getAction().setEnabled(selectedItemsCount == 1);
        deleteButton.getAction().setEnabled(selectedItemsCount >= 1);
        editButton.setForeground(selectedItemsCount == 1 ? Color.BLACK : Color.GRAY);
        deleteButton.setForeground(selectedItemsCount >= 1 ? Color.BLACK : Color.GRAY);
        templateFromEventAction.setEnabled(selectedItemsCount == 1);
    }

    public void refreshCategoryListModel() {
        categoryListModel.refresh();
    }

    public void refreshTimeUnitListModel() {
        timeUnitListModel.refresh();
    }

    public void refreshTemplateListModel() {
        templateListModel.refresh();
    }

    public void show() {
        frame.setVisible(true);
    }

    public void applyButtonStrategy() {
        setPanelEnabled(getStatusFilterPanel(), buttonTabStrategy.statusFilterEnabled());
        setPanelEnabled(getCategoryFilterPanel(), buttonTabStrategy.categoryFilterEnabled());
        setPanelEnabled(getDurationFilterPanel(), buttonTabStrategy.durationFilterEnabled());
        addButton.setAction(buttonTabStrategy.getAddAction());
        editButton.setAction(buttonTabStrategy.getEditAction());
        deleteButton.setAction(buttonTabStrategy.getDeleteAction());
    }

    private void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                setPanelEnabled((JPanel) component, isEnabled);
            }
            component.setEnabled(isEnabled);
        }
    }

    private JMenuBar createMenuBar(PanelChangeListener listener, EventButtonTabStrategy eventButtonTabStrategy, TemplateButtonTabStrategy templateButtonTabStrategy,
                                   CategoryButtonTabStrategy categoryButtonTabStrategy, TimeUnitButtonTabStrategy timeUnitButtonTabStrategy) {
        var menuBar = new JMenuBar();
        var fileMenu = new JMenu("File");
        fileMenu.add(importAction);
        fileMenu.add(exportAction);
        fileMenu.add(quitAction);

        var eventMenu = new JMenu("Event");
        eventMenu.add(eventButtonTabStrategy.getAddAction());
        eventMenu.add(eventButtonTabStrategy.getEditAction());
        eventMenu.add(eventButtonTabStrategy.getDeleteAction());
        eventMenu.add(templateFromEventAction);

        var templateMenu = new JMenu("Template");
        templateMenu.add(templateButtonTabStrategy.getAddAction());
        templateMenu.add(templateButtonTabStrategy.getEditAction());
        templateMenu.add(templateButtonTabStrategy.getDeleteAction());

        var categoryMenu = new JMenu("Category");
        categoryMenu.add(categoryButtonTabStrategy.getAddAction());
        categoryMenu.add(categoryButtonTabStrategy.getEditAction());
        categoryMenu.add(categoryButtonTabStrategy.getDeleteAction());

        var timeUnitMenu = new JMenu("Time Unit");
        timeUnitMenu.add(timeUnitButtonTabStrategy.getAddAction());
        timeUnitMenu.add(timeUnitButtonTabStrategy.getEditAction());
        timeUnitMenu.add(timeUnitButtonTabStrategy.getDeleteAction());

        listener.setMenus(eventMenu, categoryMenu, templateMenu, timeUnitMenu);
        listener.disableMenuBar();
        eventMenu.setEnabled(true);

        menuBar.add(fileMenu);
        menuBar.add(eventMenu);
        menuBar.add(templateMenu);
        menuBar.add(categoryMenu);
        menuBar.add(timeUnitMenu);
        return menuBar;
    }

    private JToolBar createToolbar(JButton addButton, JButton editButton, JButton deleteButton, Component...
            components) {
        var toolbar = new JToolBar();
        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.addSeparator();
        for (var component : components) {
            toolbar.add(component);
            toolbar.addSeparator(new Dimension(50, 10));
        }
        return toolbar;
    }

    private JFrame createFrame() {
        var frame = new JFrame("TO-DO");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(MAXIMIZED_BOTH);
        return frame;
    }

    private JPopupMenu createEventTablePopupMenu(EventButtonTabStrategy eventButtonTabStrategy) {
        var menu = new JPopupMenu();
        menu.add(eventButtonTabStrategy.getAddAction());
        menu.add(eventButtonTabStrategy.getEditAction());
        menu.add(eventButtonTabStrategy.getDeleteAction());
        menu.add(templateFromEventAction);
        return menu;
    }

    private JPopupMenu createCategoryTablePopupMenu(CategoryButtonTabStrategy categoryButtonTabStrategy) {
        var menu = new JPopupMenu();
        menu.add(categoryButtonTabStrategy.getAddAction());
        menu.add(categoryButtonTabStrategy.getEditAction());
        menu.add(categoryButtonTabStrategy.getDeleteAction());
        return menu;
    }

    private JPopupMenu createTemplateTablePopupMenu(TemplateButtonTabStrategy templateButtonTabStrategy) {
        var menu = new JPopupMenu();
        menu.add(templateButtonTabStrategy.getAddAction());
        menu.add(templateButtonTabStrategy.getEditAction());
        menu.add(templateButtonTabStrategy.getDeleteAction());
        return menu;
    }

    private JPopupMenu createTimeUnitTablePopupMenu(TimeUnitButtonTabStrategy timeUnitButtonTabStrategy) {
        var menu = new JPopupMenu();
        menu.add(timeUnitButtonTabStrategy.getAddAction());
        menu.add(timeUnitButtonTabStrategy.getEditAction());
        menu.add(timeUnitButtonTabStrategy.getDeleteAction());
        return menu;
    }

    public void setButtonTabStrategy(ButtonTabStrategy buttonTabStrategy) {
        this.buttonTabStrategy = buttonTabStrategy;
    }

    public JPanel getStatusFilterPanel() {
        return statusFilterPanel;
    }

    public JPanel getCategoryFilterPanel() {
        return categoryFilterPanel;
    }

    public JPanel getDurationFilterPanel() {
        return durationFilterPanel;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public TableModel<Event> getEventTableModel() {
        return eventTableModel;
    }

    public TableModel<Category> getCategoryTableModel() {
        return categoryTableModel;
    }

    public TableModel<TimeUnit> getTimeUnitTableModel() {
        return timeUnitTableModel;
    }

    public TableModel<Template> getTemplateTableModel() {
        return templateTableModel;
    }

    public Validator<Event> getEventValidator() {
        return eventValidator;
    }

    public Validator<Category> getCategoryValidator() {
        return categoryValidator;
    }

    public Validator<TimeUnit> getTimeUnitValidator() {
        return timeUnitValidator;
    }

    public Validator<Template> getTemplateValidator() {
        return templateValidator;
    }
}
