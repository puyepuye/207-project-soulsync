package use_case.edit_profile;

import use_case.signup.SignupOutputData;

/**
 * The output boundary for the Signup Use Case.
 */
public interface EditProfileOutputBoundary {

    /**
     * Prepares the success view for the Signup Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(EditProfileOutputData outputData);

    /**
     * Prepares the failure view for the Signup Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

}
