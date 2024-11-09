package entity;

import java.util.Date;
import java.util.List;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements UserFactory {

    @Override
    public User create(String email, String password, String fullName, String location, String gender, Date dateOfBirth, String bio, List<String> preferences, List<String> tags, List<String> matched, List<String> saved) {
        return new CommonUser(email, password, fullName, location, gender, dateOfBirth, bio, preferences, tags, matched, saved);
    }
}
