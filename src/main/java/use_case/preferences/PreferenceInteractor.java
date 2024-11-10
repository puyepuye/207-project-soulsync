package use_case.preferences;

import entity.User;
import entity.UserFactory;
import use_case.login.LoginInputData;

/**
 * The Preference Interactor.
 */
public class PreferenceInteractor implements PreferenceInputBoundary {
    private final PreferenceUserDataAccessInterface userDataAccessObject;
    private final PreferenceOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public PreferenceInteractor(PreferenceUserDataAccessInterface userDataAccessObject, PreferenceOutputBoundary userPresenter, UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
        this.userFactory = userFactory;
    }
    @Override
    public void execute(PreferenceInputData preferenceInputData) {
        System.out.println("Executing with tags: " + preferenceInputData.getTags());
        System.out.println("Bio: " + preferenceInputData.getBio());
        System.out.println("Preferences: " + preferenceInputData.getPreferences());
        System.out.println("Preferred Gender: " + preferenceInputData.getPreferredGender());
        System.out.println("Preferred Age: " + preferenceInputData.getPreferredAge());
    }

    @Override
    public void switchToSwipeView() {
//        userPresenter.switchToSwipeView();
    }
}
