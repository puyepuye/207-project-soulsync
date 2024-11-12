package use_case.compatibility;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface CompatibilityInputBoundary {

    /**
     * Executes the login use case.
     * @param compatibilityInputData the input data
     */
    void execute(CompatibilityInputData compatibilityInputData);

}
