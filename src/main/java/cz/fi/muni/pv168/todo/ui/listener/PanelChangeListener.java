package cz.fi.muni.pv168.todo.ui.listener;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.action.strategy.ButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.main.MainWindow;
import cz.fi.muni.pv168.todo.ui.panels.TablePanel;

import javax.swing.JMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PanelChangeListener implements ChangeListener {

    private final MainWindow mainWindow;
    private final ButtonTabStrategy eventButtonTabStrategy;
    private final ButtonTabStrategy templateButtonTabStrategy;
    private final ButtonTabStrategy categoryButtonTabStrategy;
    private final ButtonTabStrategy timeUnitButtonTabStrategy;
    private final TablePanel<Event> eventTablePanel;
    private final TablePanel<Template> templateTablePanel;
    private final TablePanel<Category> categoryTablePanel;
    private final TablePanel<TimeUnit> timeUnitTablePanel;
    private JMenu eventMenu;
    private JMenu categoryMenu;
    private JMenu templateMenu;
    private JMenu timeUnitMenu;

    public PanelChangeListener(MainWindow mainWindow,
                               ButtonTabStrategy eventButtonTabStrategy,
                               ButtonTabStrategy templateButtonTabStrategy,
                               ButtonTabStrategy categoryButtonTabStrategy,
                               ButtonTabStrategy timeUnitButtonTabStrategy,
                               TablePanel<Event> eventTablePanel,
                               TablePanel<Template> templateTablePanel,
                               TablePanel<Category> categoryTablePanel,
                               TablePanel<TimeUnit> timeUnitTablePanel) {
        this.mainWindow = mainWindow;
        this.eventButtonTabStrategy = eventButtonTabStrategy;
        this.templateButtonTabStrategy = templateButtonTabStrategy;
        this.categoryButtonTabStrategy = categoryButtonTabStrategy;
        this.timeUnitButtonTabStrategy = timeUnitButtonTabStrategy;
        this.eventTablePanel = eventTablePanel;
        this.templateTablePanel = templateTablePanel;
        this.categoryTablePanel = categoryTablePanel;
        this.timeUnitTablePanel = timeUnitTablePanel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        changeActions();
    }

    public void disableMenuBar() {
        eventMenu.setEnabled(false);
        categoryMenu.setEnabled(false);
        templateMenu.setEnabled(false);
        timeUnitMenu.setEnabled(false);
    }

    private void changeActions() {
        disableMenuBar();
        if (mainWindow.getTabbedPane().getSelectedComponent().equals(eventTablePanel)) {
            mainWindow.setButtonTabStrategy(eventButtonTabStrategy);
            eventMenu.setEnabled(true);
            eventTablePanel.getTable().clearSelection();
        } else if (mainWindow.getTabbedPane().getSelectedComponent().equals(templateTablePanel)) {
            mainWindow.setButtonTabStrategy(templateButtonTabStrategy);
            templateMenu.setEnabled(true);
            templateTablePanel.getTable().clearSelection();
        } else if (mainWindow.getTabbedPane().getSelectedComponent().equals(categoryTablePanel)) {
            mainWindow.setButtonTabStrategy(categoryButtonTabStrategy);
            categoryMenu.setEnabled(true);
            categoryTablePanel.getTable().clearSelection();
        } else if (mainWindow.getTabbedPane().getSelectedComponent().equals(timeUnitTablePanel)) {
            mainWindow.setButtonTabStrategy(timeUnitButtonTabStrategy);
            timeUnitMenu.setEnabled(true);
            timeUnitTablePanel.getTable().clearSelection();
        }
        mainWindow.applyButtonStrategy();
    }

    public void setMenus(JMenu eventMenu, JMenu categoryMenu, JMenu templateMenu, JMenu timeUnitMenu) {
        this.eventMenu = eventMenu;
        this.categoryMenu = categoryMenu;
        this.templateMenu = templateMenu;
        this.timeUnitMenu = timeUnitMenu;
    }
}
