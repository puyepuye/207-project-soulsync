package data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

//import static com.mongodb.client.model.Filters.eq;

public class MongoDB {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();
        String mongoUri = dotenv.get("MONGODB_URI");

        MongoClient client = MongoClients.create(mongoUri);

        MongoDatabase db = client.getDatabase("sampleDB");

        MongoCollection col = db.getCollection("sampleCollection");

        // Example insert to test that it shows up in sampleCollection
        Document sampleDoc = new Document("_id", 1).append("name", "Yolanda Lee");
        Document sampleDoc2 = new Document("_id", 2).append("age", "Legal");
        Document sampleDoc3 = new Document("_id", 3).append("preference", "Hot Men");
        Document sampleDoc4 = new Document("_id", 4).append("preferredAge", "Mature");

        col.drop();  // Removes existing documents before inserting new entries
        col.insertOne(sampleDoc);
        col.insertOne(sampleDoc2);
        col.insertOne(sampleDoc3);
        col.insertOne(sampleDoc4);

    }


}
