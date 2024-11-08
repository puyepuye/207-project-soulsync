package app;

import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.ChangePasswordController;
import interface_adapter.logged_in.LoggedInPresenter;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import view.LoggedInView;

/**
 * This class contains the static factory function for creating the LoggedInView.
 */
public final class ChangePasswordUseCaseFactory {

    /** Prevent instantiation. */
    private ChangePasswordUseCaseFactory() {

    }

    /**
     * Factory function for creating the LoggedInView.
     * @param viewManagerModel the ViewManagerModel to inject into the LoggedInView
     * @param loggedInViewModel the loggedInViewModel to inject into the LoggedInView
     * @param userDataAccessObject the ChangePasswordUserDataAccessInterface to inject into the LoggedInView
     * @return the LoggedInView created for the provided input classes
     */
    public static LoggedInView create(
            ViewManagerModel viewManagerModel,
            LoggedInViewModel loggedInViewModel,
            ChangePasswordUserDataAccessInterface userDataAccessObject) {

        final ChangePasswordController changePasswordController =
                    createChangePasswordUseCase(viewManagerModel, loggedInViewModel, userDataAccessObject);
        return new LoggedInView(loggedInViewModel, changePasswordController);

    }

    private static ChangePasswordController createChangePasswordUseCase(
            ViewManagerModel viewManagerModel,
            LoggedInViewModel loggedInViewModel,
            ChangePasswordUserDataAccessInterface userDataAccessObject) {

        // Notice how we pass this method's parameters through to the Presenter.
        final ChangePasswordOutputBoundary changePasswordOutputBoundary = new LoggedInPresenter(viewManagerModel,
                                                                                                loggedInViewModel);

        final UserFactory userFactory = new CommonUserFactory();

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        return new ChangePasswordController(changePasswordInteractor);
    }
}
