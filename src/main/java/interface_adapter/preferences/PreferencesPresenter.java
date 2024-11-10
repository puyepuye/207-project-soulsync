package interface_adapter.preferences;

import interface_adapter.ViewManagerModel;
import interface_adapter.preferences.PreferencesState;
import interface_adapter.preferences.PreferencesViewModel;
import use_case.preferences.PreferenceOutputBoundary;
import use_case.preferences.PreferenceOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class PreferencesPresenter implements PreferenceOutputBoundary {

    private final PreferencesViewModel preferencesViewModel;
    private final ViewManagerModel viewManagerModel;

    public PreferencesPresenter(ViewManagerModel viewManagerModel,
                          PreferencesViewModel preferencesViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.preferencesViewModel = preferencesViewModel;
    }

    @Override
    public void prepareSuccessView(PreferenceOutputData response) {
        // On success, switch to the preferences view.

        final PreferencesState preferencesState = preferencesViewModel.getState();
        this.preferencesViewModel.setState(preferencesState);
        this.preferencesViewModel.firePropertyChanged();

        this.viewManagerModel.setState(preferencesViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final PreferencesState preferencesState = preferencesViewModel.getState();
        preferencesViewModel.firePropertyChanged();
    }

//    @Override
//    public void switchToPreferencesView() {
//        viewManagerModel.setState(preferencesViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
//    }
}
