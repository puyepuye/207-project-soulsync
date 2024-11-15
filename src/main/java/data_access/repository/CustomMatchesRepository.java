package data_access.repository;

import entity.CommonMatches;
import org.bson.types.ObjectId;

public interface CustomMatchesRepository {
    void saveMatch(CommonMatches match);
    public CommonMatches getMatch(ObjectId userAId, ObjectId userBId);
}
