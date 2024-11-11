package use_case.swipe;

import use_case.swipe.SwipeInputData;

public interface SwipeInputBoundary {
    /**
     * Execute the Change Password Use Case.
     * @param swipeInputData the input data for this use case
     */
    void execute(SwipeInputData swipeInputData);

}
