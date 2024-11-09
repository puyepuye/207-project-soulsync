package use_case.swipe;

/**
 * The input data for the Swipe Use Case.
 */
public class SwipeInputData {

    private final boolean like;
    private final String username;
    private final String password;
    private final String profileUsername;

    public SwipeInputData(boolean like,
                          String username,
                          String password,
                          String profileUsername) {
        this.like = like;
        this.username = username;
        this.password = password;
        this.profileUsername = profileUsername;
    }

    boolean getLiked() {
        return like;
    }

    String getUsername() {
        return username;
    }
    String getPassword() {
        return password;
    }
    String getProfileUsername() {
        return profileUsername;
    }


}

