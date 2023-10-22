package cz.fi.muni.pv168.bosv.better_todo.ui.panels;

import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
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
        // TO-DO: give data and handle size when changing to this panel
        setLayout(new GridLayout(2, 1));
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2));
        add(topPanel);
        topPanel.add(createCountBarChart(80, 65, 5));
        topPanel.add(createDurationBarChart(4800, 3900, 900));
        add(createPieChart(65, 5, 10));
    }

    @SuppressWarnings("unchecked")
    private JPanel createPieChart(int planned, int inProgress, int done) {
        DefaultPieDataset<Status> data = new DefaultPieDataset<>();
        data.setValue(Status.PLANNED, planned);
        data.setValue(Status.IN_PROGRESS, inProgress);
        data.setValue(Status.DONE, done);
        JFreeChart pieChart = ChartFactory.createPieChart("Events by status", data, false, false, false);
        PiePlot<Status> plot = (PiePlot<Status>) pieChart.getPlot();
        plot.setSimpleLabels(true);
        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                "{0}: {1}", new DecimalFormat("0"), new DecimalFormat("0%"));
        plot.setLabelGenerator(gen);
        plot.setSectionPaint(Status.PLANNED, Color.CYAN);
        plot.setSectionPaint(Status.IN_PROGRESS, Color.GREEN);
        plot.setSectionPaint(Status.DONE, Color.RED);
        return new ChartPanel(pieChart);
    }

    private JPanel createCountBarChart(int total, int filtered, int today) {
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        data.setValue(total, "Count", "Total Events");
        data.setValue(filtered, "Count", "Filtered Events");
        data.setValue(today, "Count", "Today Events");

        JFreeChart countBarChart = ChartFactory.createBarChart("Event Counts", "", "Count", data, PlotOrientation.VERTICAL, false, false, false);
        CategoryPlot plot = countBarChart.getCategoryPlot();

        StackedBarRenderer renderer = new StackedBarRenderer(false);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        plot.setRenderer(renderer);

        return new ChartPanel(countBarChart);
    }

    private JPanel createDurationBarChart(int total, int filtered, int today) {
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        data.setValue(total, "Duration", "Total Events");
        data.setValue(filtered, "Duration", "Filtered Events");
        data.setValue(today, "Duration", "Today Events");

        JFreeChart durationBarChart = ChartFactory.createBarChart("Event Durations", "", "Duration", data, PlotOrientation.VERTICAL, false, false, false);
        CategoryPlot plot = durationBarChart.getCategoryPlot();

        StackedBarRenderer renderer = new StackedBarRenderer(false);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        plot.setRenderer(renderer);

        return new ChartPanel(durationBarChart);
    }
}
