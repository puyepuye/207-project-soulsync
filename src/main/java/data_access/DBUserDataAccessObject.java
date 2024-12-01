package data_access;

import entity.Matches;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.*;
import java.util.List;
import java.util.Map;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import entity.User;
import entity.Matches;
import entity.UserFactory;
import entity.MatchesFactory;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.compatibility.CompatibilityUserDataAccessInterface;
import use_case.edit_profile.EditProfileUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.preferences.PreferenceUserDataAccessInterface;
import use_case.swipe.SwipeUserDataAccessInterface;

/**
 * The DAO for usser data.
 */
public class DBUserDataAccessObject implements SignupUserDataAccessInterface,
                                               LoginUserDataAccessInterface,
                                               ChangePasswordUserDataAccessInterface,
                                               PreferenceUserDataAccessInterface,
                                               SwipeUserDataAccessInterface,
                                               CompatibilityUserDataAccessInterface,
                                               EditProfileUserDataAccessInterface
{
    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private final UserFactory userFactory;
    private final MongoCollection<Document> userCollection;
    private final MongoCollection<Document> matchesCollection;

    public DBUserDataAccessObject(UserFactory userFactory) {
        this.userFactory = userFactory;
        // No need to do anything to reinitialize a user list! The data is the cloud that may be miles away.
        Dotenv dotenv = Dotenv.load();
        String mongoUri = dotenv.get("MONGODB_URI");
        MongoClient client = MongoClients.create(mongoUri);
        MongoDatabase db = client.getDatabase("sampleDB");
        this.userCollection = db.getCollection("sampleCollection");
        this.matchesCollection = db.getCollection("matchesCollection");
    }

    @Override
    public User get(String username) {
        Document query = new Document("username", username);
        Document userDoc = userCollection.find(query).first();
        if (userDoc != null) {
            String userName = userDoc.getString(USERNAME);
            String password = userDoc.getString(PASSWORD);

            HashMap<String, Integer> preferredAgeMap = null;
            if (userDoc.get("preferredAge") != null) {
                Document preferredAgeDoc = (Document) userDoc.get("preferredAge");

                // Initialize the HashMap
                preferredAgeMap = new HashMap<>();

                // Iterate through the Document and cast values to Integer
                for (String key : preferredAgeDoc.keySet()) {
                    Object value = preferredAgeDoc.get(key);
                    if (value instanceof Integer) {
                        preferredAgeMap.put(key, (Integer) value);
                    } else {
                        // Log or handle unexpected types
                        System.out.println("Unexpected value type for key " + key + ": " + value.getClass());
                    }
                }
            }
            return userFactory.create(userName,
                    password,
                    userDoc.getString("image"),
                    userDoc.getString("fullName"),
                    userDoc.getString("location"),
                    userDoc.getString("gender"),
                    (List<String>) userDoc.get("preferredGender"),
                    userDoc.getDate("dateOfBirth"),
                    preferredAgeMap,
                    userDoc.getString("bio"),
                    (Map<String, Boolean>) userDoc.get("preferences"),
                    (List<String>) userDoc.get("tags"),
                    (List<String>) userDoc.get("matched"),
                    (ArrayList<String>) userDoc.get("swipedRight"),
                    (ArrayList<String>) userDoc.get("swipedLeft"),
                    (ArrayList<String>) userDoc.get("swipedOn")
            );
        }
        return null;  // User is NOT found
    }

    @Override
    public void setCurrentUser(String name) { }

    @Override
    public String getCurrentUser() {
        return "";
    }

    @Override
    public boolean existsByName(String username) {
        Document query = new Document("username", username);
        return userCollection.find(query).first() != null;
    }


    public void saveUser(User user) {
        Document userDoc = new Document("username", user.getUsername())
                .append("password", user.getPassword())
                .append("image", user.getImage())
                .append("fullName", user.getFullname())
                .append("location", user.getLocation())
                .append("gender", user.getGender())
                .append("preferredGender", user.getPreferredGender())
                .append("dateOfBirth", user.getDateOfBirth())
                .append("preferredAge", user.getPreferredAge())
                .append("bio", user.getBio())
                .append("preferences", user.getPreferences())
                .append("tags", user.getTags())
                .append("matched", user.getMatched())
                .append("swipedRight", user.getSwipedRight())
                .append("swipedLeft", user.getSwipedLeft())
                .append("swipedRightOn", user.getSwipedRightOn());
        userCollection.insertOne(userDoc);

    }

    public void saveMatch(String usernameA, String usernameB) {
        Document matchDoc = new Document("usernameA", usernameA)
                .append("usernameB", usernameB);
        matchesCollection.insertOne(matchDoc);
    }

    @Override
    public void updatePreference(User user) {
        Document query = new Document("username", user.getUsername());
        Document update = new Document("$set", new Document("tags", user.getTags())
                .append("preferences", user.getPreferences())
                .append("preferredAge", user.getPreferredAge())
                .append("bio", user.getBio())
                .append("preferredGender", user.getPreferredGender()));

        userCollection.updateOne(query, update);
    }

    @Override
    public void changePassword(User user) {
        Document query = new Document("username", user.getUsername());
        Document update = new Document("$set", new Document("password", user.getPassword()));
        userCollection.updateOne(query, update);
    }

    @Override
    public void changeImage(User user) {
        Document query = new Document("username", user.getUsername());
        Document update = new Document("$set", new Document("image", user.getImage()));
        userCollection.updateOne(query, update);
    }

    @Override
    public void changeFullname(User user) {
        Document query = new Document("username", user.getUsername());
        Document update = new Document("$set", new Document("fullName", user.getFullname()));
        userCollection.updateOne(query, update);
    }

    @Override
    public void changeLocation(User user) {
        Document query = new Document("username", user.getUsername());
        Document update = new Document("$set", new Document("location", user.getLocation()));
        userCollection.updateOne(query, update);
    }

    @Override
    public void changeGender(User user) {
        Document query = new Document("username", user.getUsername());
        Document update = new Document("$set", new Document("gender", user.getGender()));
        userCollection.updateOne(query, update);
    }

    @Override
    public void changePreferredGender(User user) {
        Document query = new Document("username", user.getUsername());
        Document update = new Document("$set", new Document("preferredGender", user.getPreferredGender()));
        userCollection.updateOne(query, update);
    }

    @Override
    public void changeDOB(User user) {
        Document query = new Document("username", user.getUsername());
        Document update = new Document("$set", new Document("dateOfBirth", user.getDateOfBirth()));
        userCollection.updateOne(query, update);
    }

    @Override
    public void changePreferredAge(User user) {
        Document query = new Document("username", user.getUsername());
        Document update = new Document("$set", new Document("preferredAge", user.getPreferredAge()));
        userCollection.updateOne(query, update);
    }

    @Override
    public void updateLike(User user, User userSwipedOn, boolean like) {
        if (like) {
            // Add userSwipedOn's username to the swipedRight array of the current user
            Document swipedRightQuery = new Document("username", user.getUsername());
            Document swipedRightUpdate = new Document("$push", new Document("swipedRight", userSwipedOn.getUsername()));
            userCollection.updateOne(swipedRightQuery, swipedRightUpdate);

            // Add the current user's username to the swipedOn array of the userSwipedOn
            Document swipedOnQuery = new Document("username", userSwipedOn.getUsername());
            Document swipedOnUpdate = new Document("$push", new Document("swipedRightOn", user.getUsername()));
            userCollection.updateOne(swipedOnQuery, swipedOnUpdate);
        } else {
            // Add userSwipedOn's username to the swipedLeft array of the current user
            Document swipedLeftQuery = new Document("username", user.getUsername());
            Document swipedLeftUpdate = new Document("$push", new Document("swipedLeft", userSwipedOn.getUsername()));
            userCollection.updateOne(swipedLeftQuery, swipedLeftUpdate);
        }
    }

}
