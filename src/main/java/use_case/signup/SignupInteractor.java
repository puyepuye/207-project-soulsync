package use_case.signup;

import entity.User;
import entity.UserFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        else if (!isValidDateFormat(signupInputData.getDateOfBirth())) {
            userPresenter.prepareFailView("Invalid date format.");
        }
        else if (!isValidDate(signupInputData.getDateOfBirth())) {
            userPresenter.prepareFailView("Invalid date.");
        }
        else if (signupInputData.getGender().isEmpty() || signupInputData.getGender().equals("Select Gender")) {
            userPresenter.prepareFailView("Please choose your gender.");
        }
        else if (signupInputData.getLocation().isEmpty() || signupInputData.getLocation().contains("Select")) {
            userPresenter.prepareFailView("Please choose your country and city.");
        }
        else {
            Date dateOfBirth = parseDate(signupInputData.getDateOfBirth());

            final User user =  userFactory.create(signupInputData.getUsername(),
                    signupInputData.getPassword(),
                    "imageLink",
                    signupInputData.getFullname(),
                    signupInputData.getLocation(),
                    signupInputData.getGender(),
                    new ArrayList<>() {{}},
                    dateOfBirth,
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

    // Method to parse the date from string
    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    // Method to check if the date matches the pattern
    private boolean isValidDateFormat(String dateString) {
        String pattern = "^(\\d{4})-(\\d{2})-(\\d{2})$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(dateString);
        return m.matches();
    }

    // Method to validate the actual date using SimpleDateFormat
    private boolean isValidDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);  // Prevents accepting invalid dates like 2024-02-30
        try {
            Date date = dateFormat.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
