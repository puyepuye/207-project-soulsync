package data_access;

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
import entity.UserFactory;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.compatibility.CompatibilityUserDataAccessInterface;
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
                                               CompatibilityUserDataAccessInterface
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

    public DBUserDataAccessObject(UserFactory userFactory) {
        this.userFactory = userFactory;
        // No need to do anything to reinitialize a user list! The data is the cloud that may be miles away.
        Dotenv dotenv = Dotenv.load();
        String mongoUri = dotenv.get("MONGODB_URI");
        MongoClient client = MongoClients.create(mongoUri);
        MongoDatabase db = client.getDatabase("sampleDB");
        this.userCollection = db.getCollection("sampleCollection");
    }

    @Override
    public User get(String username) {
        Document query = new Document("username", username);
        Document userDoc = userCollection.find(query).first();

        if (userDoc != null) {
            String userName = userDoc.getString(USERNAME);
            String password = userDoc.getString(PASSWORD);

            return userFactory.create(userName,
                    password,
                    userDoc.getString("image"),
                    userDoc.getString("fullName"),
                    userDoc.getString("location"),
                    userDoc.getString("gender"),
                    (List<String>) userDoc.get("preferredGender"),
                    userDoc.getDate("dateOfBirth"),
                    (HashMap<String, Integer>) userDoc.get("preferredAge"),
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
                .append(PASSWORD, user.getPassword())
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
    public void updateLike(User user, User userSwipedOn, boolean like) {
        if (like) {
            Document swipedRightQuery = new Document("username", user.getUsername());
            Document swipedOnQuery = new Document("username", userSwipedOn.getUsername());

            Document swipedRightUpdate = new Document("$set", new Document("swipedRight", userSwipedOn.getUsername()));
            Document swipedOnUpdate = new Document("$set", new Document("swipedOn", user.getUsername()));

            userCollection.updateOne(swipedRightQuery, swipedRightUpdate);
            userCollection.updateOne(swipedOnQuery, swipedOnUpdate);
        }
        else {
            Document swipedLeftQuery = new Document("username", user.getUsername());
            Document swipedLeftUpdate = new Document("$set", new Document("swipedLeft", userSwipedOn.getUsername()));

            userCollection.updateOne(swipedLeftQuery, swipedLeftUpdate);
        }
    }
}
