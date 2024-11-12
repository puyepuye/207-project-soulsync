package app;

import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.navbar.NavbarController;
import interface_adapter.navbar.NavbarPresenter;
import interface_adapter.navbar.NavbarViewModel;
import interface_adapter.swipe.SwipeController;
import interface_adapter.swipe.SwipePresenter;
import interface_adapter.swipe.SwipeViewModel;
import use_case.navbar.NavbarInputBoundary;
import use_case.navbar.NavbarInteractor;
import use_case.navbar.NavbarOutputBoundary;
import use_case.swipe.SwipeInputBoundary;
import use_case.swipe.SwipeInteractor;
import use_case.swipe.SwipeOutputBoundary;
import use_case.swipe.SwipeUserDataAccessInterface;
import view.SwipeView;
import view.NavBarView;

import static app.NavbarUseCaseFactory.createNavbarUseCase;

/**
 * This class contains the static factory function for creating the SwipeView.
 */
public final class SwipeUseCaseFactory {

    /** Prevent instantiation. */
    private SwipeUseCaseFactory() {

    }

    /**
     * Factory function for creating the LoggedInView.
     * @param viewManagerModel the ViewManagerModel to inject into the LoggedInView
     * @param swipeViewModel the SwipeViewModel to inject into the SwipeView
     * @param navBarViewModel the NavbarViewModel to inject into the SwipeView
     * @param userDataAccessObject the ChangePasswordUserDataAccessInterface to inject into the LoggedInView
     * @return the LoggedInView created for the provided input classes
     */
    public static SwipeView create(
            ViewManagerModel viewManagerModel,
            SwipeViewModel swipeViewModel,
            NavbarViewModel navBarViewModel,
            CompatibilityViewModel compatibilityViewModel,
            SwipeUserDataAccessInterface userDataAccessObject) {

        final SwipeController swipeController =
                createSwipeUseCase(viewManagerModel, swipeViewModel, userDataAccessObject);

        final NavbarController navBarController =
                createNavbarUseCase(viewManagerModel, swipeViewModel, navBarViewModel, compatibilityViewModel);

        return new SwipeView(swipeController, swipeViewModel, navBarController);

    }

    private static SwipeController createSwipeUseCase(
            ViewManagerModel viewManagerModel,
            SwipeViewModel swipeViewModel,
            SwipeUserDataAccessInterface userDataAccessObject) {

        // Notice how we pass this method's parameters through to the Presenter.
        final SwipeOutputBoundary swipeOutputBoundary = new SwipePresenter(viewManagerModel,
                swipeViewModel);

        final UserFactory userFactory = new CommonUserFactory();

        final SwipeInputBoundary swipeInteractor =
                new SwipeInteractor(userDataAccessObject, swipeOutputBoundary, userFactory);

        return new SwipeController(swipeInteractor);
    }
}
