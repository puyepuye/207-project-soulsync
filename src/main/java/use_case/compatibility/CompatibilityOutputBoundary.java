package use_case.compatibility;

/**
 * The output boundary for the Change Password Use Case.
 */
public interface CompatibilityOutputBoundary {
    /**
     * Prepares the success view for the Change Password Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(CompatibilityOutputData outputData);

    /**
     * Prepares the failure view for the Change Password Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
