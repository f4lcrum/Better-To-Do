package cz.fi.muni.pv168.todo.business.error;

/**
 * Interface for exceptions with error message displayable to user
 * @author Vojtěch Sassmann
 */
public interface ApplicationException {

    /**
     * @return error message displayable to user
     */
    String getUserMessage();
}
