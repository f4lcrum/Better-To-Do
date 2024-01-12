package cz.fi.muni.pv168.todo.ui.listener;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.action.strategy.CategoryButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.EventButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.TemplateButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.action.strategy.TimeUnitButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.panels.BasePanel;
import cz.fi.muni.pv168.todo.ui.panels.CategoryTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.TemplateTablePanel;
import cz.fi.muni.pv168.todo.ui.panels.TimeUnitTablePanel;

import javax.swing.JMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PanelChangeListener implements ChangeListener {

    private final MainWindow mainWindow;
    private final EventButtonTabStrategy eventButtonTabStrategy;
    private final TemplateButtonTabStrategy templateButtonTabStrategy;
    private final CategoryButtonTabStrategy categoryButtonTabStrategy;
    private final TimeUnitButtonTabStrategy timeUnitButtonTabStrategy;
    private final BasePanel<Event> eventTablePanel;
    private final BasePanel<Template> templateTablePanel;
    private final BasePanel<Category> categoryTablePanel;
    private final BasePanel<TimeUnit> timeUnitTablePanel;
    private JMenu eventMenu;
    private JMenu categoryMenu;
    private JMenu templateMenu;
    private JMenu timeUnitMenu;

    public PanelChangeListener(MainWindow mainWindow,
                               EventButtonTabStrategy eventButtonTabStrategy,
                               TemplateButtonTabStrategy templateButtonTabStrategy,
                               CategoryButtonTabStrategy categoryButtonTabStrategy,
                               TimeUnitButtonTabStrategy timeUnitButtonTabStrategy,
                               EventTablePanel eventTablePanel,
                               TemplateTablePanel templateTablePanel,
                               CategoryTablePanel categoryTablePanel,
                               TimeUnitTablePanel timeUnitTablePanel) {
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
