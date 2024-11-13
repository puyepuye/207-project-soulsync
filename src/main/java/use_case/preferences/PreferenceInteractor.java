package use_case.preferences;

import entity.User;
import entity.UserFactory;
import use_case.signup.SignupInputData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Preference Interactor.
 */
public class PreferenceInteractor implements PreferenceInputBoundary {
    private final PreferenceUserDataAccessInterface userDataAccessObject;
    private final PreferenceOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public PreferenceInteractor(PreferenceUserDataAccessInterface userDataAccessInterface, PreferenceOutputBoundary userPresenter, UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessInterface;
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

        final User currentUserInfo = userDataAccessObject.get(preferenceInputData.getUsername());
        System.out.println(currentUserInfo.getDateOfBirth());

        final User user =  userFactory.create(currentUserInfo.getUsername(),
                currentUserInfo.getPassword(),
                currentUserInfo.getImage(),
                currentUserInfo.getFullname(),
                currentUserInfo.getLocation(),
                currentUserInfo.getGender(),
                preferenceInputData.getPreferredGender(),
                currentUserInfo.getDateOfBirth(),
                preferenceInputData.getPreferredAge(),
                preferenceInputData.getBio(),
                preferenceInputData.getPreferences(),
                preferenceInputData.getTags(),
                new ArrayList<>() {{}},
                new ArrayList<>() {{}},
                new ArrayList<>() {{}},
                new ArrayList<>() {{}}
        );

        userDataAccessObject.updatePreference(user);

        final PreferenceOutputData preferenceOutputData = new PreferenceOutputData(user.getTags(), user.getBio(),
                user.getPreferences(), user.getPreferredGender(), user.getPreferredAge(), false);


        userPresenter.prepareSuccessView(preferenceOutputData);
    }

    @Override
    public void switchToSwipeView() {
        userPresenter.switchToSwipeView();
    }
}
