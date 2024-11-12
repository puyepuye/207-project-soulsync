package interface_adapter.compatibility;

import interface_adapter.ViewModel;

public class CompatibilityViewModel extends ViewModel<CompatibilityState> {

    public CompatibilityViewModel() {
        super("compatibility");
        setState(new CompatibilityState());
    }

}
