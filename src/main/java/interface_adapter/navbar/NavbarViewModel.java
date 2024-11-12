package interface_adapter.navbar;

import interface_adapter.ViewModel;

/**
 * The View Model for the Login View.
 */
public class NavbarViewModel extends ViewModel<NavbarState> {

    public NavbarViewModel() {
        super("navbar");
        setState(new NavbarState());
    }

}
