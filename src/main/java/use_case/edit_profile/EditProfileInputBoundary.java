package use_case.edit_profile;

import use_case.signup.SignupInputData;

import java.text.ParseException;

/**
 * Input Boundary for actions which are related to signing up.
 */
public interface EditProfileInputBoundary {

    /**
     * Executes the signup use case.
     * @param editProfileInputData the input data
     */
    void execute(EditProfileInputData editProfileInputData) throws ParseException;

    void switchToSwipeView();
}
