package data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import entity.CommonMatches;
import entity.MatchesFactory;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

public class DBMatchesDataAccessObject {
    private final MatchesFactory matchesFactory;
    private final MongoCollection<Document> matchesCollection;

    public DBMatchesDataAccessObject(MatchesFactory matchesFactory) {
        this.matchesFactory = matchesFactory;
        Dotenv dotenv = Dotenv.load();
        String mongoUri = dotenv.get("MONGODB_URI");
        MongoClient client = MongoClients.create(mongoUri);
        MongoDatabase db = client.getDatabase("sampleDB");
        this.matchesCollection = db.getCollection("matchesCollection");
    }

    /** Saves the CommonMatches object to MongoDB collection.
     *
     * @param match is the CommonMatches object to save.
     */
    public void saveMatch(CommonMatches match) {
        Document saveMatchDoc = new Document("userAId", match.getUserAId())
                .append("userBId", match.getUserBId())
                .append("matchDate", match.getMatchDate())
                .append("isActive", match.getIsActive());
        matchesCollection.insertOne(saveMatchDoc);
    }

    /**
     * Gets the CommonMatches object fromm the MongoDB collection by both users.
     * @param userAId the ObjectId of userA.
     * @param userBId the ObjectId of userB.
     * @return The matching CommonMatches object or null if not found.
     */
    public CommonMatches getMatch(ObjectId userAId, ObjectId userBId) {
        Document query = new Document("userAId", userAId).append("userBId", userBId);
        Document matchDoc = matchesCollection.find(query).first();

        if (matchDoc != null) {
            ObjectId retreiveUserAId = matchDoc.getObjectId("userAId");
            ObjectId retreiveUserBId = matchDoc.getObjectId("userBId");
            Date matchDate = matchDoc.getDate("matchDate");
            Boolean isActive = matchDoc.getBoolean("isActive");

            return matchesFactory.create(retreiveUserAId, retreiveUserBId, matchDate, isActive);
        }
        return null;
    }
}
