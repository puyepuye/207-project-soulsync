package interface_adapter.compatibility;

import use_case.compatibility.CompatibilityInputBoundary;
import use_case.compatibility.CompatibilityInputData;

import java.util.Date;
import java.util.Map;

/**
 * The controller for the Login Use Case.
 */
public class CompatibilityController {

    private final CompatibilityInputBoundary compatibilityUseCaseInteractor;

    public CompatibilityController(CompatibilityInputBoundary compatibilityUseCaseInteractor) {
        this.compatibilityUseCaseInteractor = compatibilityUseCaseInteractor;
    }

    /**
     * Executes the Compatibility Use Case.
     *
     * @param username the username of the current user
     * @param otherUsername the username of the other user
     */
    public void execute(String username, String otherUsername) {
        final CompatibilityInputData compatibilityInputData = new CompatibilityInputData(username);

        compatibilityUseCaseInteractor.execute(compatibilityInputData);
    }

    /**
     * Executes the getMatchedUser Compatibility Use Case.
     *
     * @param username the username of the current user
     */
    public String[] getMatchedUsers(String username) {
        final CompatibilityInputData compatibilityInputData = new CompatibilityInputData(username);

        return compatibilityUseCaseInteractor.getMatchedUsers(compatibilityInputData);

    }

    /**
     * Executes the getUserDOB Compatibility Use Case.
     *
     * @param username the username of the current user
     */
    public Date getUserDOB(String username) {
        final CompatibilityInputData compatibilityInputData = new CompatibilityInputData(username);

        return compatibilityUseCaseInteractor.getUserDOB(compatibilityInputData);

    }

    /**
     * Executes the getUserPreferences Compatibility Use Case.
     *
     * @param username the username of the current user
     */
    public Map<String, Boolean> getUserPreferences(String username) {
        final CompatibilityInputData compatibilityInputData = new CompatibilityInputData(username);

        return compatibilityUseCaseInteractor.getUserPreferences(compatibilityInputData);

    }

}