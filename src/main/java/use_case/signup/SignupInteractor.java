package use_case.signup;

import entity.User;
import entity.UserFactory;

import java.util.*;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User already exists.");
        }
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else {
            Date sampleDate = new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime();
            final User user =  userFactory.create(signupInputData.getUsername(),
                    signupInputData.getPassword(),
                    "imageLink",
                    "Fullname",
                    "Location",
                    "gender",
                    new ArrayList<>() {{}},
                    sampleDate,
                    new HashMap<>() {{put("min", 18); put("max", 99);}},
                    "",
                    new HashMap<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}}
            );
            userDataAccessObject.save(user);

            final SignupOutputData signupOutputData = new SignupOutputData(user.getUsername(), user.getUsername(), user.getImage(),
                    user.getLocation(), user.getGender(), user.getDateOfBirth(), false);

            //final SignupOutputData signupOutputData = new SignupOutputData(user.getFullName(), false);

            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
