package interface_adapter.edit_profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.preferences.PreferencesState;
import interface_adapter.preferences.PreferencesViewModel;
import interface_adapter.swipe.SwipeViewModel;
import use_case.edit_profile.EditProfileOutputBoundary;
import use_case.edit_profile.EditProfileOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class EditProfilePresenter implements EditProfileOutputBoundary {

    private final EditProfileViewModel editProfileViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SwipeViewModel swipeViewModel;

    public EditProfilePresenter(ViewManagerModel viewManagerModel,
                                EditProfileViewModel editProfileViewModel,
                                LoginViewModel loginViewModel,
                                SwipeViewModel swipeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.editProfileViewModel = editProfileViewModel;
        this.loginViewModel = loginViewModel;
        this.swipeViewModel = swipeViewModel;
    }

    @Override
    public void prepareSuccessView(EditProfileOutputData response) {
        final EditProfileState editProfileState = editProfileViewModel.getState();
//        loginState.setUsername(response.getUsername());
        editProfileState.setUsername(response.getUsername());
        editProfileState.setFullName(response.getFullName());
        editProfileState.setPassword(response.getPassword());
        editProfileState.setImage(response.getImage());
        editProfileState.setDateOfBirth(response.getDateOfBirth());
        editProfileState.setGender(response.getGender());
        editProfileState.setLocation(response.getLocation());
        editProfileState.setPreferredGender(response.getPreferredGender());
        editProfileState.setPreferredAge(response.getPreferredAge());
        this.editProfileViewModel.setState(editProfileState);
        this.editProfileViewModel.firePropertyChanged();

        this.viewManagerModel.setState(editProfileViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final EditProfileState editProfileState = editProfileViewModel.getState();
        editProfileState.setUsernameError(error);
        editProfileState.setPasswordError(error);
        editProfileState.setFullnameError(error);
        editProfileState.setDateOfBirthError(error);
        editProfileState.setGenderError(error);
        editProfileState.setLocationError(error);
        editProfileState.setPreferredGenderError(error);
        editProfileState.setPreferredAgeError(error);
        editProfileViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSwipeView() {
        viewManagerModel.setState(swipeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
