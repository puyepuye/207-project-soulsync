package app;

import static app.NavbarUseCaseFactory.createNavbarUseCase;

// Interface Adapter Layer
import interface_adapter.ViewManagerModel;
import interface_adapter.compatibility.CompatibilityController;
import interface_adapter.compatibility.CompatibilityPresenter;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.edit_profile.EditProfileViewModel;
import interface_adapter.listchat.ListChatViewModel;
import interface_adapter.navbar.NavbarController;
import interface_adapter.navbar.NavbarViewModel;
import interface_adapter.swipe.SwipeViewModel;
import use_case.compatibility.CompatibilityInputBoundary;
import use_case.compatibility.CompatibilityInteractor;
import use_case.compatibility.CompatibilityOutputBoundary;
import use_case.compatibility.CompatibilityUserDataAccessInterface;
import view.CompatibilityView;

/**
 * This class contains the static factory function for creating the CompatibilityView.
 */
public final class CompatibilityUseCaseFactory {

    /** Prevent instantiation. */
    private CompatibilityUseCaseFactory() {

    }

    /**
     * Factory function for creating the {@code CompatibilityView}.
     *
     * @param viewManagerModel responsible for managing transitions between views
     * @param compatibilityViewModel for managing compatibility-related data
     * @param navbarViewModel for managing data related to the navigation bar
     * @param swipeViewModel for handling swipe-related data and logic
     * @param editProfileViewModel for managing the edit profile related feature
     * @param userDataAccessInterface for accessing compatibility score
     * @param listChatViewModel for managing the list of chats and related data
     * @return the fully constructed instance
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
                createNavbarUseCase(viewManagerModel, swipeViewModel, navbarViewModel, compatibilityViewModel,
                        editProfileViewModel, listChatViewModel);

        return new CompatibilityView(compatibilityViewModel, compatibilityController, navBarController);

    }

    private static CompatibilityController createCompatibilityUseCase(
            ViewManagerModel viewManagerModel,
            CompatibilityViewModel compatibilityViewModel,
            CompatibilityUserDataAccessInterface userDataAccessInterface) {

        final CompatibilityOutputBoundary compatibilityOutputBoundary = new CompatibilityPresenter(viewManagerModel,
                compatibilityViewModel);
        final CompatibilityInputBoundary compatibilityInteractor = new CompatibilityInteractor(userDataAccessInterface,
                compatibilityOutputBoundary);

        return new CompatibilityController(compatibilityInteractor);
    }
}
