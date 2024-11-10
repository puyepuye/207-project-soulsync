package use_case.preferences;

import java.util.List;
import java.util.Map;

/**
 * The Input Data for the Signup Use Case.
 */
public class PreferenceInputData {

    private final List<String> tags;
    private final String bio;
    private final List<String> preferences;
    private final List<String> preferredGender;
    private final Map<String, Integer> preferredAge;

    public PreferenceInputData(List<String> tags, String bio,  List<String> preferences,
                               List<String> preferredGender, Map<String, Integer> preferredAge) {
        this.tags = tags;
        this.bio = bio;
        this.preferences = preferences;
        this.preferredGender = preferredGender;
        this.preferredAge = preferredAge;
    }

    List<String> getTags() { return tags; }

    String getBio() { return bio; }

    List<String> getPreferences() {
        return preferences;
    }

    List<String> getPreferredGender() { return preferredGender; }

    Map<String, Integer> getPreferredAge() { return preferredAge; }
}
