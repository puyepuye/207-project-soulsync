package use_case.preferences;

/**
 * The output boundary for the Signup Use Case.
 */
public interface PreferenceOutputBoundary {

    /**
     * Prepares the success view for the Signup Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(PreferenceOutputData outputData);

    /**
     * Prepares the failure view for the Signup Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

}
