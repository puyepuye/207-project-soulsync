package interface_adapter.compatibility;

import use_case.compatibility.CompatibilityInputBoundary;
import use_case.compatibility.CompatibilityInputData;

/**
 * The controller for the Login Use Case.
 */
public class CompatibilityController {

    private final CompatibilityInputBoundary compatibilityUseCaseInteractor;

    public CompatibilityController(CompatibilityInputBoundary compatibilityUseCaseInteractor) {
        this.compatibilityUseCaseInteractor = compatibilityUseCaseInteractor;
    }

    /**
     * Executes the Login Use Case.
     *
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     */
    public void execute(String username, String password) {
        final CompatibilityInputData compatibilityInputData = new CompatibilityInputData(
                username, password);

        compatibilityUseCaseInteractor.execute(compatibilityInputData);
    }
}