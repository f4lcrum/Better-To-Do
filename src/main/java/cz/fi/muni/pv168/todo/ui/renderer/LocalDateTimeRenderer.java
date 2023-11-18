package cz.fi.muni.pv168.todo.ui.renderer;

import lombok.NonNull;

import javax.swing.*;
import java.time.LocalDateTime;

public class LocalDateTimeRenderer extends AbstractRenderer<LocalDateTime> {
    public LocalDateTimeRenderer() {
        super(LocalDateTime.class);
    }

    @Override
    protected void updateLabel(@NonNull JLabel label,
                               @NonNull LocalDateTime value) {
        label.setText(String.format("%d. %d. %d %02d:%02d", value.getDayOfMonth(), value.getMonthValue(), value.getYear(), value.getHour(), value.getMinute()));
    }
}
