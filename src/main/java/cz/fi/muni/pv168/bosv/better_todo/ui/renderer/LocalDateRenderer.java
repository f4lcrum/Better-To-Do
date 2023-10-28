package cz.fi.muni.pv168.bosv.better_todo.ui.renderer;

import lombok.NonNull;

import javax.swing.*;
import java.time.LocalDate;

public class LocalDateRenderer extends AbstractRenderer<LocalDate> {
    public LocalDateRenderer() {
        super(LocalDate.class);
    }

    @Override
    protected void updateLabel(@NonNull JLabel label,
                               @NonNull LocalDate value) {
        label.setText(String.format("%s. %s. %s", value.getDayOfMonth(), value.getMonthValue(), value.getYear()));
    }
}
