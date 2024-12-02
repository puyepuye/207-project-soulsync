package data_access;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import use_case.compatibility.CompatibilityCalculator;

public class SwipesListDataAccessObject {

    private final MongoCollection<Document> userCollection;

    public SwipesListDataAccessObject() {
        // Load environment variables and connect to MongoDB
        Dotenv dotenv = Dotenv.load();
        String mongoUri = dotenv.get("MONGODB_URI");
        MongoClient client = MongoClients.create(mongoUri);
        MongoDatabase db = client.getDatabase("sampleDB");
        this.userCollection = db.getCollection("sampleCollection");
    }

    /**
     * Retrieve a user object by their username
     * @param username The username of the user
     * @return A Document representing the user object
     */
    public Document getUserByUsername(String username) {
        Document query = new Document("username", username);
        return userCollection.find(query).first();
    }

    /**
     * Retrieve the preferences of a user by their username
     * @param username The username of the user
     * @return A Map<String, Boolean> representing the user's preferences, or null if the user is not found
     */
    public Map<String, Boolean> getUserPreferences(String username) {
        // Retrieve the user document
        Document user = getUserByUsername(username);

        if (user == null) {
            System.out.println("User not found!");
            return null; // Return null if the user doesn't exist
        }

        // Extract the preferences field as a Document
        Document preferencesDoc = (Document) user.get("preferences");
        if (preferencesDoc == null) {
            System.out.println("Preferences not found for user: " + username);
            return null; // Return null if the preferences field is missing
        }

        // Convert Document to HashMap<String, Boolean>
        Map<String, Boolean> preferencesMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : preferencesDoc.entrySet()) {
            preferencesMap.put(entry.getKey(), (Boolean) entry.getValue());
        }

        return preferencesMap;
    }

    /**
     * Generate a swiping list for a specific user, ranked by compatibility
     * @param username The username of the user
     * @return A list of users matching the preferred criteria, ranked by compatibility
     */
    public List<String> getSwipedRightOn(String username) {
        Document query = new Document("username", username);
        Document user = userCollection.find(query).first();

        if (user != null && user.containsKey("swipedRightOn")) {
            return user.getList("swipedRightOn", String.class); // Assumes swipedRightOn is stored as a list
        }

        return new ArrayList<>(); // Return an empty list if no data is found
    }

    /**
     * Generate a swiping list for a specific user, ranked by compatibility.
     * @param username The username of the user
     * @return A list of users matching the preferred criteria, ranked by compatibility
     */
    public List<Document> generateSwipes(String username) {
        Document user = getUserByUsername(username);
        if (user == null) {
            System.out.println("User not found!");
            return new ArrayList<>();
        }

        Map<String, Boolean> currentUserPreferences = getUserPreferences(username);
        if (currentUserPreferences == null) {
            System.out.println("No preferences found for the current user!");
            return new ArrayList<>();
        }

        List<String> preferredGenders = (List<String>) user.get("preferredGender");
        List<String> swipedRight = (List<String>) user.get("swipedRight");
        List<String> swipedLeft = (List<String>) user.get("swipedLeft");
        List<String> swipedRightOn = (List<String>) user.get("swipedRightOn");


        Document filters = new Document("$and", List.of(
                new Document("gender", new Document("$in", preferredGenders)), // Match preferred genders
                new Document("username", new Document("$ne", username)),      // Exclude the current user
                new Document("username", new Document("$nin", swipedRight)),  // Exclude users already swiped right
                new Document("username", new Document("$nin", swipedLeft))    // Exclude users already swiped left
        ));
        // Query with Filters
        MongoCursor<Document> cursor = userCollection.find(filters).iterator();
        List<Document> swipingList = new ArrayList<>();
        while (cursor.hasNext()) {
            swipingList.add(cursor.next());
        }
        // Step 6: Create a map to store compatibility scores
        Map<Document, Integer> userScores = new HashMap<>();

        for (Document swipingUser : swipingList) {
            String swipingUsername = swipingUser.getString("username");

            Document preferencesDoc = (Document) swipingUser.get("preferences");
            Map<String, Boolean> swipingUserPreferences = new HashMap<>();
            for (Map.Entry<String, Object> entry : preferencesDoc.entrySet()) {
                swipingUserPreferences.put(entry.getKey(), (Boolean) entry.getValue());
            }

            if (swipingUserPreferences == null) {
                continue;
            }

            CompatibilityCalculator compatibilityCalculator = new CompatibilityCalculator(
                    currentUserPreferences,
                    swipingUserPreferences
            );
            int compatibilityScore = compatibilityCalculator.calculate();
            userScores.put(swipingUser, compatibilityScore);
        }

        List<Document> sortedList = userScores.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // Sort by compatibility score
                .map(Map.Entry::getKey) // Extract sorted documents
                .collect(Collectors.toList());

                List<Document> prioritizedList = new ArrayList<>();
                for (Document sortedUser : sortedList) {
                    if (swipedRightOn.contains(sortedUser.getString("username"))) {
                        prioritizedList.add(sortedUser);
                    }
                }
                for (Document sortedUser : sortedList) {
                    if (!swipedRightOn.contains(sortedUser.getString("username"))) {
                        prioritizedList.add(sortedUser);
                    }
                }

        return prioritizedList;

    }

    public static void main(String[] args) {
        // Example: Generate a swiping list for a user with username "puye"
        String username = "puye";
        SwipesListDataAccessObject dao = new SwipesListDataAccessObject();
        List<Document> swipesList = dao.generateSwipes(username);

        // Iterate through the list and print each object on a new line
        for (Document swipe : swipesList) {
            System.out.println(swipe.toJson()); // Use toJson() to format the MongoDB Document
        }
    }
}
