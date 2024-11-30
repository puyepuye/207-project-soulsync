package interface_adapter.preferences;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The state for the Preference View Model.
 */
public class PreferencesState {
    private String username = "";
    private String usernameError = "";
    private String password = "";
    private String repeatPassword = "";
    private String image = "";
    private String location = "";
    private String gender = "";
    private Date dateOfBirth;
    private String fullName;
    private List<String> tags;
    private String tagsError;
    private String bio = "";
    private String bioError;
    private Map<String, Boolean> preferences;
    private String preferencesError;
    private List<String> preferredGender;
    private String preferredGenderError;
    private HashMap<String, Integer> preferredAge;
    private String preferredAgeError;

    public String getFullname() { return fullName; }
    public String getUsername() { return username; }
    public String getUsernameError() { return usernameError; }
    public String getPassword() { return password; }
    public String getRepeatPassword() { return repeatPassword; }
    public String getImage() { return image; }
    public String getLocation() { return location; }
    public String getGender() { return gender; }
    public Date getDateOfBirth() { return dateOfBirth; }

    public List<String> getTags() {
        return tags;
    }

    public String getTagsError() {
        return tagsError;
    }

    public String getBio() {
        return bio;
    }

    public String getBioError() {
        return bioError;
    }

    public Map<String, Boolean> getPreferences() {
        return preferences;
    }

    public String getPreferencesError() {
        return preferencesError;
    }

    public List<String> getPreferredGender() {
        return preferredGender;
    }

    public String getPreferredGenderError() {
        return preferredGenderError;
    }

    public HashMap<String, Integer> getPreferredAge() {
        return preferredAge;
    }

    public String getPreferredAgeError() {
        return preferredAgeError;
    }

    //set methods

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setImage(String image) { this.image = image; }

    public void setLocation(String location) { this.location = location; }

    public void setGender(String gender) { this.gender = gender; }

    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setTagsError(String tagsError) {
        this.tagsError = tagsError;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setBioError(String bioError) {
        this.bioError = bioError;
    }

    public void setPreferences(Map<String, Boolean> preferences) {
        this.preferences = preferences;
    }

    public void setPreferencesError(String preferencesError) {
        this.preferencesError = preferencesError;
    }

    public void setPreferredGender(List<String> preferredGender) {
        this.preferredGender = preferredGender;
    }

    public void setPreferredGenderError(String preferredGenderError) {
        this.preferredGenderError = preferredGenderError;
    }

    public void setPreferredAge(HashMap<String, Integer> preferredAge) {
        this.preferredAge = preferredAge;
    }

    public void setPreferredAgeError(String preferredAgeError) {
        this.preferredAgeError = preferredAgeError;
    }

    @Override
    public String toString() {
        return "PreferencesState{"
                + "tags=" + tags
                + ", bio='" + bio + '\''
                + ", preferences=" + preferences
                + ", preferredGender=" + preferredGender
                + ", preferredAge=" + preferredAge
                + '}';
    }
}
