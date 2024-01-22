package cz.fi.muni.pv168.todo.storage.sql.db;


import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Transaction handler
 *
 * @author Peter Stanko
 */
public class TransactionImpl implements Transaction {

    private final ConnectionHandler connectionHandler;
    private boolean closed = false;

    /**
     * Creates new transaction over given connection
     *
     * @param connection database connection
     */
    public TransactionImpl(Connection connection) throws SQLException {
        Objects.requireNonNull(connection, "Missing connection object");
        connection.setAutoCommit(false);
        this.connectionHandler = new ConnectionHandlerImpl(connection);
    }

    @Override
    public ConnectionHandler connection() {
        return connectionHandler;
    }

    @Override
    public void commit() {
        try {
            connectionHandler.use().commit();
        } catch (SQLException e) {
            throw new DataStorageException("Unable to commit transaction", e);
        }
    }

    @Override
    public void close() {
        try {
            connectionHandler.use().close();
            closed = true;
        } catch (SQLException e) {
            throw new DataStorageException("Unable close database connection", e);
        }
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
