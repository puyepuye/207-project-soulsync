package interface_adapter.preferences;

import use_case.login.LoginInputData;
import use_case.preferences.PreferenceInputBoundary;
import use_case.preferences.PreferenceInputData;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PreferencesController {
    private final PreferenceInputBoundary preferenceInputBoundary;

    public PreferencesController(PreferenceInputBoundary preferenceInputBoundary) {
        this.preferenceInputBoundary = preferenceInputBoundary;
    }

    /**
     * Executes the login use case with user preferences and profile details.
     *
     * @param username        a username string
     * @param tags            a list of tags or interests associated with the user
     * @param bio             a brief biography or description of the user
     * @param preferences     a list of user preferences regarding matching criteria
     * @param preferredGender a list of preferred genders for potential matches
     * @param preferredAge    a map containing age preferences for potential matches,
     *                        where the key is the age range type (e.g., "min" or "max")
     *                        and the value is the corresponding age
     */
    public void execute(String username, List<String> tags, String bio, Map<String, Boolean> preferences,
                        List<String> preferredGender, Map<String, Integer> preferredAge) {
        final PreferenceInputData preferenceInputData = new PreferenceInputData(username, tags, bio, preferences, preferredGender, preferredAge);

        preferenceInputBoundary.execute(preferenceInputData);
    }

    /**
     * Executes the "switch to SwipeView" Use Case.
     */
    public void switchToSwipeView() {
        preferenceInputBoundary.switchToSwipeView();
    }
}
