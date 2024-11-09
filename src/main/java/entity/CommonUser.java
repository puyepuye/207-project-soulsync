package entity;

import java.util.List;
import java.util.Date;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String email;
    private final String password;
    private final String fullName;
    private final String location;
    private final String gender;
    private final Date dateOfBirth;
    private final String bio;
    private final List<String> preferences;
    private final List<String> tags;
    private final List<String> matched;
    private final List<String> saved;

    public CommonUser(String email, String password, String fullName, String location, String gender, Date dateOfBirth, String bio, List<String> preferences, List<String> tags, List<String> matched, List<String> saved) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.location = location;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.preferences = preferences;
        this.tags = tags;
        this.matched = matched;
        this.saved = saved;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String getBio() {
        return bio;
    }

    @Override
    public List<String> getPreferences() {
        return preferences;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    @Override
    public List<String> getMatched() {
        return matched;
    }

    @Override
    public List<String> getSaved() {
        return saved;
    }
}
