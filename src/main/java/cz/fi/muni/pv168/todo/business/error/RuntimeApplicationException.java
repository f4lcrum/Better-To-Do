package cz.fi.muni.pv168.todo.business.error;

import java.io.Serial;

/**
 * @author Vojtěch Sassmann
 */

public class RuntimeApplicationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 0L;

    public RuntimeApplicationException(String message) {
        this(message, message);
    }

    public RuntimeApplicationException(String userMessage, String message) {
        super(message);
    }

    public RuntimeApplicationException(String userMessage, String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimeApplicationException(String message, Throwable cause) {
        this(message, message, cause);
    }
}
