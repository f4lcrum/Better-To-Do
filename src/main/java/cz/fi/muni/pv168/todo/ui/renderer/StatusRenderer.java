package cz.fi.muni.pv168.todo.ui.renderer;


import cz.fi.muni.pv168.todo.business.entity.Status;

import javax.swing.JLabel;

public final class StatusRenderer extends AbstractRenderer<Status> {

    public StatusRenderer() {
        super(Status.class);
    }

    @Override
    protected void updateLabel(JLabel label, Status status) {
        if (status != null) {
            var txt = status.toString().replace("_", " ");
            label.setText(txt.substring(0, 1).toUpperCase() + txt.substring(1).toLowerCase());
        }
    }
}

