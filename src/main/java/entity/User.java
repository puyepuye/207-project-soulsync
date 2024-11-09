package entity;

import java.util.Date;
import java.util.List;

/**
 * The representation of a user in our program.
 */
public interface User {

    String getEmail();

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    String getPassword();

    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    String getFullName();

    String getLocation();

    String getGender();

    Date getDateOfBirth();

    String getBio();

    List<String> getPreferences();

    List<String> getTags();

    List<String> getMatched();

    List<String> getSaved();
}
