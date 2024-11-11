package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

import java.text.ParseException;
import java.util.Date;

/**
 * Controller for the Signup Use Case.
 */
public class SignupController {

    private final SignupInputBoundary userSignupUseCaseInteractor;

    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    /**
     * Executes the Signup Use Case.
     * @param fullname the full name of the user
     * @param username the username to sign up
     * @param password1 the password
     * @param password2 the password repeated
     * @param image the profile image of the user
     * @param location the location of the user
     * @param gender the gender of the user
     * @param dateOfBirth the date of birth of the user
     */
    public void execute(String fullname, String username, String password1, String password2,
                        String image, String location, String gender, String dateOfBirth) {
        final SignupInputData signupInputData = new SignupInputData(
                fullname, username, password1, image, password2, location, gender, dateOfBirth);

        try {
            userSignupUseCaseInteractor.execute(signupInputData);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the "switch to Login View" Use Case.
     */
    public void switchToLoginView() {
        userSignupUseCaseInteractor.switchToLoginView();
    }

}
