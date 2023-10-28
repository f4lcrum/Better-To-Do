package cz.fi.muni.pv168.bosv.better_todo.ui.renderer;


import cz.fi.muni.pv168.bosv.better_todo.entity.EventDuration;

import javax.swing.*;

public final class DurationRenderer extends AbstractRenderer<EventDuration> {

    public DurationRenderer() {
        super(EventDuration.class);
    }

    @Override
    protected void updateLabel(JLabel label, EventDuration duration) {
        if (duration != null) {
            label.setText(duration.toString());
        }
    }
}

