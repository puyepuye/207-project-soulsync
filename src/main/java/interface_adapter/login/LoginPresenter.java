package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.compatibility.CompatibilityState;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.listchat.ListChatState;
import interface_adapter.listchat.ListChatViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.swipe.SwipeViewModel;
import interface_adapter.swipe.SwipeState;
import interface_adapter.edit_profile.EditProfileViewModel;
import interface_adapter.edit_profile.EditProfileState;

import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final SignupViewModel signupViewModel;
    private final SwipeViewModel swipeViewModel;
    private final ViewManagerModel viewManagerModel;
    private final CompatibilityViewModel compatibilityViewModel;
    private final EditProfileViewModel editProfileViewModel;
    private final ListChatViewModel listChatViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          SwipeViewModel swipeViewModel,
                          LoginViewModel loginViewModel,
                          SignupViewModel signupViewModel,
                          CompatibilityViewModel compatibilityViewModel,
                          EditProfileViewModel editProfileViewModel,
                          ListChatViewModel listChatViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.swipeViewModel = swipeViewModel;
        this.loginViewModel = loginViewModel;
        this.compatibilityViewModel = compatibilityViewModel;
        this.editProfileViewModel = editProfileViewModel;
        this.listChatViewModel = listChatViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {

        final SwipeState swipeState = swipeViewModel.getState();
        swipeState.setUsername(response.getUsername());
        this.swipeViewModel.setState(swipeState);
        this.swipeViewModel.firePropertyChanged();

        final CompatibilityState compatibilityState = compatibilityViewModel.getState();
        compatibilityState.setUsername(response.getUsername());
        this.compatibilityViewModel.setState(compatibilityState);
        this.compatibilityViewModel.firePropertyChanged();

        final EditProfileState editProfileState = editProfileViewModel.getState();
        editProfileState.setUsername(response.getUsername());
        this.editProfileViewModel.setState(editProfileState);
        this.editProfileViewModel.firePropertyChanged();

        final ListChatState listChatState = listChatViewModel.getState();
        listChatState.setUsername(response.getUsername());
        this.listChatViewModel.setState(listChatState);
        this.listChatViewModel.firePropertyChanged();

        this.viewManagerModel.setState(swipeViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
