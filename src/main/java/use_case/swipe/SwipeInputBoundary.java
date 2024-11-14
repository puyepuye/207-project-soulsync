package use_case.swipe;

import entity.User;
import use_case.swipe.SwipeInputData;

import java.util.List;

public interface SwipeInputBoundary {
    /**
     * Execute the Change Password Use Case.
     * @param swipeInputData the input data for this use case
     */
    void execute(SwipeInputData swipeInputData);

//    List<User> getProfileList();

}
