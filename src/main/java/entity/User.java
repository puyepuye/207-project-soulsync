package entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The representation of a user in our program.
 */
public interface User {

    /**
     * Returns the userName of the user.
     * @return the userName of the user.
     */
    String getUserName();

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    String getPassword();

    /**
     * Returns the image of the user.
     * @return the image of the user.
     */
    String getImage();

    /**
     * Returns the fullName of the user.
     * @return the fullName of the user.
     */
    String getFullName();

    /**
     * Returns the location of the user.
     * @return the location of the user.
     */
    String getLocation();

    /**
     * Returns the gender of the user.
     * @return the gender of the user.
     */
    String getGender();

    /**
     * Returns the preferredGender of the user.
     * @return the preferredGender of the user.
     */
    List<String> getPreferredGender();

    /**
     * Returns the dateOfBirth of the user.
     * @return the dateOfBirth of the user.
     */
    Date getDateOfBirth();

    /**
     * Returns the preferredAge of the user.
     * @return the preferredAge of the user.
     */
    Map<String, Integer> getPreferredAge();

    /**
     * Returns the bio of the user.
     * @return the bio of the user.
     */
    String getBio();

    /**
     * Returns the preferences of the user.
     * @return the preferences of the user.
     */
    Map<String, Boolean> getPreferences();

    /**
     * Returns the tags of the user.
     * @return the tags of the user.
     */
    List<String> getTags();

    /**
     * Returns the matched person of the user.
     * @return the matched person of the user.
     */
    List<String> getMatched();
}
