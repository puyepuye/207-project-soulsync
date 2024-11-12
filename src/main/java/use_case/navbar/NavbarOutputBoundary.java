package use_case.navbar;

/**
 * The output boundary for the Login Use Case.
 */
public interface NavbarOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(NavbarOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Executes the switch to swipe view use case.
     */
    void switchToSwipeView();

    /**
     * Executes the switch to compatibility view use case.
     */
    void switchToCompatibilityView();
}
