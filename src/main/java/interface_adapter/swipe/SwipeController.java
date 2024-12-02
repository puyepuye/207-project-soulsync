package interface_adapter.swipe;

import use_case.swipe.SwipeInputBoundary;
import use_case.swipe.SwipeInputData;

public class SwipeController {
    private final SwipeInputBoundary userSwipeUseCaseInteractor;

    public SwipeController (SwipeInputBoundary userSwipeUseCaseInteractor) {
        this.userSwipeUseCaseInteractor = userSwipeUseCaseInteractor;
    }

    /**
     * Executes the Change Password Use Case.
     * @param like whether the person like the profile on the screen or not
     */
    public void execute(boolean like, String username, String profileUsername) {
        final SwipeInputData swipeInputData = new SwipeInputData(like, username, profileUsername);

        userSwipeUseCaseInteractor.execute(swipeInputData);
    }

    /**
     * Executes the Change Password Use Case.
     * @param like whether the person like the profile on the screen or not
     */
    public void saveMatch(boolean like, String username, String profileUsername) {
        final SwipeInputData swipeInputData = new SwipeInputData(like, username, profileUsername);
        System.out.println("usernameA" + username);
        System.out.println("usernameB" + profileUsername);
        userSwipeUseCaseInteractor.saveMatch(swipeInputData);
    }
}
