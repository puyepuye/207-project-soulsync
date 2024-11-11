package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class SignupViewModel extends ViewModel<SignupState> {

    public static final String TITLE_LABEL = "Sign Up View";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String PASSWORD_LABEL = "Choose password";
    public static final String REPEAT_PASSWORD_LABEL = "Enter password again";

    public static final String FULLNAME_LABEL = "Full Name";
    public static final String DOB_LABEL = "Date of Birth";
    public static final String GENDER_LABEL = "Gender";
    public static final String COUNTRY_LABEL = "Country";
    public static final String CITY_LABEL = "City";

    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";
    public static final String TO_LOGIN_BUTTON_LABEL = "Go to Login";

    public static final String[] GENDERS = {"Select Gender", "Male", "Female", "Non-Binary", "Other"};
    public static final String[] COUNTRIES = {"Select Country", "Canada", "Myanmar", "Taiwan", "Thailand"};
    public static final String[] CITIES = {"Select City", "Toronto", "Yangon", "Taipei", "Bangkok"};

    public SignupViewModel() {
        super("sign up");
        setState(new SignupState());
    }

}
