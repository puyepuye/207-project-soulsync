package use_case.preferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Output Data for the Preference Use Case.
 */
public class PreferenceOutputData {

    private final List<String> tags;
    private final String bio;
    private final Map<String, Boolean> preferences;
    private final List<String> preferredGender;
    private final HashMap<String, Integer> preferredAge;
    private final boolean useCaseFailed;

    public PreferenceOutputData(List<String> tags, String bio, Map<String, Boolean> preferences,
                                List<String> preferredGender, HashMap<String, Integer> preferredAge,
                                boolean useCaseFailed) {
        this.tags = tags;
        this.bio = bio;
        this.preferences = preferences;
        this.preferredGender = preferredGender;
        this.preferredAge = preferredAge;
        this.useCaseFailed = useCaseFailed;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getBio() {
        return bio;
    }

    public Map<String, Boolean> getPreferences() {
        return preferences;
    }

    public List<String> getPreferredGender() {
        return preferredGender;
    }

    public HashMap<String, Integer> getPreferredAge() {
        return preferredAge;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
