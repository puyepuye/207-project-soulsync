package entity;

import java.util.Date;
import java.util.List;

/**
 * Factory for creating users.
 */
public interface UserFactory {
    /**
     * Creates a new User.
     * @param fullName the name of the new user
     * @param password the password of the new user
     * @return the new user
     */
    User create(String email, String password, String fullName,
                String location, String gender, Date dateOfBirth,
                String bio, List<String> preferences, List<String> tags,
                List<String> matched, List<String> saved);

}
