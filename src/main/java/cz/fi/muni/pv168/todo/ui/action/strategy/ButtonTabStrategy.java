package cz.fi.muni.pv168.todo.ui.action.strategy;

import javax.swing.AbstractAction;

public interface ButtonTabStrategy {

    AbstractAction getAddAction();

    AbstractAction getEditAction();

    AbstractAction getDeleteAction();

    Boolean statusFilterEnabled();

    Boolean durationFilterEnabled();

    Boolean categoryFilterEnabled();
}
