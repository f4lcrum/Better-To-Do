package cz.muni.fi.pv168.employees.business.error;

/**
 * Interface for exceptions with error message displayable to user
 */
public interface ApplicationException {

    /**
     * @return error message displayable to user
     */
    String getUserMessage();
}
