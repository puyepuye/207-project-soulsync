package use_case.swipe;

import entity.User;
import entity.UserFactory;

/**
 * The Swipe Password Interactor.
 */
public class SwipeInteractor implements SwipeInputBoundary {
    private final SwipeUserDataAccessInterface userDataAccessObject;
    private final SwipeOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SwipeInteractor(SwipeUserDataAccessInterface swipeDataAccessInterface,
                                    SwipeOutputBoundary swipeOutputBoundary,
                                    UserFactory userFactory) {
        this.userDataAccessObject = swipeDataAccessInterface;
        this.userPresenter = swipeOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SwipeInputData swipeInputData) {
        final String username = swipeInputData.getUsername();
        final String password = swipeInputData.getPassword();
        final String profileUsername = swipeInputData.getProfileUsername();
        final boolean liked = swipeInputData.getLiked();
        final User user = userFactory.create(username, password);
        userDataAccessObject.updateLike(user, profileUsername, liked);

        final SwipeOutputData SwipeOutputData = new SwipeOutputData();
        userPresenter.prepareSuccessView(swipeOutputData);
    }
}
