package use_case.compatibility;

import java.util.HashMap;
import java.util.Map;

/**
 * The calculator for compatibility based on preferences.
 */
public class CompatibilityCalculator {

    private final Map<String, Boolean> preferencesUser1;
    private final Map<String, Boolean> preferencesUser2;
    private final Map<String, Boolean> samplePreferences = new HashMap<>();

    public CompatibilityCalculator(Map<String, Boolean> preferencesUser1,Map<String, Boolean> preferencesUser2) {
        this.preferencesUser1 = preferencesUser1;
        this.preferencesUser2 = preferencesUser2;
        this.samplePreferences.put("Morning", Boolean.TRUE);
        this.samplePreferences.put("Spontaneous", Boolean.FALSE);
        this.samplePreferences.put("Food", Boolean.FALSE);
        this.samplePreferences.put("Mountain", Boolean.TRUE);
        this.samplePreferences.put("Music", Boolean.TRUE);
        this.samplePreferences.put("Reading", Boolean.FALSE);
        this.samplePreferences.put("Nature", Boolean.FALSE);
        this.samplePreferences.put("Half-boiled eggs", Boolean.FALSE);
    }

    /**
     * Calculates compatbility between two users based on preferences list.
     */
    public int calculate() {
        int curScore = 0;
        int i = 8;
        for (Map.Entry<String, Boolean> entry : this.samplePreferences.entrySet()) {
            if (preferencesUser1.get(entry.getKey()).equals(preferencesUser2.get(entry.getKey()))) {
                curScore += i/2;
            }
            i--;
        }

        return (curScore/28);
    }
}
