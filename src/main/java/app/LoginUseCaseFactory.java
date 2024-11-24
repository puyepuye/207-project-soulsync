package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.listchat.ListChatViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.swipe.SwipeViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginUserDataAccessInterface;
import view.CompatibilityView;
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
     * @param viewManagerModel the ViewManagerModel to inject into the LoginView
     * @param loginViewModel the LoginViewModel to inject into the LoginView
     * @param swipeViewModel the LoggedInViewModel to inject into the LoginView
     * @param userDataAccessObject the LoginUserDataAccessInterface to inject into the LoginView
     * @param signupViewModel the SignupViewModel to inject to the LoginView
     * @return the LoginView created for the provided input classes
     */
    public static LoginView create(
            ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel,
            SwipeViewModel swipeViewModel,
            SignupViewModel signupViewModel,
            CompatibilityViewModel compatibilityViewModel,
            LoginUserDataAccessInterface userDataAccessObject,
            ListChatViewModel listChatViewModel) {

        final LoginController loginController = createLoginUseCase(viewManagerModel, loginViewModel, swipeViewModel,
                signupViewModel, compatibilityViewModel, userDataAccessObject, listChatViewModel);
        return new LoginView(loginViewModel, loginController);

    }

    private static LoginController createLoginUseCase(
            ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel,
            SwipeViewModel swipeViewModel,
            SignupViewModel signupViewModel,
            CompatibilityViewModel compatibilityViewModel,
            LoginUserDataAccessInterface userDataAccessObject,
            ListChatViewModel listChatViewModel) {

        // Notice how we pass this method's parameters to the Presenter.
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, swipeViewModel,
                loginViewModel, signupViewModel, compatibilityViewModel, listChatViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        return new LoginController(loginInteractor);
    }
}
