package app;

import data_access.ChatDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.edit_profile.EditProfileViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.preferences.PreferencesViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.swipe.SwipeViewModel;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupUserDataAccessInterface;
import view.SignupView;

/**
 * This class contains the static factory function for creating the SignupView.
 */
public final class SignupUseCaseFactory {

    /** Prevent instantiation. */
    private SignupUseCaseFactory() {

    }

    /**
     * Creates and initializes a SignupView with the provided dependencies.
     *
     * @param viewManagerModel the model managing the application's views
     * @param loginViewModel the model storing data related to the login process
     * @param signupViewModel the model storing data related to the signup process
     * @param preferencesViewModel the model storing data related to user preferences
     * @param swipeViewModel the model providing data for swipe functionality
     * @param compatibilityViewModel the model providing compatibility-related data
     * @param editProfileViewModel the model for managing edit profile functionality
     * @param userDataAccessObject the data access object for managing user data during signup
     * @return an instance of SignupView configured with the required dependencies
     */
    public static SignupView create(
            ViewManagerModel viewManagerModel, LoginViewModel loginViewModel,
            SignupViewModel signupViewModel, PreferencesViewModel preferencesViewModel,
            SwipeViewModel swipeViewModel, CompatibilityViewModel compatibilityViewModel,
            EditProfileViewModel editProfileViewModel, SignupUserDataAccessInterface userDataAccessObject) {

        final SignupController signupController = createUserSignupUseCase(viewManagerModel, signupViewModel,
                loginViewModel, preferencesViewModel, swipeViewModel, compatibilityViewModel, editProfileViewModel,
                userDataAccessObject);
        return new SignupView(signupController, signupViewModel);

    }

    /**
     * Creates and configures a SignupController with its dependencies.
     *
     * @param viewManagerModel the model managing the application's views
     * @param signupViewModel the model storing data related to the signup process
     * @param loginViewModel the model storing data related to the login process
     * @param preferencesViewModel the model storing data related to user preferences
     * @param swipeViewModel the model providing data for swipe functionality
     * @param compatibilityViewModel the model providing compatibility-related data
     * @param editProfileViewModel the model for managing edit profile functionality
     * @param userDataAccessObject the data access object for managing user data during signup
     * @return an instance of SignupController configured with the required dependencies
     */
    private static SignupController createUserSignupUseCase(ViewManagerModel viewManagerModel,
                                                            SignupViewModel signupViewModel,
                                                            LoginViewModel loginViewModel,
                                                            PreferencesViewModel preferencesViewModel,
                                                            SwipeViewModel swipeViewModel,
                                                            CompatibilityViewModel compatibilityViewModel,
                                                            EditProfileViewModel editProfileViewModel,
                                                            SignupUserDataAccessInterface userDataAccessObject) {

        // Notice how we pass this method's parameters to the Presenter.
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel,
                loginViewModel, preferencesViewModel, swipeViewModel, compatibilityViewModel, editProfileViewModel);

        final UserFactory userFactory = new CommonUserFactory();
        final ChatDataAccessObject chatDataAccessObject = new ChatDataAccessObject();

        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory, chatDataAccessObject);

        return new SignupController(userSignupInteractor);
    }
}
