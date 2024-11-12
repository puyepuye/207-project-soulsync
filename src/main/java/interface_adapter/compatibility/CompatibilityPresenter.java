package interface_adapter.compatibility;

import interface_adapter.ViewManagerModel;
import use_case.compatibility.CompatibilityOutputBoundary;
import use_case.compatibility.CompatibilityOutputData;

/**
 * The Presenter for the Change Password Use Case.
 */
public class CompatibilityPresenter implements CompatibilityOutputBoundary {

    private final CompatibilityViewModel compatibilityViewModel;
    private final ViewManagerModel viewManagerModel;

    public CompatibilityPresenter(ViewManagerModel viewManagerModel,
                                  CompatibilityViewModel compatibilityViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.compatibilityViewModel = compatibilityViewModel;
    }

    @Override
    public void prepareSuccessView(CompatibilityOutputData outputData) {
        compatibilityViewModel.firePropertyChanged("password");

    }

    @Override
    public void prepareFailView(String error) {
    }
}
