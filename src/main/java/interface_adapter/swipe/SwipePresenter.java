package interface_adapter.swipe;

import interface_adapter.ViewManagerModel;
import use_case.swipe.SwipeOutputBoundary;
import use_case.swipe.SwipeOutputData;

/**
 * The Presenter for the Change Password Use Case.
 */
public class SwipePresenter implements SwipeOutputBoundary {

    private final SwipeViewModel swipeViewModel;
    private final ViewManagerModel viewManagerModel;

    public SwipePresenter(ViewManagerModel viewManagerModel,
                          SwipeViewModel swipeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.swipeViewModel = swipeViewModel;
    }

    @Override
    public void prepareSuccessView(SwipeOutputData outputData) {
        // TODO update the viewmodel!
        swipeViewModel.firePropertyChanged("password");

    }

    @Override
    public void prepareFailView(String error) {
        // TODO update the viewmodel!
    }
}
