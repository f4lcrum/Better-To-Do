package cz.fi.muni.pv168.todo.ui.renderer;

import javax.swing.JLabel;
import java.time.LocalDateTime;

public class LocalDateTimeRenderer extends AbstractRenderer<LocalDateTime> {
    public LocalDateTimeRenderer() {
        super(LocalDateTime.class);
    }

    @Override
    protected void updateLabel( JLabel label,
                                LocalDateTime value) {
        label.setText(String.format("%d. %d. %d %02d:%02d", value.getDayOfMonth(), value.getMonthValue(), value.getYear(), value.getHour(), value.getMinute()));
    }
}
