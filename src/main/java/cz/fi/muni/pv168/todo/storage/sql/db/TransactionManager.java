package cz.fi.muni.pv168.todo.storage.sql.db;

/**
 * Manages transactions
 * @author Vojtěch Sassmann
 */
public interface TransactionManager {

    /**
     * Begins new transaction
     */
    Transaction beginTransaction();

    /**
     * @return active {@link ConnectionHandler} instance
     */
    ConnectionHandler getConnectionHandler();

    /**
     * @return true if transaction is active, false otherwise
     */
    boolean hasActiveTransaction();
}

