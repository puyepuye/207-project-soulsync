package use_case.preferences;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The Input Data for the Signup Use Case.
 */
public class PreferenceInputData {

    private final String username;
    private final List<String> tags;
    private final String bio;
    private final Map<String, Boolean> preferences;
    private final List<String> preferredGender;
    private final Map<String, Integer> preferredAge;

    public PreferenceInputData(String username, List<String> tags, String bio,  Map<String, Boolean> preferences,
                               List<String> preferredGender, Map<String, Integer> preferredAge) {
        this.username = username;
        this.tags = tags;
        this.bio = bio;
        this.preferences = preferences;
        this.preferredGender = preferredGender;
        this.preferredAge = preferredAge;
    }


    public String getUsername() {
        return username;
    }

    List<String> getTags() { return tags; }

    String getBio() { return bio; }

    Map<String, Boolean> getPreferences() {
        return preferences;
    }

    List<String> getPreferredGender() { return preferredGender; }

    Map<String, Integer> getPreferredAge() { return preferredAge; }
}
