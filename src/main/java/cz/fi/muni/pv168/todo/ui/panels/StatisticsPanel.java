package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Status;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class StatisticsPanel extends JPanel {
    CrudService<Event> eventCrudService;
    JPanel leftPanel;
    JPanel rightPanel;
    JLabel totalEventDuration = new JLabel("0");
    JLabel totalEventCount = new JLabel("0");
    JLabel plannedCount = new JLabel("0");
    JLabel filteredEventCount = new JLabel("0");
    JLabel filteredEventDuration = new JLabel("0");
    JLabel inProgressCount = new JLabel("0");
    JLabel todayEventCount = new JLabel("0");
    JLabel todayEventDuration = new JLabel("0");
    JLabel doneCount = new JLabel("0");
    public StatisticsPanel(CrudService<Event> eventCrudService) {
        setLayout(new BorderLayout());
        this.leftPanel = new JPanel(new MigLayout("wrap 3"));
        this.rightPanel = new JPanel(new MigLayout("wrap 2"));
        this.eventCrudService = eventCrudService;
        add(this.leftPanel, BorderLayout.WEST);
        add(this.rightPanel, BorderLayout.EAST);
        setValues();
    }

    private void setValues() {
        setTitles();
        setContent();
        setRow("Total", this.totalEventCount, this.totalEventDuration, "Planned", this.plannedCount);
        setRow("Filtered", this.filteredEventCount, this.filteredEventDuration, "In Progress", this.inProgressCount);
        setRow("Today", this.todayEventCount, this.todayEventDuration, "Done", this.doneCount);
    }

    private long getEventListDuration(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return 0;
        }

        return events.stream().mapToLong(Event::getEventDuration).sum();
    }

    private Map<Status, List<Event>> sortByStatus(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return Collections.emptyMap();
        }

        return events.stream().collect(Collectors.groupingBy(Event::getStatus));
    }

    private List<Event> findEventsToday(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return Collections.emptyList();
        }
        LocalDate today = LocalDate.now();

        return events.stream().filter(event -> event.getDate().equals(today)).collect(Collectors.toList());
    }


    private void setContent() {
        List<Event> events = this.eventCrudService.findAll();
        int totalCount = events.size();
        long totalDuration = getEventListDuration(events);

        List<Event> todayEvents = findEventsToday(events);
        int todayCount = todayEvents.size();
        long todayDuration = getEventListDuration(todayEvents);

        int filteredCount = 0;
        int filteredDuration = 6200;

        Map<Status, List<Event>> sortedByStatus = sortByStatus(events);

        long plannedCount = getEventListDuration(sortedByStatus.get(Status.PLANNED));
        long doneCount = getEventListDuration(sortedByStatus.get(Status.DONE));
        long inProgressCount = getEventListDuration(sortedByStatus.get(Status.IN_PROGRESS));
        this.totalEventCount.setText(String.valueOf(totalCount));
        this.totalEventDuration.setText(String.valueOf(totalDuration));
        this.plannedCount.setText(String.valueOf(plannedCount));
        this.filteredEventCount.setText(String.valueOf(filteredCount));
        this.filteredEventDuration.setText(String.valueOf(filteredDuration));
        this.inProgressCount.setText(String.valueOf(inProgressCount));
        this.todayEventCount.setText(String.valueOf(todayCount));
        this.todayEventDuration.setText(String.valueOf(todayDuration));
        this.doneCount.setText(String.valueOf(doneCount));
    }

    private void setRow(String label, JLabel count, JLabel duration, String labelStatus, JLabel countStatus) {
        this.leftPanel.add(new JLabel(label), "wmin 100lp, grow");
        this.leftPanel.add(count, "wmin 100lp, grow");
        this.leftPanel.add(duration, "wmin 100lp, grow");
        this.rightPanel.add(new JLabel(labelStatus), "wmin 75lp, grow");
        this.rightPanel.add(countStatus, "wmin 25lp, grow");
    }

    private void setTitles() {
        var labelCount = "Event count";
        var labelDuration = "Event duration";
        var labelStatus = "Event count by status";
        this.leftPanel.add(new JLabel(""), "wmin 100lp, grow");
        this.leftPanel.add(new JLabel(labelCount), "wmin 100lp, grow");
        this.leftPanel.add(new JLabel(labelDuration), "wmin 100lp, grow");
        this.rightPanel.add(new JLabel(labelStatus), "wmin 100lp, span 2, grow");
    }

    public void refresh() {
        setContent();
    }
}
