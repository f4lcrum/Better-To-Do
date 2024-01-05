package cz.fi.muni.pv168.todo.storage.sql;//package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.CategoryRepository;
import cz.fi.muni.pv168.todo.business.repository.TemplateRepository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.wiring.TestingDependencyProvider;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

final class TemplateSqlRepositoryIntegrationTest {
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
    void templateInitSucceeds() {
    }

    @Test
    void createOneTemplateSucceeds() {
    }

    @Test
    void updateOfInsertedTemplateSucceeds() {
        // Add code to test updating a Template entity
    }

    @Test
    void deleteByGuidOnExistingTemplateSucceeds() {
        // Add code to test deletion of a Template entity
    }

    @Test
    void deleteAllSucceeds() {
        // Add code to test deletion of all Template entities
    }

    @Test
    void existsByGuidOnExistingTemplateSucceeds() {
        // Add code to test existence check for a Template entity
    }

    @Test
    void existsByGuidOnNonExistingTemplateFails() {
        // Add code to test non-existence check for a Template entity
    }

    @Test
    void findByGuidOnExistingTemplateSucceeds() {
        // Add code to test finding a specific Template entity by GUID
    }
}
