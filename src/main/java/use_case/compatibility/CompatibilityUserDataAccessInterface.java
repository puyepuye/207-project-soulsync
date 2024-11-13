package use_case.compatibility;

import entity.User;

/**
 * DAO for the Compatibility Use Case.
 */
public interface CompatibilityUserDataAccessInterface {
    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Get the user.
     *
     * @param username the user to get
     * @return
     */
    User get(String username);

    /**
     * Returns the current session user.
     * @return the current user
     */
    String getCurrentUser();
}
