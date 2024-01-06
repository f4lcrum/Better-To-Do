package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.repository.CategoryRepository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.wiring.TestingDependencyProvider;
import java.awt.Color;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

final class CategorySqlRepositoryIntegrationTest {
    private DatabaseManager databaseManager;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        databaseManager = DatabaseManager.createTestInstance();
        var dependencyProvider = new TestingDependencyProvider(databaseManager);
        categoryRepository = dependencyProvider.getCategoryRepository();
    }

    @AfterEach
    void tearDown() {
        databaseManager.destroySchema();
    }

    @Test
    void categoryInitSucceeds() {
        Set<String> expectedCategoriesNames = new HashSet<>(Set.of("Work", "Family", "Personal"));
        final Collection<Category> retrievedCategories;

        retrievedCategories = categoryRepository.findAll();

        retrievedCategories.forEach(timeUnit -> expectedCategoriesNames.remove(timeUnit.getName()));

        // Expecting 4 initial time units - Minute, Hour, Day, Week
        assertEquals(3, retrievedCategories.size());
        assertEquals(0, expectedCategoriesNames.size());
    }

    @Test
    void createOneCategorySucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestCategory", Color.PINK);
        final Optional<Category> retrievedCategory;

        categoryRepository.create(newCategory);

        retrievedCategory = assertDoesNotThrow(() -> categoryRepository.findByGuid(newCategory.getGuid()));
        assertTrue(retrievedCategory.isPresent());
        assertEquals(newCategory, retrievedCategory.get());
    }

    @Test
    void updateOfInsertedCategorySucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestCategory", Color.PINK);
        final Category updatedCategory = new Category(newCategory.getGuid(), "UpdatedCategory", Color.BLUE);
        final Optional<Category> updateResult;

        categoryRepository.create(newCategory);
        assertDoesNotThrow(() -> categoryRepository.update(updatedCategory));
        updateResult = assertDoesNotThrow(() -> categoryRepository.findByGuid(newCategory.getGuid()));

        assertTrue(updateResult.isPresent());
        assertEquals(updatedCategory, updateResult.get());
    }

    @Test
    void deleteByGuidOnExistingCategorySucceeds() {
        final Category newCategory = new Category(UUID.randomUUID(), "TestCategory", Color.BLACK);

        categoryRepository.create(newCategory);

        assertDoesNotThrow(() -> categoryRepository.existsByGuid(newCategory.getGuid()));
        assertDoesNotThrow(() -> categoryRepository.deleteByGuid(newCategory.getGuid()));

        assertThrows(DataStorageException.class, () -> categoryRepository.deleteByGuid(newCategory.getGuid()));
    }

    @Test
    void deleteAllSucceeds() {
        final Category newCategory1 = new Category(UUID.randomUUID(), "First", Color.BLACK);
        final Category newCategory2 = new Category(UUID.randomUUID(), "Second", Color.GRAY);
        final Category newCategory3 = new Category(UUID.randomUUID(), "Third", Color.WHITE);
        final Collection<Category> categories;

        categoryRepository.create(newCategory1);
        categoryRepository.create(newCategory2);
        categoryRepository.create(newCategory3);
        categories = categoryRepository.findAll();
        assertDoesNotThrow(() -> categoryRepository.deleteAll());

        assertEquals(3 + 3, categories.size()); // Desired time units were added
        assertEquals(0, categoryRepository.findAll().size()); // All time units were removed
    }

    @Test
    void existsByGuidOnExistingCategorySucceeds() {
        final Category category = new Category(UUID.randomUUID(), "Money", Color.GREEN);
        final boolean existsCategory;

        categoryRepository.create(category);
        existsCategory = categoryRepository.existsByGuid(category.getGuid());

        assertDoesNotThrow(() -> categoryRepository.findByGuid(category.getGuid())); // Time unit actually exists
        assertTrue(existsCategory); // existsByGuid returns correct value
    }

    @Test
    void existsByGuidOnNonExistingCategoryFails() {
        final UUID randomUUID = UUID.fromString("d94ee5ef-0325-4ebe-a895-fa380c968f49");
        final boolean existsResult;

        existsResult = assertDoesNotThrow(() -> categoryRepository.existsByGuid(randomUUID));

        assertFalse(existsResult);
    }

    @Test
    void findByGuidOnExistingCategorySucceeds() {
        final Category category = new Category(UUID.randomUUID(), "Money", Color.GREEN);
        final Optional<Category> retrievedCategory;

        categoryRepository.create(category);
        retrievedCategory = assertDoesNotThrow(() -> categoryRepository.findByGuid(category.getGuid()));

        assertTrue(retrievedCategory.isPresent());
        assertEquals(category, retrievedCategory.get());
    }
}
