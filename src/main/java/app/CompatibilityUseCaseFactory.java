package app;

import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.compatibility.CompatibilityController;
import interface_adapter.compatibility.CompatibilityPresenter;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.edit_profile.EditProfileViewModel;
import interface_adapter.listchat.ListChatViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.navbar.NavbarController;
import interface_adapter.navbar.NavbarPresenter;
import interface_adapter.navbar.NavbarViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.swipe.SwipeViewModel;
import use_case.compatibility.CompatibilityInputBoundary;
import use_case.compatibility.CompatibilityInteractor;
import use_case.compatibility.CompatibilityOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginUserDataAccessInterface;
import use_case.compatibility.CompatibilityInputBoundary;
import use_case.compatibility.CompatibilityInteractor;
import use_case.compatibility.CompatibilityOutputBoundary;
import use_case.compatibility.CompatibilityUserDataAccessInterface;
import view.CompatibilityView;
import view.LoginView;
import view.NavBarView;

import static app.NavbarUseCaseFactory.createNavbarUseCase;

/**
 * This class contains the static factory function for creating the LoggedInView.
 */
public final class CompatibilityUseCaseFactory {

    /** Prevent instantiation. */
    private CompatibilityUseCaseFactory() {

    }

    /**
     * Factory function for creating the LoggedInView.
     * @param viewManagerModel the ViewManagerModel to inject into the NavbarView
     * @param compatibilityViewModel the compatibilityViewModel to inject into the NavbarView
     * @return the LoggedInView created for the provided input classes
     */
    public static CompatibilityView create(
            ViewManagerModel viewManagerModel,
            CompatibilityViewModel compatibilityViewModel,
            NavbarViewModel navbarViewModel,
            SwipeViewModel swipeViewModel,
            EditProfileViewModel editProfileViewModel,
            CompatibilityUserDataAccessInterface userDataAccessInterface,
            ListChatViewModel listChatViewModel) {

        final CompatibilityController compatibilityController =
                createCompatibilityUseCase(viewManagerModel, compatibilityViewModel, userDataAccessInterface);

        final NavbarController navBarController =
                createNavbarUseCase(viewManagerModel, swipeViewModel, navbarViewModel, compatibilityViewModel, editProfileViewModel, listChatViewModel);

        return new CompatibilityView(compatibilityViewModel, compatibilityController, navBarController);

    }

    private static CompatibilityController createCompatibilityUseCase(
            ViewManagerModel viewManagerModel,
            CompatibilityViewModel compatibilityViewModel,
            CompatibilityUserDataAccessInterface userDataAccessInterface) {

//        final CompatibilityController compatibilityController =
//                createCompatibilityUseCase(viewManagerModel, compatibilityViewModel, userDataAccessInterface);
        final CompatibilityOutputBoundary compatibilityOutputBoundary = new CompatibilityPresenter(viewManagerModel,
                compatibilityViewModel);
        final CompatibilityInputBoundary compatibilityInteractor = new CompatibilityInteractor(userDataAccessInterface,
                compatibilityOutputBoundary);

        return new CompatibilityController(compatibilityInteractor);
    }
}
