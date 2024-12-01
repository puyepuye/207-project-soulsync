package interface_adapter.edit_profile;

import use_case.edit_profile.EditProfileInputBoundary;
import use_case.edit_profile.EditProfileInputData;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Controller for the Signup Use Case.
 */
public class EditProfileController {

    private final EditProfileInputBoundary userEditProfileUseCaseInteractor;

    public EditProfileController(EditProfileInputBoundary userSignupUseCaseInteractor) {
        this.userEditProfileUseCaseInteractor = userSignupUseCaseInteractor;
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
     * @param preferredGender the list of preferred gender of the user
     * @param preferredAge the start and end integer of the user's preferred age range
     */
    public void execute(String fullname, String username, String password1, String password2,
                        String image, String location, String gender, Date dateOfBirth, List<String> preferredGender,
                        HashMap<String, Integer> preferredAge) {
        final EditProfileInputData editProfileInputData = new EditProfileInputData(
                fullname, username, password1, image, password2, location, gender, dateOfBirth, preferredGender, preferredAge);
        try {
            userEditProfileUseCaseInteractor.execute(editProfileInputData);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToSwipeView() {
        userEditProfileUseCaseInteractor.switchToSwipeView();
    }

}
