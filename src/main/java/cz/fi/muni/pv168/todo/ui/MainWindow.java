package cz.fi.muni.pv168.todo.ui;

import cz.fi.muni.pv168.todo.data.TestDataGenerator;
import cz.fi.muni.pv168.todo.ui.action.AddCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.AddEventAction;
import cz.fi.muni.pv168.todo.ui.action.AddTemplateAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteEventAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteTemplateAction;
import cz.fi.muni.pv168.todo.ui.action.EditCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.EditEventAction;
import cz.fi.muni.pv168.todo.ui.action.EditTemplateAction;
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
import cz.fi.muni.pv168.todo.ui.model.EventTableModel;
import cz.fi.muni.pv168.todo.ui.model.StatusListModel;
import cz.fi.muni.pv168.todo.ui.model.TemplateTableModel;
import cz.fi.muni.pv168.todo.ui.model.TimeUnitTableModel;
import cz.fi.muni.pv168.todo.ui.panels.CategoryTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.StatisticsPanel;
import cz.fi.muni.pv168.todo.ui.panels.TemplateTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.TimeUnitTablePanel;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import static java.awt.Frame.MAXIMIZED_BOTH;

import cz.fi.muni.pv168.todo.wiring.DependencyProvider;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

public class MainWindow {

    private final JFrame frame;
    private ButtonTabStrategy buttonTabStrategy;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final Action quitAction;
    private final Action exportAction;
    private final Action importAction;
    private final StatusListModel statusListModel;
    private final CategoryListModel categoryListModel;
    private final JTabbedPane tabbedPane;
    private final EventTablePanel eventTablePanel;
    private final CategoryTablePanel categoryTablePanel;
    private final TemplateTablePanel templateTablePanel;
    private final TimeUnitTablePanel timeUnitTablePanel;
    private final JPanel statusFilterPanel;
    private final JPanel categoryFilterPanel;
    private final CategoryTableModel categoryTableModel;

    public MainWindow(DependencyProvider dependencyProvider) {
        var testDataGenerator = new TestDataGenerator();
        var eventTableModel = new EventTableModel(testDataGenerator.createTestEvents(10));
        this.eventTablePanel = new EventTablePanel(eventTableModel);
        var templateTableModel = new TemplateTableModel(testDataGenerator.createTestTemplates(10));
        this.templateTablePanel = new TemplateTablePanel(templateTableModel);
        categoryTableModel = new CategoryTableModel(dependencyProvider.getCategoryCrudService());
        this.categoryTablePanel = new CategoryTablePanel(categoryTableModel);
        var timeUnitTableModel = new TimeUnitTableModel(dependencyProvider.getTimeUnitCrudService());
        this.timeUnitTablePanel = new TimeUnitTablePanel(timeUnitTableModel);
        categoryListModel = new CategoryListModel(testDataGenerator.getCategories());
        statusListModel = new StatusListModel();

        quitAction = new QuitAction();
        exportAction = new ExportAction(eventTablePanel);
        importAction = new ImportAction(eventTablePanel);

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

        this.buttonTabStrategy = new EventButtonTabStrategy(eventTablePanel.getEventTable(), categoryListModel, statusListModel);

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
        frame.pack();
        frame.add(createToolbar(addButton, editButton, deleteButton, statusFilterPanel, categoryFilterPanel), BorderLayout.BEFORE_FIRST_LINE);
        frame.add(statistics, BorderLayout.SOUTH);
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
        eventMenu.add(new AddEventAction(eventTablePanel.getEventTable(), categoryListModel));
        eventMenu.add(new EditEventAction(eventTablePanel.getEventTable(), categoryListModel));
        eventMenu.add(new DeleteEventAction(eventTablePanel.getEventTable()));

        var categoryMenu = new JMenu("Category");
        categoryMenu.add(new AddCategoryAction(categoryTablePanel.getEventTable(), this));
        categoryMenu.add(new EditCategoryAction(categoryTablePanel.getEventTable()));
        categoryMenu.add(new DeleteCategoryAction(categoryTablePanel.getEventTable(), this));

        var templateMenu = new JMenu("Template");
        templateMenu.add(new AddTemplateAction(templateTablePanel.getEventTable(), categoryListModel, statusListModel));
        templateMenu.add(new EditTemplateAction(templateTablePanel.getEventTable(), categoryListModel, statusListModel));
        templateMenu.add(new DeleteTemplateAction(templateTablePanel.getEventTable()));

        menuBar.add(fileMenu);
        menuBar.add(eventMenu);
        menuBar.add(categoryMenu);
        menuBar.add(templateMenu);
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

    public void setButtonTabStrategy(ButtonTabStrategy buttonTabStrategy) {
        this.buttonTabStrategy = buttonTabStrategy;
    }

    public StatusListModel getStatusListModel() {
        return statusListModel;
    }

    public CategoryListModel getCategoryListModel() {
        return categoryListModel;
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

    public CategoryTableModel getCategoryTableModel() { return categoryTableModel; }
}
