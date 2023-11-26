package cz.fi.muni.pv168.todo.data;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Status;
import static cz.fi.muni.pv168.todo.business.entity.Status.DONE;
import static cz.fi.muni.pv168.todo.business.entity.Status.IN_PROGRESS;
import static cz.fi.muni.pv168.todo.business.entity.Status.PLANNED;
import cz.fi.muni.pv168.todo.business.entity.Template;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

public final class TestDataGenerator {

    private static final Random RAND_GEN = new Random();

    private static final List<String> EVENT_NAMES = List.of("Meeting", "Exam", "Doctor", "Walk the dog", "Cook", "Clean the dishes", "Bank", "Party", "Clean shoes");

    private static final List<String> DESCRIPTION = List.of("Dress-code", "Buy groceries beforehand");

    private static final List<Category> CATEGORIES = List.of(
            new Category(UUID.randomUUID(), "Work", Color.GREEN),
            new Category(UUID.randomUUID(), "Chores", Color.BLUE),
            new Category(UUID.randomUUID(), "School", Color.RED),
            new Category(UUID.randomUUID(), "Social events", Color.PINK)
    );

    private static final List<Status> STATUSES = List.of(PLANNED, IN_PROGRESS, DONE);

    private static final List<String> CATEGORY_NAMES = List.of("Social", "Chores", "Shopping", "Work", "School");

    private static final List<Color> CATEGORY_COLORS = List.of(Color.BLUE, Color.RED, Color.GREEN, Color.PINK);

    private final Random random = new Random(2L);

    public Event createTestEvent() {
        String name = selectRandom(EVENT_NAMES);
        String description = selectRandom(DESCRIPTION);
        Status status = selectRandom(STATUSES);
        Category category = selectRandom(CATEGORIES);
        LocalDate eventStart = createRandomDate();
        LocalDateTime startDateTime = eventStart.atTime(RAND_GEN.nextInt(23), RAND_GEN.nextInt(59));
        LocalDateTime endDateTime = startDateTime.plusMinutes(RAND_GEN.nextInt(120));
        return new Event(UUID.randomUUID(), name, status, category, eventStart, startDateTime.toLocalTime(), endDateTime.toLocalTime(), description);
    }

    public LocalDate createRandomDate() {
        return LocalDate.of(RAND_GEN.nextInt(1900, 2024), RAND_GEN.nextInt(1, 12), RAND_GEN.nextInt(1, 28));
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
        LocalDate eventStart = createRandomDate();
        LocalDateTime startDateTime = eventStart.atTime(RAND_GEN.nextInt(23), RAND_GEN.nextInt(59));
        LocalDateTime endDateTime = startDateTime.plusMinutes(RAND_GEN.nextInt(120));
        return new Template(UUID.randomUUID(), name, description, category, startDateTime.toLocalTime(), endDateTime.toLocalTime());
    }

    public Category createTestCategory() {
        Color color = selectRandom(CATEGORY_COLORS);
        String name = selectRandom(CATEGORY_NAMES);
        return new Category(UUID.randomUUID(), name, color);
    }

    public List<Category> createTestCategories(int count) {
        return Stream
                .generate(this::createTestCategory)
                .limit(count)
                .toList();
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
}
