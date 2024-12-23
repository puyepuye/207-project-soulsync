package interface_adapter.preferences;

import interface_adapter.ViewManagerModel;
import interface_adapter.swipe.SwipeState;
import interface_adapter.swipe.SwipeViewModel;
import use_case.preferences.PreferenceOutputBoundary;
import use_case.preferences.PreferenceOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class PreferencesPresenter implements PreferenceOutputBoundary {

    private final PreferencesViewModel preferencesViewModel;
    private final SwipeViewModel swipeViewModel;
    private final ViewManagerModel viewManagerModel;

    public PreferencesPresenter(ViewManagerModel viewManagerModel,
                          PreferencesViewModel preferencesViewModel,
                                SwipeViewModel swipeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.preferencesViewModel = preferencesViewModel;
        this.swipeViewModel = swipeViewModel;
    }

    @Override
    public void prepareSuccessView(PreferenceOutputData response) {
        // On success, switch to the preferences view.

        final PreferencesState preferencesState = preferencesViewModel.getState();
        preferencesState.setPreferences(response.getPreferences());
        preferencesState.setPreferredAge(response.getPreferredAge());
        preferencesState.setPreferredGender(response.getPreferredGender());
        preferencesState.setBio(response.getBio());
        preferencesState.setTags(response.getTags());
        this.preferencesViewModel.setState(preferencesState);
        this.preferencesViewModel.firePropertyChanged();

        final SwipeState swipeState = swipeViewModel.getState();
        this.swipeViewModel.setState(swipeState);
        this.swipeViewModel.firePropertyChanged();

        this.viewManagerModel.setState(swipeViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final PreferencesState preferencesState = preferencesViewModel.getState();
        preferencesState.setTagsError(error);
        preferencesState.setBioError(error);
        preferencesState.setPreferencesError(error);

//        preferencesState.firePropertyChanged();
        preferencesViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSwipeView() {
        viewManagerModel.setState(swipeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
