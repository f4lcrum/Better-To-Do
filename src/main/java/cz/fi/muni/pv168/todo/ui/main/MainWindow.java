package cz.fi.muni.pv168.todo.ui.main;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Status;
import cz.fi.muni.pv168.todo.ui.action.ExportAction;
import cz.fi.muni.pv168.todo.ui.action.ImportAction;
import cz.fi.muni.pv168.todo.ui.action.QuitAction;
import cz.fi.muni.pv168.todo.ui.action.strategy.ButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.filter.components.DurationFilterComponents;
import cz.fi.muni.pv168.todo.ui.filter.components.FilterPanel;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EventTableFilter;
import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterCategoryValues;
import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterStatusValues;
import cz.fi.muni.pv168.todo.ui.listener.PanelChangeListener;
import cz.fi.muni.pv168.todo.ui.model.Column;
import cz.fi.muni.pv168.todo.ui.model.StatusListModel;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.panels.StatisticsPanel;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.util.Either;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;

import static cz.fi.muni.pv168.todo.ui.filter.components.FilterPanel.createResetFiltersButton;

public class MainWindow {

    private final JFrame frame;
    private ButtonTabStrategy buttonTabStrategy;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final JTabbedPane tabbedPane;
    private final JPanel statusFilterPanel;
    private final JPanel categoryFilterPanel;
    private final JPanel durationFilterPanel;
    private final StatisticsPanel statistics;
    private final MainWindowCategory mainWindowCategory;
    private final MainWindowTimeUnit mainWindowTimeUnit;
    private final MainWindowTemplate mainWindowTemplate;
    private final MainWindowEvent mainWindowEvent;
    private final TableRowSorter<TableModel<Event>> rowSorter;

    public MainWindow(DependencyProvider dependencyProvider) {
        Column<Event, LocalDateTime> startDateRow = new Column<>("Start date and Time", LocalDateTime.class, Event::calculateStart);
        var statusListModel = new StatusListModel();

        this.mainWindowCategory = new MainWindowCategory(dependencyProvider, this::changeActionsState, this::refresh);
        this.mainWindowTimeUnit = new MainWindowTimeUnit(dependencyProvider, this::changeActionsState, this::refresh);
        this.mainWindowTemplate = new MainWindowTemplate(dependencyProvider, this::changeActionsState, mainWindowCategory.getListModel(), mainWindowTimeUnit.getListModel(), this::refresh);
        this.mainWindowEvent = new MainWindowEvent(dependencyProvider, this::changeActionsState, mainWindowCategory.getListModel(), mainWindowTemplate, mainWindowTimeUnit.getListModel(),
                startDateRow, this::refreshStatistics, this::refresh);
        this.statistics = new StatisticsPanel(dependencyProvider.getEventCrudService(), mainWindowEvent.getTableModel());

        this.rowSorter = new TableRowSorter<>(mainWindowEvent.getTableModel());
        mainWindowEvent.getTablePanel().getTable().setRowSorter(rowSorter);
        rowSorter.toggleSortOrder(mainWindowEvent.getTableModel().getColumnIndex(startDateRow));

        this.tabbedPane = initTabbedPane();

        EventTableFilter eventTableFilter = new EventTableFilter(rowSorter, statistics);
        JList<Either<SpecialFilterStatusValues, Status>> statusFilterList = FilterPanel.createStatusFilter(eventTableFilter, statusListModel);
        JList<Either<SpecialFilterCategoryValues, Category>> categoryFilterList = FilterPanel.createCategoryFilter(eventTableFilter, mainWindowCategory.getListModel());
        this.statusFilterPanel = FilterPanel.createFilterPanel(statusFilterList, "Status: ");
        this.categoryFilterPanel = FilterPanel.createFilterPanel(categoryFilterList, "Category: ");
        DurationFilterComponents durationComponents = FilterPanel.createDurationFilter(eventTableFilter);
        this.durationFilterPanel = durationComponents.panel;
        JButton resetFiltersButton = createResetFiltersButton(eventTableFilter);
        eventTableFilter.setUIComponents(durationComponents.minDurationField, durationComponents.maxDurationField, statusFilterList, categoryFilterList);

        this.buttonTabStrategy = mainWindowEvent.getButtonTabStrategy();
        this.addButton = new JButton(Icons.ADD_ICON);
        this.editButton = new JButton(Icons.EDIT_ICON);
        this.deleteButton = new JButton(Icons.DELETE_ICON);
        this.addButton.setAction(this.buttonTabStrategy.getAddAction());
        this.editButton.setAction(this.buttonTabStrategy.getEditAction());
        this.deleteButton.setAction(this.buttonTabStrategy.getDeleteAction());

        var panelChangeListener = new PanelChangeListener(
                this,
                mainWindowEvent.getButtonTabStrategy(),
                mainWindowTemplate.getButtonTabStrategy(),
                mainWindowCategory.getButtonTabStrategy(),
                mainWindowTimeUnit.getButtonTabStrategy(),
                mainWindowEvent.getTablePanel(),
                mainWindowTemplate.getTablePanel(),
                mainWindowCategory.getTablePanel(),
                mainWindowTimeUnit.getTablePanel()
        );

        var menuBar = MainWindowHelper.createMenuBar(
                panelChangeListener,
                mainWindowEvent.getButtonTabStrategy(),
                mainWindowTemplate.getButtonTabStrategy(),
                mainWindowCategory.getButtonTabStrategy(),
                mainWindowTimeUnit.getButtonTabStrategy(),
                new ImportAction(mainWindowEvent.getTablePanel(),
                        dependencyProvider,
                        this::refresh
                ),
                new ExportAction(
                        mainWindowEvent.getTablePanel(),
                        dependencyProvider.getExportService()
                ),
                new QuitAction()
        );

        var toolbar = MainWindowHelper.createToolbar(addButton, editButton, deleteButton, statusFilterPanel, categoryFilterPanel, durationFilterPanel, resetFiltersButton);

        frame = MainWindowHelper.createFrame();
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setJMenuBar(menuBar);
        frame.add(toolbar, BorderLayout.BEFORE_FIRST_LINE);
        frame.add(this.statistics, BorderLayout.SOUTH);
        tabbedPane.addChangeListener(panelChangeListener);
        frame.pack();
        changeActionsState(0, true);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void applyButtonStrategy() {
        setPanelEnabled(statusFilterPanel, buttonTabStrategy.statusFilterEnabled());
        setPanelEnabled(categoryFilterPanel, buttonTabStrategy.categoryFilterEnabled());
        setPanelEnabled(durationFilterPanel, buttonTabStrategy.durationFilterEnabled());
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

    private void refresh() {
        mainWindowEvent.refreshModel();
        mainWindowCategory.refreshModel();
        mainWindowTemplate.refreshModel();
        mainWindowTimeUnit.refreshModel();
    }

    private void refreshStatistics() {
        statistics.refresh();
        rowSorter.sort();
    }

    private JTabbedPane initTabbedPane() {
        var newTabbedPane = new JTabbedPane();
        newTabbedPane.addTab("Events", mainWindowEvent.getTablePanel());
        newTabbedPane.addTab("Templates", mainWindowTemplate.getTablePanel());
        newTabbedPane.addTab("Categories", mainWindowCategory.getTablePanel());
        newTabbedPane.addTab("Units", mainWindowTimeUnit.getTablePanel());
        newTabbedPane.addChangeListener(e -> changeActionsState(0, true));
        return newTabbedPane;
    }

    private void changeActionsState(int selectedItemsCount, boolean enabled) {
        var edit = enabled && selectedItemsCount == 1;
        var delete = enabled && selectedItemsCount >= 1;
        editButton.getAction().setEnabled(edit);
        deleteButton.getAction().setEnabled(delete);
        editButton.setForeground(edit ? Color.BLACK : Color.GRAY);
        deleteButton.setForeground(delete ? Color.BLACK : Color.GRAY);
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setButtonTabStrategy(ButtonTabStrategy buttonTabStrategy) {
        this.buttonTabStrategy = buttonTabStrategy;
    }
}
