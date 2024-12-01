package use_case.edit_profile;

import entity.User;

/**
 * DAO for the Signup Use Case.
 */
public interface EditProfileUserDataAccessInterface {

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Saves the user.
     * @param user the user to save
     */
    void saveUser(User user);

    User get(String username);

    void changePassword(User user);

    void changeFullname(User user);

    void changeImage(User user);

    void changeLocation(User user);

    void changeGender(User user);

    void changePreferredGender(User user);

    void changeDOB(User user);

    void changePreferredAge(User user);
}
