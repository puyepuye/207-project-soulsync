package use_case.swipe;

import entity.User;
import entity.UserFactory;
import use_case.chat.ChatDataAccessInterface;
import java.util.ArrayList;

/**
 * The Swipe Password Interactor.
 */
public class SwipeInteractor implements SwipeInputBoundary {
    private final SwipeUserDataAccessInterface userDataAccessObject;
    private final SwipeOutputBoundary userPresenter;
    private final ChatDataAccessInterface chatDataAccessObject;

    public SwipeInteractor(SwipeUserDataAccessInterface swipeDataAccessInterface,
                           SwipeOutputBoundary swipeOutputBoundary,
                           ChatDataAccessInterface chatDataAccessObject) {
        this.userDataAccessObject = swipeDataAccessInterface;
        this.userPresenter = swipeOutputBoundary;
        this.chatDataAccessObject = chatDataAccessObject;
    }

    @Override
    public void execute(SwipeInputData swipeInputData) {
        final String username = swipeInputData.getUsername();
        final String profileUsername = swipeInputData.getProfileUsername();
        final boolean liked = swipeInputData.getLiked();

        final User user = userDataAccessObject.get(username);
        final User profileUser = userDataAccessObject.get(profileUsername);
        userDataAccessObject.updateLike(user, profileUser, liked);

        final SwipeOutputData swipeOutputData = new SwipeOutputData(liked, profileUsername, "",
                new ArrayList<>(), false);
        userPresenter.prepareSuccessView(swipeOutputData);
    }

    @Override
    public void saveMatch(SwipeInputData swipeInputData) {
        final String username = swipeInputData.getUsername();
        final String profileUsername = swipeInputData.getProfileUsername();
        userDataAccessObject.saveMatch(username, profileUsername);
        chatDataAccessObject.createChat(username, profileUsername);
    }
}
