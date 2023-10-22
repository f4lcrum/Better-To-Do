package cz.fi.muni.pv168.bosv.better_todo.data;

import cz.fi.muni.pv168.bosv.better_todo.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static cz.fi.muni.pv168.bosv.better_todo.entity.CategoryColour.GREEN;
import static cz.fi.muni.pv168.bosv.better_todo.entity.CategoryColour.BLUE;
import static cz.fi.muni.pv168.bosv.better_todo.entity.CategoryColour.RED;
import static cz.fi.muni.pv168.bosv.better_todo.entity.CategoryColour.PINK;

import static cz.fi.muni.pv168.bosv.better_todo.entity.Status.DONE;
import static cz.fi.muni.pv168.bosv.better_todo.entity.Status.IN_PROGRESS;
import static cz.fi.muni.pv168.bosv.better_todo.entity.Status.PLANNED;

import static java.time.Month.SEPTEMBER;
import static java.time.temporal.ChronoUnit.DAYS;

public final class TestDataGenerator {
    private static final Random RAND_GEN = new Random();

    private static final User LUCY = new User(UUID.randomUUID(), "Lucy", String.format("%d", RAND_GEN.nextInt()));

    private static final List<String> EVENT_NAMES = List.of("Meeting", "Exam", "Doctor", "Walk the dog", "Cook", "Clean the dishes", "Bank", "Party", "Clean shoes");

    private static final List<String> DESCRIPTION = List.of("Dress-code", "Buy groceries beforehand");

    private static final List<Category> CATEGORIES = List.of(
            new Category(UUID.randomUUID(), "Work", GREEN),
            new Category(UUID.randomUUID(), "Chores", BLUE),
            new Category(UUID.randomUUID(), "School", RED),
            new Category(UUID.randomUUID(), "Social events", PINK)
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
        return new Event(UUID.randomUUID(), LUCY.getId(), name, status, category, EVENT_START.toLocalDate(), EVENT_START.toLocalTime(), EVENT_END.toLocalTime(), description);
    }

    public List<Event> createTestEvents(int count) {
        return Stream
                .generate(this::createTestEvent)
                .limit(count)
                .toList();
    }

    public Template createTestTemplate() {
        String name = selectRandom(EVENT_NAMES);
        String description = selectRandom(DESCRIPTION);
        Category category = selectRandom(CATEGORIES);
        return new Template(UUID.randomUUID(), LUCY.getId(), name, description, category, EVENT_START.toLocalTime(), EVENT_END.toLocalTime());
    }

    public List<Template> createTestTemplates(int count) {
        return Stream
                .generate(this::createTestTemplate)
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
