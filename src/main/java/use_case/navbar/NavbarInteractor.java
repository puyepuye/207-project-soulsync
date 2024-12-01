package use_case.navbar;

import entity.User;
import use_case.login.LoginOutputData;

/**
 * The Login Interactor.
 */
public class NavbarInteractor implements NavbarInputBoundary {
    private final NavbarOutputBoundary navbarPresenter;

    public NavbarInteractor(NavbarOutputBoundary navbarOutputBoundary) {
        this.navbarPresenter = navbarOutputBoundary;
    }

    @Override
    public void switchToSwipeView() {
        navbarPresenter.switchToSwipeView();
    }

    @Override
    public void switchToCompatibilityView() {
        navbarPresenter.switchToCompatibilityView();
    }

    @Override
    public void switchToEditProfileView() {
        navbarPresenter.switchToEditProfileView();
    }

    @Override
    public void switchToListChatView(){navbarPresenter.switchToListChatView();}
}
