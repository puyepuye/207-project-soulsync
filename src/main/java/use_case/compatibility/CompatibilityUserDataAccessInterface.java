package use_case.compatibility;

import entity.User;

/**
 * The interface of the DAO for the Change Password Use Case.
 */
public interface CompatibilityUserDataAccessInterface {

    /**
     * Updates the system to record this user's password.
     * @param user the user whose password is to be updated
     */
    void compatibilityMatch(User user);
}
