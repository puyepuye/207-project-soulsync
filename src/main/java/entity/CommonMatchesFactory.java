package entity;

import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Factory for creating matches.
 */
public class CommonMatchesFactory implements MatchesFactory {

    @Override
    public CommonMatches create(String usernameA, String usernameB) {
        return new CommonMatches(usernameA, usernameB);
    }
}
