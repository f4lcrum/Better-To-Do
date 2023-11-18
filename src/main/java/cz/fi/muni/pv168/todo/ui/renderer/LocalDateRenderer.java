package cz.fi.muni.pv168.todo.ui.renderer;

import javax.swing.JLabel;
import java.time.LocalDate;

public class LocalDateRenderer extends AbstractRenderer<LocalDate> {
    public LocalDateRenderer() {
        super(LocalDate.class);
    }

    @Override
    protected void updateLabel( JLabel label,
                                LocalDate value) {
        label.setText(String.format("%s. %s. %s", value.getDayOfMonth(), value.getMonthValue(), value.getYear()));
    }
}
