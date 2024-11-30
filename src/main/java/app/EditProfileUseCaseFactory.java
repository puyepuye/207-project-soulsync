package app;

import data_access.ChatDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.edit_profile.EditProfileController;
import interface_adapter.edit_profile.EditProfilePresenter;
import interface_adapter.edit_profile.EditProfileViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.preferences.PreferencesViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.swipe.SwipeViewModel;
import use_case.chat.ChatDataAccessInterface;
import use_case.edit_profile.EditProfileInputBoundary;
import use_case.edit_profile.EditProfileInteractor;
import use_case.edit_profile.EditProfileOutputBoundary;
import use_case.edit_profile.EditProfileUserDataAccessInterface;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupUserDataAccessInterface;
import view.EditProfileView;
import view.SignupView;

/**
 * This class contains the static factory function for creating the SignupView.
 */
public final class EditProfileUseCaseFactory {

    /** Prevent instantiation. */
    private EditProfileUseCaseFactory() {

    }

    /**
     * Factory function for creating the SignupView.
     * @param viewManagerModel the ViewManagerModel to inject into the SignupView
     * @param loginViewModel the LoginViewModel to inject into the SignupView
     * @param editProfileViewModel the SignupViewModel to inject into the SignupView
     * @param userDataAccessObject the SignupUserDataAccessInterface to inject into the SignupView
     * @return the LoginView created for the provided input classes
     */
    public static EditProfileView create(
            ViewManagerModel viewManagerModel, LoginViewModel loginViewModel,
            EditProfileViewModel editProfileViewModel, PreferencesViewModel preferencesViewModel, SwipeViewModel swipeViewModel, EditProfileUserDataAccessInterface userDataAccessObject) {

        final EditProfileController editProfileController = createUserEditProfileUseCase(viewManagerModel, editProfileViewModel,
                loginViewModel, preferencesViewModel, swipeViewModel, userDataAccessObject);
        return new EditProfileView(editProfileController, editProfileViewModel);

    }

    private static EditProfileController createUserEditProfileUseCase(ViewManagerModel viewManagerModel,
                                                            EditProfileViewModel editProfileViewModel,
                                                            LoginViewModel loginViewModel,
                                                            PreferencesViewModel preferencesViewModel,
                                                            SwipeViewModel swipeViewModel,
                                                            EditProfileUserDataAccessInterface userDataAccessObject) {

        // Notice how we pass this method's parameters to the Presenter.
        final EditProfileOutputBoundary editProfileOutputBoundary = new EditProfilePresenter(viewManagerModel,
                editProfileViewModel,
                loginViewModel, swipeViewModel);

        final UserFactory userFactory = new CommonUserFactory();
        final ChatDataAccessObject chatDataAccessObject = new ChatDataAccessObject();

        final EditProfileInputBoundary userEditProfileInteractor = new EditProfileInteractor(
                userDataAccessObject, editProfileOutputBoundary, userFactory, chatDataAccessObject);

        return new EditProfileController(userEditProfileInteractor);
    }
}
