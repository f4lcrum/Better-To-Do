package cz.fi.muni.pv168.bosv.better_todo.ui.renderer;


import cz.fi.muni.pv168.bosv.better_todo.entity.Status;

import javax.swing.*;

public final class StatusRenderer extends AbstractRenderer<Status> {

    public StatusRenderer() {
        super(Status.class);
    }

    @Override
    protected void updateLabel(JLabel label, Status status) {
        if (status != null) {
            label.setText(status.toString().replace("_", " "));
        }
    }
}

