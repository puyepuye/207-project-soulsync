package use_case.compatibility;

import java.util.Date;
import java.util.Map;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface CompatibilityInputBoundary {

    /**
     * Executes the compatibility use case.
     * @param compatibilityInputData the input data
     */
    void execute(CompatibilityInputData compatibilityInputData);

    /**
     * get users that current user matched with use case.
     * @param compatibilityInputData the input data
     */
    String[] getMatchedUsers(CompatibilityInputData compatibilityInputData);

    /**
     * get users' date of birth.
     * @param compatibilityInputData the input data
     */
    Date getUserDOB(CompatibilityInputData compatibilityInputData);

    /**
     * get users' preferences Map
     * @param compatibilityInputData the input data
     */
    Map<String, Boolean> getUserPreferences(CompatibilityInputData compatibilityInputData);
}
