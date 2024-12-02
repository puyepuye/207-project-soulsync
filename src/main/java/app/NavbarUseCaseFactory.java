package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.edit_profile.EditProfileViewModel;
import interface_adapter.listchat.ListChatViewModel;
import interface_adapter.navbar.NavbarController;
import interface_adapter.navbar.NavbarPresenter;
import interface_adapter.navbar.NavbarViewModel;
import interface_adapter.swipe.SwipeViewModel;
import use_case.navbar.NavbarInputBoundary;
import use_case.navbar.NavbarInteractor;
import use_case.navbar.NavbarOutputBoundary;
import view.NavBarView;

/**
 * This class contains the static factory function for creating the LoggedInView.
 */
public final class NavbarUseCaseFactory {

    /** Prevent instantiation. */
    private NavbarUseCaseFactory() {

    }

    /**
     * Creates and initializes a NavBarView with the provided dependencies.
     *
     * @param viewManagerModel for managing the application's views
     * @param swipeViewModel for providing data related to swipe functionality
     * @param navBarViewModel for storing data for the navigation bar
     * @param compatibilityViewModel for providing compatibility-related data
     * @param editProfileViewModel for providing data related to profile editing
     * @param listChatViewModel for handling data for chat lists
     * @return an instance of NavBarView configured with the required dependencies
     */
    public static NavBarView create(
            ViewManagerModel viewManagerModel,
            SwipeViewModel swipeViewModel,
            NavbarViewModel navBarViewModel,
            CompatibilityViewModel compatibilityViewModel,
            EditProfileViewModel editProfileViewModel,
            ListChatViewModel listChatViewModel) {

        final NavbarController navBarController =
                createNavbarUseCase(viewManagerModel, swipeViewModel, navBarViewModel, compatibilityViewModel,
                        editProfileViewModel, listChatViewModel);

        return new NavBarView(navBarController);

    }

    /**
     * Creates and configures a {@link NavbarController} with its dependencies.
     *
     * @param viewManagerModel for managing the application's views
     * @param swipeViewModel for providing data related to swipe functionality
     * @param navBarViewModel for storing data for the navigation bar
     * @param compatibilityViewModel for providing compatibility-related data
     * @param editProfileViewModel for providing data related to profile editing
     * @param listChatViewModel for handling data for chat lists
     * @return an instance of NavBarController configured with the required dependencies
     */
    public static NavbarController createNavbarUseCase(
            ViewManagerModel viewManagerModel,
            SwipeViewModel swipeViewModel,
            NavbarViewModel navBarViewModel,
            CompatibilityViewModel compatibilityViewModel,
            EditProfileViewModel editProfileViewModel,
            ListChatViewModel listChatViewModel) {

        // Notice how we pass this method's parameters to the Presenter.
        final NavbarOutputBoundary navBarOutputBoundary = new NavbarPresenter(navBarViewModel,
                viewManagerModel, swipeViewModel, compatibilityViewModel, editProfileViewModel, listChatViewModel);
        final NavbarInputBoundary navBarInteractor = new NavbarInteractor(navBarOutputBoundary);

        return new NavbarController(navBarInteractor);
    }
}
