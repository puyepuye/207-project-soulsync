package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.preferences.PreferencesState;
import interface_adapter.preferences.PreferencesViewModel;
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

    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel,
                           LoginViewModel loginViewModel,
                           PreferencesViewModel preferencesViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.preferencesViewModel = preferencesViewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // On success, switch to the login view.
        final PreferencesState preferencesState = preferencesViewModel.getState();
//        loginState.setUsername(response.getUsername());
        preferencesState.setUsername(response.getUsername());
        this.preferencesViewModel.setState(preferencesState);
        this.preferencesViewModel.firePropertyChanged();

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
        signupViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
