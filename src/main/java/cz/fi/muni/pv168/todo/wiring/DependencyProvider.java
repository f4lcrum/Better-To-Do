package cz.fi.muni.pv168.todo.wiring;

import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutor;

public interface DependencyProvider {
    DatabaseManager getDatabaseManager();


    TransactionExecutor getTransactionExecutor();

}
