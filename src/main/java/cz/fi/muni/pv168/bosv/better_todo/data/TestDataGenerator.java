package cz.fi.muni.pv168.bosv.better_todo.data;

import cz.fi.muni.pv168.bosv.better_todo.Entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.Entity.CategoryColour;
import cz.fi.muni.pv168.bosv.better_todo.Entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.Entity.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import static cz.fi.muni.pv168.bosv.better_todo.Entity.CategoryColour.GREEN;
import static cz.fi.muni.pv168.bosv.better_todo.Entity.CategoryColour.BLUE;
import static cz.fi.muni.pv168.bosv.better_todo.Entity.CategoryColour.RED;
import static cz.fi.muni.pv168.bosv.better_todo.Entity.CategoryColour.PINK;

import static cz.fi.muni.pv168.bosv.better_todo.Entity.Status.DONE;
import static cz.fi.muni.pv168.bosv.better_todo.Entity.Status.IN_PROGRESS;
import static cz.fi.muni.pv168.bosv.better_todo.Entity.Status.PLANNED;

import static java.time.Month.SEPTEMBER;
import static java.time.Month.OCTOBER;
import static java.time.temporal.ChronoUnit.DAYS;

public final class TestDataGenerator {

    private static final List<String> EVENT_NAMES = List.of("Meeting", "Exam", "Doctor", "Walk the dog", "Cook", "Clean the dishes", "Bank", "Party", "Clean shoes");

    private static final List<String> DESCRIPTION = List.of("", "Dress-code", "Buy groceries beforehand", "", "", "", "", "", "");

    private static final List<Category> CATEGORIES = List.of(
            new Category("Work", GREEN),
            new Category("Chores", BLUE),
            new Category("School", RED),
            new Category("Social events", PINK)
    );

    private static final List<Status> STATUSES = List.of(PLANNED, IN_PROGRESS, DONE);


    private static final LocalDateTime EVENT_START = LocalDate.of(2023, SEPTEMBER, 28).atStartOfDay();

    private static final LocalDateTime EVENT_END = LocalDate.of(2023, SEPTEMBER, 29).atStartOfDay();


    private final Random random = new Random(2L);

    public Event createTestEvent() {
        String name = selectRandom(EVENT_NAMES);
        String description = selectRandom(DESCRIPTION);
        Status status = selectRandom(STATUSES);
        Category category = selectRandom(CATEGORIES);
        return new Event(name, status, category, EVENT_START, EVENT_END, description);
    }

    public List<Event> createTestEvents(int count) {
        return Stream
                .generate(this::createTestEvent)
                .limit(count)
                .toList();
    }

    public List<Category> getCategories() {
        return CATEGORIES;
    }

    private <T> T selectRandom(List<T> data) {
        int index = random.nextInt(data.size());
        return data.get(index);
    }

    private LocalDate selectRandomLocalDate(LocalDate min, LocalDate max) {
        int maxDays = Math.toIntExact(DAYS.between(min, max) + 1);
        int days = random.nextInt(maxDays);
        return min.plusDays(days);
    }
}
