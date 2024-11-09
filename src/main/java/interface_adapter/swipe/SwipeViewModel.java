package interface_adapter.swipe;

import interface_adapter.ViewModel;

/**
 * The View Model for the Logged In View.
 */
public class SwipeViewModel extends ViewModel<SwipeState> {
    public static final String DISLIKE_BUTTON_LABEL = "X";
    public static final String LIKE_BUTTON_LABEL = "♡";
    public SwipeViewModel() {
        super("swipe");
        setState(new SwipeState());
    }

}
