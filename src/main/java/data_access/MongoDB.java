package data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

public class MongoDB {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String mongoUri = dotenv.get("MONGODB_URI");

        MongoClient client = MongoClients.create(mongoUri);
        MongoDatabase db = client.getDatabase("sampleDB");
        MongoCollection<Document> col = db.getCollection("sampleCollection");

        // Example insert to test that it shows up in sampleCollection
        Document sampleDoc = new Document("_id", 1).append("name", "Yolanda Lee");
        Document sampleDoc2 = new Document("_id", 2).append("age", "Legal");
        Document sampleDoc3 = new Document("_id", 3).append("preference", "Hot Men");
        Document sampleDoc4 = new Document("_id", 4).append("preferredAge", "Mature");

        col.drop();  // Clears existing documents before inserting new entries
        col.insertOne(sampleDoc);
        col.insertOne(sampleDoc2);
        col.insertOne(sampleDoc3);
        col.insertOne(sampleDoc4);

        Document documentWithId = new Document("_id", 000000)
                .append("userName", "bobby888")
                .append("password", "bobby888")
                .append("image", "https://bob.png")
                .append("fullName", "Bobby Dobby")
                .append("location", "Toronto")
                .append("gender", "Male")
                .append("preferredGender", "Female")
                .append("preferredAge", "18")
                .append("dateOfBirth", "2001-12-12")
                .append("age", 35)
                .append("bio", "asdadadadadad")
                .append("preferences", "white,tall,hot,nature")
                .append("tags", "ocean,romantic")
                .append("matched", "")
                .append("email", "charlie@example.com");

        col.insertOne(documentWithId);

        client.close();  // Close the MongoDB client
    }
}
