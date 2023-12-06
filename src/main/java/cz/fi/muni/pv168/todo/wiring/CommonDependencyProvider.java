package cz.fi.muni.pv168.todo.wiring;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutor;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutorImpl;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionManagerImpl;
import cz.fi.muni.pv168.todo.business.service.validation.CategoryValidator;
/**
 * Common dependency provider for both production and test environment.
 * @author Vojtech Sassmann
 */
public class CommonDependencyProvider implements DependencyProvider {

    private final DatabaseManager databaseManager;
    private final TransactionExecutor transactionExecutor;
    private final CategoryValidator categoryValidator;

    public CommonDependencyProvider(DatabaseManager databaseManager) {
        categoryValidator = new CategoryValidator();
        this.databaseManager = databaseManager;
        var transactionManager = new TransactionManagerImpl(databaseManager);
        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);
    }
    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public Validator<Category> getCategoryValidator() {
        return categoryValidator;
    }

    @Override
    public TransactionExecutor getTransactionExecutor() {
        return transactionExecutor;
    }
}
