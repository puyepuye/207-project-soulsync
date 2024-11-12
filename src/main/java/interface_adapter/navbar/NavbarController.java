package interface_adapter.navbar;

import use_case.navbar.NavbarInputBoundary;
import use_case.navbar.NavbarInputData;

/**
 * The controller for the Login Use Case.
 */
public class NavbarController {

    private final NavbarInputBoundary navBarUseCaseInteractor;

    public NavbarController(NavbarInputBoundary navBarUseCaseInteractor) {
        this.navBarUseCaseInteractor = navBarUseCaseInteractor;
    }

    /**
     * Executes the "switch to SwipeView" Use Case.
     */
    public void switchToSwipeView() {
        navBarUseCaseInteractor.switchToSwipeView();
    }

    /**
     * Executes the "switch to CompatibilityView" Use Case.
     */
    public void switchToCompatibilityView() {
        navBarUseCaseInteractor.switchToCompatibilityView();
    }
}
