package entity;

import org.bson.types.ObjectId;

import java.util.Date;

public interface MatchesFactory {
    /**
     * Creates a new match.
     * @param usernameA the userAId of the new user
     * @param usernameB the userBId of the new user
     */

        CommonMatches create(String usernameA, String usernameB);
}
