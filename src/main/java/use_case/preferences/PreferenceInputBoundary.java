package use_case.preferences;

import use_case.signup.SignupInputData;

/**
 * Input Boundary for actions which are related to signing up.
 */
public interface PreferenceInputBoundary {

    /**
     * Executes the signup use case.
     * @param preferenceInputData the input data
     */
    void execute(PreferenceInputData preferenceInputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToSwipeView();
}
