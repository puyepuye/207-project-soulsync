package use_case.swipe;

import entity.User;

/**
 * The interface of the DAO for the Swipe Use Case.
 */
public interface SwipeUserDataAccessInterface {
    /**
     * Updates the system to record this user's password.
     * @param user the user whose liked collection is being updated
     * @param userSwipedOn the user who was liked/not
     * @param like whether liked/not
     */
    void updateLike(User user, User userSwipedOn, boolean like);

    /**
     * Get the user.
     *
     * @param username the user to get
     * @return User
     */
    User get(String username);

    void saveMatch(String profileUsername, String currentSwipingUsername);
}
