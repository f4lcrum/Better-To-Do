package cz.fi.muni.pv168.todo.ui.listener;

import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.action.strategy.CategoryButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.EventButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.TemplateButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.TimeUnitButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.todo.ui.model.StatusListModel;

import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PanelChangeListener implements ChangeListener {

    private static final int EVENT_TAB_INDEX = 0;
    private static final int TEMPLATE_TAB_INDEX = 1;
    private static final int CATEGORY_TAB_INDEX = 2;
    private static final int TIMEUNIT_TAB_INDEX = 3;
    private final MainWindow mainWindow;

    public PanelChangeListener(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        changeActions();
    }

    private void changeActions() {
        JTable eventTable = mainWindow.getEventTablePanel().getEventTable();
        JTable templateTable = mainWindow.getTemplateTablePanel().getEventTable();
        JTable categoryTable = mainWindow.getCategoryTablePanel().getEventTable();
        JTable timeUnitTable = mainWindow.getTimeUnitTablePanel().getEventTable();

        CategoryListModel categoryListModel = mainWindow.getCategoryListModel();
        StatusListModel statusListModel = mainWindow.getStatusListModel();

        int selectedIndex = mainWindow.getTabbedPane().getSelectedIndex();
        switch (selectedIndex) {
            case EVENT_TAB_INDEX:
                mainWindow.setButtonTabStrategy(new EventButtonTabStrategy(eventTable, categoryListModel, statusListModel));
                break;
            case TEMPLATE_TAB_INDEX:
                mainWindow.setButtonTabStrategy(new TemplateButtonTabStrategy(templateTable, categoryListModel, statusListModel));
                break;
            case CATEGORY_TAB_INDEX:
                mainWindow.setButtonTabStrategy(new CategoryButtonTabStrategy(categoryTable));
                break;
            case TIMEUNIT_TAB_INDEX:
                mainWindow.setButtonTabStrategy(new TimeUnitButtonTabStrategy(timeUnitTable, mainWindow));
                break;
            default:
                throw new UnsupportedOperationException(String.format("The strategy for column index %d is not yet impolemented!", selectedIndex));
        }
        mainWindow.applyButtonStrategy();
    }
}
