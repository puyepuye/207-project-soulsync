package entity;

import java.util.*;

/**
 * Factory for creating users.
 */
public interface UserFactory {
    /**
     * Creates a new User.
     * @param username the name of the new user
     * @param password the password of the new user
     * @param image the image of the new user
     * @param fullName the fullName of the new user
     * @param location the location where the new user is at
     * @param gender the gender of the new user
     * @param preferredGender the preferredGender of the new user
     * @param dateOfBirth the dateOfBirth of the new user
     * @param preferredAge the preferredAge of the new user
     * @param bio the biography of the new user
     * @param preferences the preferences of the new user
     * @param tags the tags of the new user
     * @param matched the matched person of the new user
     * @param swipedRight the other users that this user swipes right on
     * @param swipedLeft the other users that this user swipes left on
     * @param swipedRightOn the other users that has swiped right on this user
     * @return the new user
     */
    User create(String username,
                String password,
                String image,
                String fullName,
                String location,
                String gender,
                List<String> preferredGender,
                Date dateOfBirth,
                HashMap<String, Integer> preferredAge,
                String bio,
                Map<String, Boolean> preferences,
                List<String> tags,
                List<String> matched,
                ArrayList<String> swipedRight,
                ArrayList<String> swipedLeft,
                ArrayList<String> swipedRightOn);

    // Overloaded create method with only username and password
    default User create(String userName, String password) {
        return create(userName, password, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

}
