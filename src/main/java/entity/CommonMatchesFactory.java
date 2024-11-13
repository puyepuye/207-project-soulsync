package entity;

import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Factory for creating matches.
 */
public class CommonMatchesFactory implements MatchesFactory {

    @Override
    public CommonMatches create(ObjectId userAId, ObjectId userBId, Date matchDate, Boolean isActive) {
        return new CommonMatches(userAId, userBId, matchDate, isActive);
    }
}
