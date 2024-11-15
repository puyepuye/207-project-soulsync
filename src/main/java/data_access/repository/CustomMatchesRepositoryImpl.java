package data_access.repository;

import entity.CommonMatches;
import entity.CommonUser;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class CustomMatchesRepositoryImpl implements CustomMatchesRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void saveMatch(CommonMatches match) {
        mongoTemplate.save(match);
    }

    @Override
    public CommonMatches getMatch(ObjectId userAId, ObjectId userBId) {
        System.out.println("Gesundheit!");
        Query query = new Query(Criteria.where("userAID").is(userAId).and("userBID").is(userBId));
        return mongoTemplate.findOne(query, CommonMatches.class);
    }

}
