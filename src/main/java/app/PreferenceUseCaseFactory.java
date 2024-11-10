package app;

import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.preferences.PreferencesController;
import interface_adapter.preferences.PreferencesViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.preferences.PreferencesPresenter;
import interface_adapter.preferences.PreferencesViewModel;
import use_case.preferences.PreferenceOutputBoundary;
import use_case.preferences.PreferenceUserDataAccessInterface;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupUserDataAccessInterface;
import view.PreferenceView;

/**
 * This class contains the static factory function for creating the SignupView.
 */
public final class PreferenceUseCaseFactory {

    /** Prevent instantiation. */
    private PreferenceUseCaseFactory() {

    }

    /**
     * Factory function for creating the SignupView.
     * @param viewManagerModel the ViewManagerModel to inject into the SignupView
     * @param loginViewModel the LoginViewModel to inject into the SignupView
     * @param signupViewModel the SignupViewModel to inject into the SignupView
     * @param userDataAccessObject the SignupUserDataAccessInterface to inject into the SignupView
     * @return the LoginView created for the provided input classes
     */
    public static PreferenceView create(
            ViewManagerModel viewManagerModel, LoginViewModel loginViewModel,
            SignupViewModel signupViewModel,  PreferencesViewModel preferencesViewModel, PreferenceUserDataAccessInterface userDataAccessObject) {

        final PreferencesController preferencesController = createPreferenceUseCase(viewManagerModel, signupViewModel,  preferencesViewModel, userDataAccessObject);
        return new PreferenceView(preferencesController, preferencesViewModel);

    }

    private static PreferencesController createPreferenceUseCase(ViewManagerModel viewManagerModel,
                                                            SignupViewModel signupViewModel,
                                                            PreferencesViewModel preferencesViewModel,
                                                            PreferenceUserDataAccessInterface userDataAccessObject) {

        // Notice how we pass this method's parameters to the Presenter.
        final PreferenceOutputBoundary preferenceOutputBoundary = new PreferencesPresenter(viewManagerModel,
                                                                              signupViewModel, preferencesViewModel);

        final UserFactory userFactory = new CommonUserFactory();

        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        return new SignupController(userSignupInteractor);
    }
}
