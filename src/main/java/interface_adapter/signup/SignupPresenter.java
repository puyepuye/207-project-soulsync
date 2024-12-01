package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.compatibility.CompatibilityState;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.edit_profile.EditProfileState;
import interface_adapter.edit_profile.EditProfileViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.preferences.PreferencesState;
import interface_adapter.preferences.PreferencesViewModel;
import interface_adapter.swipe.SwipeState;
import interface_adapter.swipe.SwipeViewModel;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;
    private final PreferencesViewModel preferencesViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SwipeViewModel swipeViewModel;
    private final CompatibilityViewModel compatibilityViewModel;
    private final EditProfileViewModel editProfileViewModel;

    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel,
                           LoginViewModel loginViewModel,
                           PreferencesViewModel preferencesViewModel,
                           SwipeViewModel swipeViewModel,
                           CompatibilityViewModel compatibilityViewModel,
                           EditProfileViewModel editProfileViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.preferencesViewModel = preferencesViewModel;
        this.swipeViewModel = swipeViewModel;
        this.compatibilityViewModel = compatibilityViewModel;
        this.editProfileViewModel = editProfileViewModel;

    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // On success, switch to the login view.
        final PreferencesState preferencesState = preferencesViewModel.getState();
//        loginState.setUsername(response.getUsername());
        preferencesState.setUsername(response.getUsername());
        preferencesState.setFullName(response.getFullName());
        preferencesState.setPassword(response.getPassword());
        preferencesState.setImage(response.getImage());
        preferencesState.setDateOfBirth(response.getDateOfBirth());
        preferencesState.setGender(response.getGender());
        preferencesState.setLocation(response.getLocation());
        preferencesState.setPreferredGender(response.getPreferredGender());
        preferencesState.setPreferredAge(response.getPreferredAge());
        this.preferencesViewModel.setState(preferencesState);
        this.preferencesViewModel.firePropertyChanged();

        final SwipeState swipeState = swipeViewModel.getState();
        swipeState.setUsername(response.getUsername());
        this.swipeViewModel.setState(swipeState);
        this.swipeViewModel.firePropertyChanged();

        final CompatibilityState compatibilityState = compatibilityViewModel.getState();
        compatibilityState.setUsername(response.getUsername());
        this.compatibilityViewModel.setState(compatibilityState);
        this.compatibilityViewModel.firePropertyChanged();

        final EditProfileState editProfileState = editProfileViewModel.getState();
        editProfileState.setUsername(response.getUsername());
        this.editProfileViewModel.setState(editProfileState);
        this.editProfileViewModel.firePropertyChanged();

        this.viewManagerModel.setState(preferencesViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

        System.out.println("SignupPresenter.java called");
        System.out.println(preferencesViewModel.getViewName());
    }

    @Override
    public void prepareFailView(String error) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setUsernameError(error);
        signupState.setPasswordError(error);
        signupState.setFullnameError(error);
        signupState.setDateOfBirthError(error);
        signupState.setGenderError(error);
        signupState.setLocationError(error);
        signupState.setPreferredGenderError(error);
        signupState.setPreferredAgeError(error);
        signupViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
