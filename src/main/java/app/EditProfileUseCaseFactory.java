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
import interface_adapter.swipe.SwipeViewModel;
import use_case.edit_profile.EditProfileInputBoundary;
import use_case.edit_profile.EditProfileInteractor;
import use_case.edit_profile.EditProfileOutputBoundary;
import use_case.edit_profile.EditProfileUserDataAccessInterface;
import view.EditProfileView;

/**
 * This class contains the static factory function for creating the EditProfileView.
 */
public final class EditProfileUseCaseFactory {

    /** Prevent instantiation. */
    private EditProfileUseCaseFactory() {

    }

    /**
     * Factory function for creating the {@code EditProfileView}.
     *
     * @param viewManagerModel responsible for managing transitions between different views
     * @param loginViewModel handles user login data and interactions
     * @param editProfileViewModel used for managing the state and data of the edit profile feature
     * @param preferencesViewModel manages user preferences data
     * @param swipeViewModel responsible for handling swipe-related actions and logic
     * @param userDataAccessObject provides data access for user profile editing
     * @return the fully constructed instance
     */

    public static EditProfileView create(
            ViewManagerModel viewManagerModel, LoginViewModel loginViewModel,
            EditProfileViewModel editProfileViewModel, PreferencesViewModel preferencesViewModel,
            SwipeViewModel swipeViewModel, EditProfileUserDataAccessInterface userDataAccessObject) {

        final EditProfileController editProfileController = createUserEditProfileUseCase(viewManagerModel,
                editProfileViewModel, loginViewModel, swipeViewModel, userDataAccessObject);
        return new EditProfileView(editProfileController, editProfileViewModel);

    }

    private static EditProfileController createUserEditProfileUseCase(ViewManagerModel viewManagerModel,
                                                            EditProfileViewModel editProfileViewModel,
                                                            LoginViewModel loginViewModel,
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
