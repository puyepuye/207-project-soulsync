package interface_adapter.swipe;

import java.util.List;

/**
 * The State information representing the logged-in user.
 */
public class SwipeState {
    private boolean like = false;
    private String username;
    private String profileName;
    private String profileBio;
    private List<String> profileTags;
    private String likedError;

    public SwipeState(SwipeState copy) {
        like = copy.like;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public SwipeState() {

    }
    public String getUsername() {
        return username;
    }
    public String getProfileName() {
        return profileName;
    }
    public String getProfileBio() {
        return profileBio;
    }
//    public List<String> getProfileTags() {
//        return profileTags;
//    }
    public String getProfileTags() {
        return profileTags.get(0);
    }
    public boolean getLiked() {
        return like;
    }
    public void setLiked(boolean like) {
        this.like = like;
    }
    public String getLikedError() {
        return likedError;
    }
}
