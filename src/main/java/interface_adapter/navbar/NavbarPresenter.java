package interface_adapter.navbar;

import interface_adapter.ViewManagerModel;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.edit_profile.EditProfileViewModel;
import interface_adapter.listchat.ListChatViewModel;
import interface_adapter.signup.SignupState;
import interface_adapter.swipe.SwipeViewModel;
import interface_adapter.swipe.SwipeState;
import interface_adapter.navbar.NavbarViewModel;

import use_case.login.LoginOutputBoundary;
import use_case.navbar.NavbarOutputBoundary;
import use_case.navbar.NavbarOutputData;
import view.CompatibilityView;

public class NavbarPresenter implements NavbarOutputBoundary {

    private final NavbarViewModel navbarViewModel;
    private final SwipeViewModel swipeViewModel;
    private final ViewManagerModel viewManagerModel;
    private final CompatibilityViewModel compatibilityViewModel;
    private final EditProfileViewModel editProfileViewModel;
    private final ListChatViewModel listChatViewModel;

    public NavbarPresenter(NavbarViewModel navbarViewModel,
                           ViewManagerModel viewManagerModel,
                           SwipeViewModel swipeViewModel,
                           CompatibilityViewModel compatibilityViewModel,
                           EditProfileViewModel editProfileViewModel,
                           ListChatViewModel listChatViewModel) {
        this.navbarViewModel = navbarViewModel;
        this.viewManagerModel = viewManagerModel;
        this.swipeViewModel = swipeViewModel;
        this.compatibilityViewModel = compatibilityViewModel;
        this.editProfileViewModel = editProfileViewModel;
        this.listChatViewModel = listChatViewModel;
    }

    @Override
    public void prepareSuccessView(NavbarOutputData outputData) {
        this.viewManagerModel.setState(swipeViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
//        final NavbarState navBarState = navbarViewModel.getState();
        navbarViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSwipeView() {
        viewManagerModel.setState(swipeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToCompatibilityView() {
        viewManagerModel.setState(compatibilityViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToEditProfileView() {
        viewManagerModel.setState(editProfileViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToListChatView(){
        viewManagerModel.setState(listChatViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}

