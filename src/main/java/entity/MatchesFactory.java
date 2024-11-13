package entity;

import org.bson.types.ObjectId;

import java.util.Date;

public interface MatchesFactory {
    /**
     * Creates a new match.
     * @param userAId the userAId of the new user
     * @param userBId the userBId of the new user
     * @param matchDate the matchDate of the new user
     * @param isActive the activity of the new user
     */

        CommonMatches create(ObjectId userAId, ObjectId userBId, Date matchDate, Boolean isActive);
}
