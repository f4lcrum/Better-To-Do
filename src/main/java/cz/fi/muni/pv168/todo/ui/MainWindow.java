package cz.fi.muni.pv168.todo.ui;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.action.AddCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.AddEventAction;
import cz.fi.muni.pv168.todo.ui.action.AddTemplateAction;
import cz.fi.muni.pv168.todo.ui.action.AddTimeUnitAction;
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
import cz.fi.muni.pv168.todo.ui.model.CategoryTableModel;
import cz.fi.muni.pv168.todo.ui.model.EventListModel;
import cz.fi.muni.pv168.todo.ui.model.EventTableModel;
import cz.fi.muni.pv168.todo.ui.model.StatusListModel;
import cz.fi.muni.pv168.todo.ui.model.TemplateListModel;
import cz.fi.muni.pv168.todo.ui.model.TemplateTableModel;
import cz.fi.muni.pv168.todo.ui.model.TimeUnitListModel;
import cz.fi.muni.pv168.todo.ui.model.TimeUnitTableModel;
import cz.fi.muni.pv168.todo.ui.panels.CategoryTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.StatisticsPanel;
import cz.fi.muni.pv168.todo.ui.panels.TemplateTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.TimeUnitTablePanel;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

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
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

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
    private final EventTableModel eventTableModel;
    private final CategoryTableModel categoryTableModel;
    private final TimeUnitTableModel timeUnitTableModel;
    private final TemplateTableModel templateTableModel;
    private final Validator<Event> eventValidator;
    private final Validator<Category> categoryValidator;
    private final Validator<TimeUnit> timeUnitValidator;
    private final Validator<Template> templateValidator;


    public MainWindow(DependencyProvider dependencyProvider) {
        this.eventTableModel = new EventTableModel(dependencyProvider.getEventCrudService());
        this.eventTablePanel = new EventTablePanel(eventTableModel);
        this.templateTableModel = new TemplateTableModel(dependencyProvider.getTemplateCrudService());
        this.templateTablePanel = new TemplateTablePanel(templateTableModel);
        this.categoryTableModel = new CategoryTableModel(dependencyProvider.getCategoryCrudService());
        this.categoryTablePanel = new CategoryTablePanel(categoryTableModel);
        this.timeUnitTableModel = new TimeUnitTableModel(dependencyProvider.getTimeUnitCrudService());
        this.timeUnitTablePanel = new TimeUnitTablePanel(timeUnitTableModel);
        this.categoryListModel = new CategoryListModel(dependencyProvider.getCategoryCrudService());
        this.timeUnitListModel = new TimeUnitListModel(dependencyProvider.getTimeUnitCrudService());
        this.templateListModel = new TemplateListModel(dependencyProvider.getTemplateCrudService());
        this.eventListModel = new EventListModel(dependencyProvider.getEventCrudService());
        this.eventValidator = dependencyProvider.getEventValidator();
        this.categoryValidator = dependencyProvider.getCategoryValidator();
        this.timeUnitValidator = dependencyProvider.getTimeUnitValidator();
        this.templateValidator = dependencyProvider.getTemplateValidator();
        var statusListModel = new StatusListModel();

        quitAction = new QuitAction();
        exportAction = new ExportAction(eventTablePanel);
        importAction = new ImportAction(eventTablePanel);

        // Apply popup menu bindings
        eventTablePanel.getEventTable().setComponentPopupMenu(createEventTablePopupMenu());
        categoryTablePanel.getEventTable().setComponentPopupMenu(createCategoryTablePopupMenu());
        templateTablePanel.getEventTable().setComponentPopupMenu(createTemplateTablePopupMenu());
        timeUnitTablePanel.getEventTable().setComponentPopupMenu(createTimeUnitTablePopupMenu());

        JPanel statistics = new StatisticsPanel();

        var rowSorter = new TableRowSorter<>(eventTableModel);
        rowSorter.toggleSortOrder(2); // 2 == 3rd column is start date, automatically sorts 3rd column

        var eventTableFilter = new EventTableFilter(rowSorter);
        eventTablePanel.getEventTable().setRowSorter(rowSorter);

        this.tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Events", eventTablePanel);
        tabbedPane.addTab("Templates", templateTablePanel);
        tabbedPane.addTab("Categories", categoryTablePanel);
        tabbedPane.addTab("Units", timeUnitTablePanel);

        // Filters
        this.statusFilterPanel = FilterPanel.createFilterPanel(FilterPanel.createStatusFilter(eventTableFilter, statusListModel), "Status: ");
        this.categoryFilterPanel = FilterPanel.createFilterPanel(FilterPanel.createCategoryFilter(eventTableFilter, categoryListModel), "Category: ");

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
        frame.add(createToolbar(addButton, editButton, deleteButton, statusFilterPanel, categoryFilterPanel), BorderLayout.BEFORE_FIRST_LINE);
        frame.add(statistics, BorderLayout.SOUTH);
        frame.pack();
    }

    public void refreshEventListModel() {
        eventListModel.refresh();
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

    public EventListModel getCEventListModel() {
        return eventListModel;
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

    public EventTableModel getEventTableModel() {
        return eventTableModel;
    }

    public CategoryTableModel getCategoryTableModel() {
        return categoryTableModel;
    }

    public TimeUnitTableModel getTimeUnitTableModel() {
        return timeUnitTableModel;
    }

    public TemplateTableModel getTemplateTableModel() {
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
