package cz.fi.muni.pv168.todo.io.exception;

import cz.fi.muni.pv168.todo.business.error.RuntimeApplicationException;

import java.io.Serial;

public class SnapshotIOException extends RuntimeApplicationException {

    @Serial
    private static final long serialVersionUID = 0L;

    public SnapshotIOException(String message, Throwable cause) {
        this("Problem while performing import/export: ", message, cause);
    }

    public SnapshotIOException(String userMessage, String message, Throwable cause) {
        super(userMessage, "Problem while performing import/export: " + message, cause);
    }
}
