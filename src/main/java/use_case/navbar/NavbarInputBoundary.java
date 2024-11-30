package use_case.navbar;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface NavbarInputBoundary {

    /**
     * Executes the switch to Swipe view use case.
     */
    void switchToSwipeView();

    /**
     * Executes the switch to Compatibiblity view use case.
     */
    void switchToCompatibilityView();

    /**
     * Executes the switch to Edit Profile view use case.
     */
    void switchToEditProfileView();

    void switchToListChatView();
}
