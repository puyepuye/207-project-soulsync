package use_case.swipe;

/**
 * The output boundary for the Swipe Use Case.
 */
public interface SwipeOutputBoundary {
    /**
     * Prepares the success view for the swipe Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(SwipeOutputData outputData);

    /**
     * Prepares the failure view for the swipe Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
