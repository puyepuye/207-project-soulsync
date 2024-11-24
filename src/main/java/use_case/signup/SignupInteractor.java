package use_case.signup;

import entity.User;
import entity.UserFactory;
import use_case.chat.ChatDataAccessInterface;

import java.text.ParseException;

import java.util.*;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;
    private final ChatDataAccessInterface chatDataAccessObject;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory,
                            ChatDataAccessInterface chatDataAccessInterface) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
        this.chatDataAccessObject = chatDataAccessInterface;
    }

    @Override
    public void execute(SignupInputData signupInputData) throws ParseException {
        if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User already exists.");
        }
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else if (!signupInputData.getFullname().matches("[\\p{IsAlphabetic} ]+")) {
            userPresenter.prepareFailView("Please Enter your full name.");
        }
        else if (signupInputData.getDateOfBirth() == null) {
            userPresenter.prepareFailView("Date of birth is required.");
        }
        else if (signupInputData.getGender().isEmpty() || signupInputData.getGender().equals("Select Gender")) {
            userPresenter.prepareFailView("Please choose your gender.");
        }
        else if (signupInputData.getLocation().isEmpty() || signupInputData.getLocation().contains("Select")) {
            userPresenter.prepareFailView("Please choose your country and city.");
        }
        else {
            final User user =  userFactory.create(signupInputData.getUsername(),
                    signupInputData.getPassword(),
                    signupInputData.getImage(),
                    signupInputData.getFullname(),
                    signupInputData.getLocation(),
                    signupInputData.getGender(),
                    signupInputData.getPreferredGender(),
                    signupInputData.getDateOfBirth(),
                    signupInputData.getPreferredAge(),
                    "",
                    new HashMap<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}}
            );
            userDataAccessObject.saveUser(user);
            chatDataAccessObject.createChatUser(signupInputData.getUsername(), signupInputData.getFullname(), signupInputData.getImage());
            final SignupOutputData signupOutputData = new SignupOutputData(user.getUsername(), user.getUsername(), user.getImage(),
                    user.getLocation(), user.getGender(), user.getDateOfBirth(), user.getPreferredGender(), user.getPreferredAge(), false);

            //final SignupOutputData signupOutputData = new SignupOutputData(user.getFullName(), false);

            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
