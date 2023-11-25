package cz.fi.muni.pv168.todo.storage.sql.dao;

/**
 * Exception that is thrown if there is some problem with data storage
 @author Vojtěch Sassmann
 */
import cz.fi.muni.pv168.todo.business.error.RuntimeApplicationException;

import java.io.Serial;

public class DataStorageException extends RuntimeApplicationException {

    @Serial
    private static final long serialVersionUID = 0L;

    public DataStorageException(String message) {
        this(message, null);
    }

    public DataStorageException(String message, Throwable cause) {
        this("Problem while interacting with database" , message, cause);
    }

    public DataStorageException(String userMessage, String message, Throwable cause) {
        super(userMessage, "Storage error: " +  message, cause);
    }
}

