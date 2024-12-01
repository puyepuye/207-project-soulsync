package use_case.swipe;

import entity.User;
import use_case.swipe.SwipeInputData;

import java.util.List;

public interface SwipeInputBoundary {
    /**
     * Execute the Swipe Use Case.
     * @param swipeInputData the input data for this use case
     */
    void execute(SwipeInputData swipeInputData);

    /**
     * Execute the Swipe Use Case.
     * @param swipeInputData the input data for this use case
     */
    void saveMatch(SwipeInputData swipeInputData);

}
