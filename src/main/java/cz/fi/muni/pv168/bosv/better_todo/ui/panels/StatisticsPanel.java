package cz.fi.muni.pv168.bosv.better_todo.ui.panels;

import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class StatisticsPanel extends JPanel {

    public StatisticsPanel() {
        // TODO: pass data
        setLayout(new MigLayout("wrap 5"));
        setValues();
    }

    private void setValues() {
        setTitles();
        setContent();
    }

    private void setContent() {
        setRow("Total", 80, 80000, "Planned", 12);
        setRow("Filtered", 62, 62000, "In Progess", 4);
        setRow("Today", 6, 6000, "Done", 64);
    }

    private void setRow(String label, int count, int duration, String labelStatus, int countStatus) {
        add(new JLabel(label), "wmin 100lp, grow");
        add(new JLabel(String.format("%d", count)), "wmin 100lp, grow");
        add(new JLabel(String.format("%d", duration)), "wmin 100lp, grow");
        add(new JLabel(labelStatus), "wmin 100lp, grow");
        add(new JLabel(String.format("%d", countStatus)), "wmin 100lp, grow");
    }

    private void setTitles() {
        var labelCount = "Event count";
        var labelDuration = "Event duration";
        var labelStatus = "Event count by status";
        add(new JLabel(""), "wmin 100lp, grow");
        add(new JLabel(labelCount), "wmin 100lp, grow");
        add(new JLabel(labelDuration), "wmin 100lp, grow");
        add(new JLabel(labelStatus), "wmin 100lp, span 2, grow");
    }

}
