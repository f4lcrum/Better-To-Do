package cz.fi.muni.pv168.todo.ui.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
<<<<<<< Updated upstream:src/main/java/cz/fi/muni/pv168/todo/ui/panels/StatisticsPanel.java
=======
import java.awt.BorderLayout;
>>>>>>> Stashed changes:src/main/java/cz/fi/muni/pv168/bosv/better_todo/ui/panels/StatisticsPanel.java

public class StatisticsPanel extends JPanel {

    public StatisticsPanel() {
        // TODO: pass data
        setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel(new MigLayout("wrap 3"));
        JPanel rightPanel = new JPanel(new MigLayout("wrap 2"));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        setValues(leftPanel, rightPanel);
    }

    private void setValues(JPanel left, JPanel right) {
        setTitles(left, right);
        setContent(left, right);
    }

    private void setContent(JPanel left, JPanel right) {
        setRow(left, right, "Total", 80, 80000, "Planned", 12);
        setRow(left, right, "Filtered", 62, 62000, "In Progess", 4);
        setRow(left, right, "Today", 6, 6000, "Done", 64);
    }

    private void setRow(JPanel left, JPanel right, String label, int count, int duration, String labelStatus, int countStatus) {
        left.add(new JLabel(label), "wmin 100lp, grow");
        left.add(new JLabel(String.format("%d", count)), "wmin 100lp, grow");
        left.add(new JLabel(String.format("%d", duration)), "wmin 100lp, grow");
        right.add(new JLabel(labelStatus), "wmin 75lp, grow");
        right.add(new JLabel(String.format("%d", countStatus)), "wmin 25lp, grow");
    }

    private void setTitles(JPanel left, JPanel right) {
        var labelCount = "Event count";
        var labelDuration = "Event duration";
        var labelStatus = "Event count by status";
        left.add(new JLabel(""), "wmin 100lp, grow");
        left.add(new JLabel(labelCount), "wmin 100lp, grow");
        left.add(new JLabel(labelDuration), "wmin 100lp, grow");
        right.add(new JLabel(labelStatus), "wmin 100lp, span 2, grow");
    }

}
