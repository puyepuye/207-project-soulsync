package data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import data_access.Cloudinary.CloudinaryUploader;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

import java.io.File;
import java.util.UUID;

public class MongoDB {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String mongoUri = dotenv.get("MONGODB_URI");

        MongoClient client = MongoClients.create(mongoUri);
        MongoDatabase db = client.getDatabase("sampleDB");
        MongoCollection<Document> col = db.getCollection("sampleCollection");
        MongoCollection<Document> col2 = db.getCollection("matchesCollection");

        // Initialize Cloudinary uploader
        CloudinaryUploader cloudinaryUploader = new CloudinaryUploader();

        // Upload image to Cloudinary & get URL
        File imageFile = new File("/Users/user/Desktop/Images/Plushie.png");
        String imageUrl = cloudinaryUploader.uploadImage(imageFile);

        // Example insert to test that it shows up in sampleCollection
        Document sampleDoc = new Document("_id", UUID.randomUUID().toString()).append("name", "Yolanda Lee");
        Document sampleDoc2 = new Document("_id", UUID.randomUUID().toString()).append("age", "Legal");
        Document sampleDoc3 = new Document("_id", UUID.randomUUID().toString()).append("preference", "Hot Men");
        Document sampleDoc4 = new Document("_id", UUID.randomUUID().toString()).append("preferredAge", "Mature");

        col.drop();  // Clears existing documents before inserting new entries
        col.insertOne(sampleDoc);
        col.insertOne(sampleDoc2);
        col.insertOne(sampleDoc3);
        col.insertOne(sampleDoc4);

        Document documentWithId = new Document("_id", UUID.randomUUID().toString())
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

        col.insertOne(documentWithId);  // Inserts into samplesCollection

        Document matchDoc = new Document("userAId", new Document("$oid", "60b8d6c2f123456789abcdef"))  // Example ObjectId
                .append("userBId", new Document("$oid", "60b8d6c2f123456789abcdee"))
                .append("matchDate", "2024-11-11T11:77:11Z") // Example ISO date string
                .append("isActive", true);

        col2.insertOne(matchDoc);  // Inserts into matchesCollection

        if (imageUrl != null) {
            // Debug: Print the URL
            System.out.println("Image URL: " + imageUrl);

            // Create a document to store in MongoDB
            Document userDoc = new Document("_id", UUID.randomUUID().toString())
                    .append("userName", "Plushie")
                    .append("fullName", "Plushie Lee")
                    .append("image", imageUrl);  // Store URL image here

            // Insert document into MongoDB
            try {
                col.insertOne(userDoc);
                System.out.println("User document with image URL inserted successfully!");
            } catch (Exception e) {
                System.out.println("Error inserting document: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to upload image to Cloudinary.");
        }

        client.close();  // Close the MongoDB client
    }
}
