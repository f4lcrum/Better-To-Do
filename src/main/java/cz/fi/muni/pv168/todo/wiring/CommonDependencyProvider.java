package cz.fi.muni.pv168.todo.wiring;

import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutor;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutorImpl;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionManagerImpl;

/**
 * Common dependency provider for both production and test environment.
 * @author Vojtech Sassmann
 */
public class CommonDependencyProvider implements DependencyProvider {

    private final DatabaseManager databaseManager;
    private final TransactionExecutor transactionExecutor;

    public CommonDependencyProvider(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        var transactionManager = new TransactionManagerImpl(databaseManager);
        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);
    }
    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public TransactionExecutor getTransactionExecutor() {
        return transactionExecutor;
    }
}
