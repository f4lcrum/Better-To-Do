package cz.fi.muni.pv168.bosv.better_todo.ui;

import cz.fi.muni.pv168.bosv.better_todo.data.TestDataGenerator;
import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.*;
import cz.fi.muni.pv168.bosv.better_todo.ui.action.*;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.components.FilterComboboxBuilder;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.components.FilterListModelBuilder;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.EventTableFilter;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterCategoryValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterDurationValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterStatusValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.*;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.CategoryTablePanel;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.TemplateTablePanel;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.*;
import cz.fi.muni.pv168.bosv.better_todo.ui.resources.Icons;
import cz.fi.muni.pv168.bosv.better_todo.util.Either;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.StatisticsPanel;
import org.jfree.chart.plot.PlotRenderingInfo;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.Border;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

import static cz.fi.muni.pv168.bosv.better_todo.ui.filter.components.FilterPanel.*;

public class MainWindow {
    private final int EVENT_TAB = 0;
    private final int TEMPLATE_TAB = 1;
    private final int CATEGORY_TAB = 2;
    private final int START_DATE_COL = 2;
    private final JFrame frame;
    private final JTable eventTable;
    private final JTable templateTable;
    private final Action addAction;
    private final Action quitAction;
    private final Action editAction;
    private final Action deleteAction;
    private final Action addTemplateAction;
    private final Action editTemplateAction;
    private final Action deleteTemplateAction;
    private final Action addCategoryAction;
    private final Action editCategoryAction;
    private final Action deleteCategoryAction;

    private final Action exportAction;
    private final Action importAction;
    private final StatusListModel statusListModel;
    private final CategoryListModel categoryListModel;
    private final JPanel statistics;

    public MainWindow() {
        var testDataGenerator = new TestDataGenerator();
        var eventTableModel = new TodoTableModel(testDataGenerator.createTestEvents(10));
        var eventTablePanel = new EventTablePanel(eventTableModel);
        var templateTableModel = new TemplateTableModel(testDataGenerator.createTestTemplates(10));
        var templateTablePanel = new TemplateTablePanel(templateTableModel);
        var categoryTableModel = new CategoryTableModel(testDataGenerator.createTestCategories(10));
        var categoryTablePanel = new CategoryTablePanel(categoryTableModel);
        var events = testDataGenerator.createTestEvents(10);
        categoryListModel = new CategoryListModel(testDataGenerator.getCategories());
        statusListModel = new StatusListModel();
        eventTable = createEventTable(events);
        templateTable = createTemplateTable(testDataGenerator.createTestTemplates(10));

        addAction = new AddEventAction(eventTablePanel.getEventTable(), categoryListModel, statusListModel);
        quitAction = new QuitAction();
        editAction = new EditEventAction(eventTablePanel.getEventTable(), categoryListModel, statusListModel);
        deleteAction = new DeleteEventAction(eventTablePanel.getEventTable());

        addCategoryAction = new AddCategoryAction(categoryTablePanel.getEventTable());
        editCategoryAction = new EditCategoryAction(categoryTablePanel.getEventTable());
        deleteCategoryAction = new DeleteCategoryAction(eventTablePanel.getEventTable());

        addTemplateAction = new AddTemplateAction(templateTablePanel.getEventTable(), categoryListModel, statusListModel);
        editTemplateAction = new EditTemplateAction(templateTablePanel.getEventTable(), categoryListModel, statusListModel);
        deleteTemplateAction = new DeleteTemplateAction(templateTablePanel.getEventTable());

        exportAction = new ExportAction(eventTablePanel);
        importAction = new ImportAction(eventTablePanel);
        statistics = new StatisticsPanel();
        frame = createFrame();

        var rowSorter = new TableRowSorter<>(eventTableModel);
        rowSorter.toggleSortOrder(START_DATE_COL); // 2 == 3rd column is start date, automatically sorts 3rd column
        var eventTableFilter = new EventTableFilter(rowSorter);
        eventTablePanel.getEventTable().setRowSorter(rowSorter);

        var addButton = new JButton(Icons.ADD_ICON);
        var editButton = new JButton(Icons.EDIT_ICON);
        var deleteButton = new JButton(Icons.DELETE_ICON);
        addButton.setAction(addAction);
        editButton.setAction(editAction);
        deleteButton.setAction(deleteAction);

        var tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Events", eventTablePanel);
        tabbedPane.addTab("Templates", templateTablePanel);
        tabbedPane.addTab("Categories", categoryTablePanel);

        // Filters
        var statusFilter = createFilterPanel(createStatusFilter(eventTableFilter, statusListModel), "Status: ");
        var durationFilter = createFilterPanel(createDurationFilter(eventTableFilter), "Duration: ");
        var categoryFilter = createFilterPanel(createCategoryFilter(eventTableFilter, categoryListModel), "Category: ");

        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                switch (tabbedPane.getSelectedIndex()) {
                    case EVENT_TAB:
                        setPanelEnabled(statusFilter, true);
                        setPanelEnabled(durationFilter, true);
                        setPanelEnabled(categoryFilter, true);
                        addButton.setAction(addAction);
                        editButton.setAction(editAction);
                        deleteButton.setAction(deleteAction);
                        break;
                    case TEMPLATE_TAB:
                        setPanelEnabled(statusFilter, false);
                        setPanelEnabled(durationFilter, false);
                        setPanelEnabled(categoryFilter, false);
                        addButton.setAction(addTemplateAction);
                        editButton.setAction(editTemplateAction);
                        deleteButton.setAction(deleteTemplateAction);
                        break;
                    case CATEGORY_TAB:
                        setPanelEnabled(statusFilter, false);
                        setPanelEnabled(durationFilter, false);
                        setPanelEnabled(categoryFilter, false);
                        addButton.setAction(addCategoryAction);
                        editButton.setAction(editCategoryAction);
                        deleteButton.setAction(deleteCategoryAction);
                        break;
                    default:
                        break;
                }
            }
        });


        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setJMenuBar(createMenuBar());
        frame.pack();
        frame.add(createToolbar(addButton, editButton, deleteButton, statusFilter, durationFilter, categoryFilter), BorderLayout.BEFORE_FIRST_LINE);
        frame.add(statistics, BorderLayout.SOUTH);
    }

    void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                setPanelEnabled((JPanel) component, isEnabled);
            }
            component.setEnabled(isEnabled);
        }
    }

    private JTable createEventTable(List<Event> employees) {
        var model = new TodoTableModel(employees);
        var table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        return table;
    }

    private JTable createTemplateTable(List<Template> templates) {
        var model = new TemplateTableModel(templates);
        var table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        return table;
    }

    public void show() {
        frame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        var fileMenu = new JMenu("File");
        fileMenu.add(importAction);
        fileMenu.add(exportAction);
        fileMenu.add(quitAction);

        var eventMenu = new JMenu("Event");
        eventMenu.add(addAction);
        eventMenu.add(editAction);
        eventMenu.add(deleteAction);

        var categoryMenu = new JMenu("Category");
        categoryMenu.add(addCategoryAction);
        categoryMenu.add(editCategoryAction);
        categoryMenu.add(deleteCategoryAction);

        var templateMenu = new JMenu("Template");
        templateMenu.add(addTemplateAction);
        templateMenu.add(editTemplateAction);
        templateMenu.add(deleteTemplateAction);

        menuBar.add(fileMenu);
        menuBar.add(eventMenu);
        menuBar.add(categoryMenu);
        menuBar.add(templateMenu);
        // menuBar.add(statisticsMenu);
        return menuBar;
    }

    private JToolBar createToolbar(JButton addButton, JButton editButton,
                                   JButton deleteButton, Component... components) {
        var toolbar = new JToolBar();
        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.addSeparator();
        for (var component : components) {
            toolbar.add(component);
            toolbar.addSeparator(new Dimension(50,10));
        }
        return toolbar;
    }

    private JFrame createFrame() {
        var frame = new JFrame("TO-DO");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        return frame;
    }
}
