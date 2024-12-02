package use_case.swipe;

import java.util.List;

/**
 * The input data for the Swipe Use Case.
 */
public class SwipeOutputData {

    private final boolean like;
    private final String profileName;
    private final String profileBio;
    private final List<String> profileTags;
    private final boolean useCaseFailed;

    public SwipeOutputData(boolean like,
                           String profileName,
                           String profileBio,
                           List<String> profileTags,
                           boolean useCaseFailed) {
        this.like = like;
        this.profileName = profileName;
        this.profileBio = profileBio;
        this.profileTags = profileTags;
        this.useCaseFailed = useCaseFailed;
    }

}

