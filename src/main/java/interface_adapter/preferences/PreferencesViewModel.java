package interface_adapter.preferences;

import interface_adapter.ViewModel;

public class PreferencesViewModel extends ViewModel<PreferencesState> {

    public PreferencesViewModel() {
        super("preferences");
        setState(new PreferencesState());
    }
}

