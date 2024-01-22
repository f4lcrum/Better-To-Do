package cz.fi.muni.pv168.todo.storage.sql.db;

import java.io.Closeable;

/**
 * Transaction Handling
 *
 * @author Vojtěch Sassmann
 */
public interface Transaction extends Closeable {

    /**
     * @return active {@link ConnectionHandler} instance
     */
    ConnectionHandler connection();

    /**
     * Commits active transaction
     */
    void commit();

    /**
     * Closes active connection
     */
    void close();

    /**
     * Returns true if connection is closed
     */
    boolean isClosed();
}
