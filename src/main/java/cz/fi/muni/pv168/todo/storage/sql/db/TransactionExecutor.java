package cz.fi.muni.pv168.todo.storage.sql.db;

/**
 * This component can be used to execute code in a transaction.
 *
 * <p>
 * Example usage:
 * <br>
 * transactionExecutor.executeInTransaction(() -> atomicAction());
 * </p>
 *
 * @author Vojtech Sassmann
 */
public interface TransactionExecutor {

    /**
     * Executes runnable in transaction
     *
     * @param operation operation which will be executed in a transaction
     */
    void executeInTransaction(Runnable operation);
}

