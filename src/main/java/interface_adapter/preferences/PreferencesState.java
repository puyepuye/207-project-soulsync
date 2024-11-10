package interface_adapter.preferences;

import java.util.List;
import java.util.Map;

/**
 * The state for the Preference View Model.
 */
public class PreferencesState {
    private String fullName;
    private List<String> tags;
    private String tagsError;
    private String bio = "";
    private String bioError;
    private List<String> preferences;
    private String preferencesError;
    private List<String> preferredGender;
    private String preferredGenderError;
    private Map<String, Integer> preferredAge;
    private String preferredAgeError;

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

    public List<String> getPreferences() {
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

    public Map<String, Integer> getPreferredAge() {
        return preferredAge;
    }

    public String getPreferredAgeError() {
        return preferredAgeError;
    }

    //set methods
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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

    public void setPreferences(List<String> preferences) {
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

    public void setPreferredAge(Map<String, Integer> preferredAge) {
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
