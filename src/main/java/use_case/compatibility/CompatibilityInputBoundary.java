package use_case.compatibility;

/**
 * The Change Password Use Case.
 */
public interface CompatibilityInputBoundary {

    /**
     * Execute the Change Password Use Case.
     * @param compatibilityInputData the input data for this use case
     */
    void execute(CompatibilityInputData compatibilityInputData);

}
