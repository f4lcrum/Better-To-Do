package cz.fi.muni.pv168.todo.ui;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Status;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.action.AddCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.AddEventAction;
import cz.fi.muni.pv168.todo.ui.action.AddTemplateAction;
import cz.fi.muni.pv168.todo.ui.action.AddTimeUnitAction;
import cz.fi.muni.pv168.todo.ui.action.CreateTemplateFromEventAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteEventAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteTemplateAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteTimeUnitAction;
import cz.fi.muni.pv168.todo.ui.action.EditCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.EditEventAction;
import cz.fi.muni.pv168.todo.ui.action.EditTemplateAction;
import cz.fi.muni.pv168.todo.ui.action.EditTimeUnitAction;
import cz.fi.muni.pv168.todo.ui.action.ExportAction;
import cz.fi.muni.pv168.todo.ui.action.ImportAction;
import cz.fi.muni.pv168.todo.ui.action.QuitAction;
import cz.fi.muni.pv168.todo.ui.action.strategy.ButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.EventButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.filter.components.FilterPanel;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EventTableFilter;
import cz.fi.muni.pv168.todo.ui.listener.PanelChangeListener;
import cz.fi.muni.pv168.todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.todo.ui.model.Column;
import cz.fi.muni.pv168.todo.ui.model.EventListModel;
import cz.fi.muni.pv168.todo.ui.model.StatusListModel;
import cz.fi.muni.pv168.todo.ui.model.TemplateListModel;
import cz.fi.muni.pv168.todo.ui.model.TimeUnitListModel;
import cz.fi.muni.pv168.todo.ui.panels.CategoryTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.StatisticsPanel;
import cz.fi.muni.pv168.todo.ui.panels.TemplateTablePanel;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.panels.TimeUnitTablePanel;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;
import static java.awt.Frame.MAXIMIZED_BOTH;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.util.List;

public class MainWindow {

    private final JFrame frame;
    private ButtonTabStrategy buttonTabStrategy;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final Action quitAction;
    private final Action exportAction;
    private final Action importAction;
    private final EventListModel eventListModel;
    private final TemplateListModel templateListModel;
    private final CategoryListModel categoryListModel;
    private final TimeUnitListModel timeUnitListModel;
    private final JTabbedPane tabbedPane;
    private final EventTablePanel eventTablePanel;
    private final CategoryTablePanel categoryTablePanel;
    private final TemplateTablePanel templateTablePanel;
    private final TimeUnitTablePanel timeUnitTablePanel;
    private final JPanel statusFilterPanel;
    private final JPanel categoryFilterPanel;
    private final JPanel durationFilterPanel;
    private final TableModel<Event> eventTableModel;

    private final TableModel<Category> categoryTableModel;
    private final TableModel<TimeUnit> timeUnitTableModel;
    private final TableModel<Template> templateTableModel;
    private final Validator<Event> eventValidator;
    private final Validator<Category> categoryValidator;
    private final Validator<TimeUnit> timeUnitValidator;
    private final Validator<Template> templateValidator;

    private final StatisticsPanel statistics;

    public MainWindow(DependencyProvider dependencyProvider) {
        this.eventTableModel = new TableModel<>(dependencyProvider.getEventCrudService(), List.of(
                new Column<>(" ", Color.class, Event::getColour),
                new Column<>("Name of the event", String.class, Event::getName),
                new Column<>("Start date and Time", LocalDateTime.class, Event::calculateStart),
                new Column<>("Category", Category.class, Event::getCategory),
                new Column<>("Status", Status.class, Event::getStatus),
                new Column<>("Duration (minutes)", String.class, Event::getDurationString)
        ));
        this.eventTablePanel = new EventTablePanel(eventTableModel, this::changeActionsState);
        this.templateTableModel = new TableModel<>(dependencyProvider.getTemplateCrudService(), List.of(
                new Column<>(" ", Color.class, Template::getColour),
                new Column<>("Template name", String.class, Template::getName),
                new Column<>("Category", Category.class, Template::getCategory),
                new Column<>("Duration", String.class, Template::getDurationString)
        ));
        this.templateTablePanel = new TemplateTablePanel(templateTableModel, this::changeActionsState);
        this.categoryTableModel = new TableModel<>(dependencyProvider.getCategoryCrudService(), List.of(
                new Column<>(" ", Color.class, Category::getColor),
                new Column<>("Name", String.class, Category::getName)
        ));
        this.categoryTablePanel = new CategoryTablePanel(categoryTableModel, this::changeActionsState);
        this.timeUnitTableModel = new TableModel<>(dependencyProvider.getTimeUnitCrudService(), List.of(
                new Column<>("Name", String.class, TimeUnit::getName),
                new Column<>("Hour count", Long.class, TimeUnit::getHours),
                new Column<>("Minute count", Long.class, TimeUnit::getMinutes)
        ));
        this.timeUnitTablePanel = new TimeUnitTablePanel(timeUnitTableModel, this::changeActionsState);
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

        // Apply popup menu bindings
        eventTablePanel.getEventTable().setComponentPopupMenu(createEventTablePopupMenu());
        categoryTablePanel.getEventTable().setComponentPopupMenu(createCategoryTablePopupMenu());
        templateTablePanel.getEventTable().setComponentPopupMenu(createTemplateTablePopupMenu());
        timeUnitTablePanel.getEventTable().setComponentPopupMenu(createTimeUnitTablePopupMenu());

        var rowSorter = new TableRowSorter<>(eventTableModel);
        rowSorter.toggleSortOrder(2); // 2 == 3rd column is start date, automatically sorts 3rd column

        var eventTableFilter = new EventTableFilter(rowSorter, statistics);
        eventTablePanel.getEventTable().setRowSorter(rowSorter);

        this.tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Events", eventTablePanel);
        tabbedPane.addTab("Templates", templateTablePanel);
        tabbedPane.addTab("Categories", categoryTablePanel);
        tabbedPane.addTab("Units", timeUnitTablePanel);
        tabbedPane.addChangeListener(e -> changeActionsState(0));

        // Filters
        this.statusFilterPanel = FilterPanel.createFilterPanel(FilterPanel.createStatusFilter(eventTableFilter, statusListModel), "Status: ");
        this.categoryFilterPanel = FilterPanel.createFilterPanel(FilterPanel.createCategoryFilter(eventTableFilter, categoryListModel), "Category: ");
        this.durationFilterPanel = FilterPanel.createDurationFilter(eventTableFilter);

        this.buttonTabStrategy = new EventButtonTabStrategy(eventTablePanel.getEventTable(), categoryListModel, timeUnitListModel, templateListModel, this);

        this.addButton = new JButton(Icons.ADD_ICON);
        this.editButton = new JButton(Icons.EDIT_ICON);
        this.deleteButton = new JButton(Icons.DELETE_ICON);

        this.addButton.setAction(this.buttonTabStrategy.getAddAction());
        this.editButton.setAction(this.buttonTabStrategy.getEditAction());
        this.deleteButton.setAction(this.buttonTabStrategy.getDeleteAction());

        applyButtonStrategy();
        tabbedPane.addChangeListener(new PanelChangeListener(this));

        frame = createFrame();
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setJMenuBar(createMenuBar());
        frame.add(createToolbar(addButton, editButton, deleteButton, statusFilterPanel, categoryFilterPanel, durationFilterPanel), BorderLayout.BEFORE_FIRST_LINE);
        frame.add(this.statistics, BorderLayout.SOUTH);
        frame.pack();
        changeActionsState(0);
    }

    public void refreshEventListModel() {
        eventListModel.refresh();
        this.statistics.refresh();
    }

    private void changeActionsState(int selectedItemsCount) {
        editButton.getAction().setEnabled(selectedItemsCount == 1);
        deleteButton.getAction().setEnabled(selectedItemsCount >= 1);
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

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        var fileMenu = new JMenu("File");
        fileMenu.add(importAction);
        fileMenu.add(exportAction);
        fileMenu.add(quitAction);

        var eventMenu = new JMenu("Event");
        eventMenu.add(new AddEventAction(eventTablePanel.getEventTable(), categoryListModel, timeUnitListModel, templateListModel, this));
        eventMenu.add(new EditEventAction(eventTablePanel.getEventTable(), categoryListModel, timeUnitListModel, templateListModel, this));
        eventMenu.add(new DeleteEventAction(eventTablePanel.getEventTable(), this));
        eventMenu.add(new CreateTemplateFromEventAction(eventTablePanel.getEventTable(), templateTablePanel.getEventTable(), this, categoryListModel, timeUnitListModel));

        var categoryMenu = new JMenu("Category");
        categoryMenu.add(new AddCategoryAction(categoryTablePanel.getEventTable(), this));
        categoryMenu.add(new EditCategoryAction(categoryTablePanel.getEventTable(), this));
        categoryMenu.add(new DeleteCategoryAction(categoryTablePanel.getEventTable(), this));

        var templateMenu = new JMenu("Template");
        templateMenu.add(new AddTemplateAction(templateTablePanel.getEventTable(), this, categoryListModel, timeUnitListModel));
        templateMenu.add(new EditTemplateAction(templateTablePanel.getEventTable(), this, categoryListModel, timeUnitListModel));
        templateMenu.add(new DeleteTemplateAction(templateTablePanel.getEventTable(), this));

        var timeUnitMenu = new JMenu("Time Unit");
        timeUnitMenu.add(new AddTimeUnitAction(timeUnitTablePanel.getEventTable(), this));
        timeUnitMenu.add(new EditTimeUnitAction(timeUnitTablePanel.getEventTable(), this));
        timeUnitMenu.add(new DeleteTimeUnitAction(timeUnitTablePanel.getEventTable(), this));

        menuBar.add(fileMenu);
        menuBar.add(eventMenu);
        menuBar.add(categoryMenu);
        menuBar.add(templateMenu);
        menuBar.add(timeUnitMenu);
        return menuBar;
    }

    private JToolBar createToolbar(JButton addButton, JButton editButton, JButton deleteButton, Component... components) {
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

    private JPopupMenu createEventTablePopupMenu() {
        var menu = new JPopupMenu();
        menu.add(new DeleteEventAction(eventTablePanel.getEventTable(), this));
        menu.add(new EditEventAction(eventTablePanel.getEventTable(), categoryListModel, timeUnitListModel, templateListModel, this));
        menu.add(new AddEventAction(eventTablePanel.getEventTable(), categoryListModel, timeUnitListModel, templateListModel, this));
        menu.add(new CreateTemplateFromEventAction(eventTablePanel.getEventTable(), templateTablePanel.getEventTable(), this, categoryListModel, timeUnitListModel));
        return menu;
    }

    private JPopupMenu createCategoryTablePopupMenu() {
        var menu = new JPopupMenu();
        menu.add(new DeleteCategoryAction(categoryTablePanel.getEventTable(), this));
        menu.add(new EditCategoryAction(categoryTablePanel.getEventTable(), this));
        menu.add(new AddCategoryAction(categoryTablePanel.getEventTable(), this));
        return menu;
    }

    private JPopupMenu createTemplateTablePopupMenu() {
        var menu = new JPopupMenu();
        menu.add(new DeleteTemplateAction(templateTablePanel.getEventTable(), this));
        menu.add(new EditTemplateAction(templateTablePanel.getEventTable(), this, categoryListModel, timeUnitListModel));
        menu.add(new AddTemplateAction(templateTablePanel.getEventTable(), this, categoryListModel, timeUnitListModel));
        return menu;
    }

    private JPopupMenu createTimeUnitTablePopupMenu() {
        var menu = new JPopupMenu();
        menu.add(new DeleteTimeUnitAction(timeUnitTablePanel.getEventTable(), this));
        menu.add(new EditTimeUnitAction(timeUnitTablePanel.getEventTable(), this));
        menu.add(new AddTimeUnitAction(timeUnitTablePanel.getEventTable(), this));
        return menu;
    }

    public void setButtonTabStrategy(ButtonTabStrategy buttonTabStrategy) {
        this.buttonTabStrategy = buttonTabStrategy;
    }

    public CategoryListModel getCategoryListModel() {
        return categoryListModel;
    }

    public TimeUnitListModel getTimeUnitListModel() {
        return timeUnitListModel;
    }

    public TemplateListModel getTemplateListModel() {
        return templateListModel;
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

    public EventTablePanel getEventTablePanel() {
        return eventTablePanel;
    }

    public CategoryTablePanel getCategoryTablePanel() {
        return categoryTablePanel;
    }

    public TemplateTablePanel getTemplateTablePanel() {
        return templateTablePanel;
    }

    public TimeUnitTablePanel getTimeUnitTablePanel() {
        return timeUnitTablePanel;
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
