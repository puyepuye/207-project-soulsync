package app;

import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.listchat.ListChatViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.navbar.NavbarController;
import interface_adapter.navbar.NavbarPresenter;
import interface_adapter.navbar.NavbarViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.swipe.SwipeViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginUserDataAccessInterface;
import use_case.navbar.NavbarInputBoundary;
import use_case.navbar.NavbarInteractor;
import use_case.navbar.NavbarOutputBoundary;
import use_case.navbar.NavbarUserDataAccessInterface;
import view.LoginView;
import view.NavBarView;

/**
 * This class contains the static factory function for creating the LoggedInView.
 */
public final class NavbarUseCaseFactory {

    /** Prevent instantiation. */
    private NavbarUseCaseFactory() {

    }

    /**
     * Factory function for creating the LoggedInView.
     * @param viewManagerModel the ViewManagerModel to inject into the NavbarView
     * @param swipeViewModel the swipeViewModel to inject into the NavbarView
     * @param navBarViewModel the navBarViewModel to inject into the NavbarView
     * @return the LoggedInView created for the provided input classes
     */
    public static NavBarView create(
            ViewManagerModel viewManagerModel,
            SwipeViewModel swipeViewModel,
            NavbarViewModel navBarViewModel,
            CompatibilityViewModel compatibilityViewModel,
            ListChatViewModel listChatViewModel) {

        final NavbarController navBarController =
                createNavbarUseCase(viewManagerModel, swipeViewModel, navBarViewModel,
                        compatibilityViewModel, listChatViewModel);

        return new NavBarView(navBarController);

    }

    public static NavbarController createNavbarUseCase(
            ViewManagerModel viewManagerModel,
            SwipeViewModel swipeViewModel,
            NavbarViewModel navBarViewModel,
            CompatibilityViewModel compatibilityViewModel,
            ListChatViewModel listChatViewModel) {

        // Notice how we pass this method's parameters to the Presenter.
        final NavbarOutputBoundary navBarOutputBoundary = new NavbarPresenter(navBarViewModel,
                viewManagerModel, swipeViewModel, compatibilityViewModel, listChatViewModel);
        final NavbarInputBoundary navBarInteractor = new NavbarInteractor(navBarOutputBoundary);

        return new NavbarController(navBarInteractor);
    }
}
