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
import use_case.preferences.PreferenceInputBoundary;
import use_case.preferences.PreferenceOutputBoundary;
import use_case.preferences.PreferenceUserDataAccessInterface;
import use_case.preferences.PreferenceInteractor;
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


    public static PreferenceView create(
            ViewManagerModel viewManagerModel,
            PreferencesViewModel preferencesViewModel,
            PreferenceUserDataAccessInterface userDataAccessObject) {

        final PreferencesController preferencesController = createPreferenceUseCase(viewManagerModel,  preferencesViewModel, userDataAccessObject);
        return new PreferenceView(preferencesViewModel, preferencesController);
    }

    private static PreferencesController createPreferenceUseCase(
            ViewManagerModel viewManagerModel,
            PreferencesViewModel preferencesViewModel,
            PreferenceUserDataAccessInterface userDataAccessObject) {

        // Notice how we pass this method's parameters to the Presenter.
        final PreferenceOutputBoundary preferenceOutputBoundary = new PreferencesPresenter(viewManagerModel,  preferencesViewModel);

        final UserFactory userFactory = new CommonUserFactory();

        final PreferenceInputBoundary preferenceInteractor = new PreferenceInteractor(
                userDataAccessObject, preferenceOutputBoundary, userFactory);

        return new PreferencesController(preferenceInteractor);
    }
}
