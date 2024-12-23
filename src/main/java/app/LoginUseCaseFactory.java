package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.edit_profile.EditProfileViewModel;
import interface_adapter.listchat.ListChatViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.swipe.SwipeViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginUserDataAccessInterface;
import view.LoginView;

/**
 * This class contains the static factory function for creating the LoginView.
 */
public final class LoginUseCaseFactory {

    /** Prevent instantiation. */
    private LoginUseCaseFactory() {

    }

    /**
     * Factory function for creating the LoginView.
     *
     * @param viewManagerModel responsible for managing transitions and coordinating views in the application
     * @param loginViewModel holds the state and data required for the login feature
     * @param swipeViewModel used for managing data and interactions related to the swipe feature
     * @param signupViewModel used to hold data and state for the signup functionality
     * @param compatibilityViewModel manages compatibility-related data and interactions
     * @param editProfileViewModel used for handling the data and state related to profile editing
     * @param userDataAccessObject provides data access for the login feature
     * @param listChatViewModel responsible for managing the data and state of the list chat feature
     * @return the LoginView created for the provided input classes
     */
    public static LoginView create(
            ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel,
            SwipeViewModel swipeViewModel,
            SignupViewModel signupViewModel,
            CompatibilityViewModel compatibilityViewModel,
            EditProfileViewModel editProfileViewModel,
            LoginUserDataAccessInterface userDataAccessObject,
            ListChatViewModel listChatViewModel) {

        final LoginController loginController = createLoginUseCase(viewManagerModel, loginViewModel, swipeViewModel,
                signupViewModel, compatibilityViewModel, editProfileViewModel, userDataAccessObject, listChatViewModel);
        return new LoginView(loginViewModel, loginController);

    }

    private static LoginController createLoginUseCase(
            ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel,
            SwipeViewModel swipeViewModel,
            SignupViewModel signupViewModel,
            CompatibilityViewModel compatibilityViewModel,
            EditProfileViewModel editProfileViewModel,
            LoginUserDataAccessInterface userDataAccessObject,
            ListChatViewModel listChatViewModel) {

        // Notice how we pass this method's parameters to the Presenter.
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, swipeViewModel,
                loginViewModel, signupViewModel, compatibilityViewModel, editProfileViewModel, listChatViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        return new LoginController(loginInteractor);
    }
}
