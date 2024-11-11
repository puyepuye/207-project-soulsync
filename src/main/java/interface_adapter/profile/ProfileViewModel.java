package interface_adapter.profile;

import interface_adapter.ViewModel;

public class ProfileViewModel extends ViewModel<ProfileState> {

    public ProfileViewModel() {
        super("profile");
        setState(new ProfileState());
    }
}

