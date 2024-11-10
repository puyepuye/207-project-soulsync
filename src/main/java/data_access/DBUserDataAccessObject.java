package data_access;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Map;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import entity.User;
import entity.UserFactory;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.preferences.PreferenceUserDataAccessInterface;
/**
 * The DAO for user data.
 */
public class DBUserDataAccessObject implements SignupUserDataAccessInterface,
                                               LoginUserDataAccessInterface,
                                               ChangePasswordUserDataAccessInterface, PreferenceUserDataAccessInterface
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
        // Make an API call to get the user object.
//        final OkHttpClient client = new OkHttpClient().newBuilder().build();
//        final Request request = new Request.Builder()
//                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", username))
//                .addHeader("Content-Type", CONTENT_TYPE_JSON)
//                .build();
//        try {
//            final Response response = client.newCall(request).execute();
//
//            final JSONObject responseBody = new JSONObject(response.body().string());
//
//            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
//                final JSONObject userJSONObject = responseBody.getJSONObject("user");
//                final String name = userJSONObject.getString(USERNAME);
//                final String password = userJSONObject.getString(PASSWORD);
//
//                return userFactory.create(name, password);
//            }
//            else {
//                throw new RuntimeException(responseBody.getString(MESSAGE));
//            }
//        }
//        catch (IOException | JSONException ex) {
//            throw new RuntimeException(ex);
//        }
        Document query = new Document("username", username);
        Document userDoc = userCollection.find(query).first();

        if (userDoc != null) {
            String userName = userDoc.getString(USERNAME);
            String password = userDoc.getString(PASSWORD);

            return userFactory.create(userName, password, userDoc.getString("image"), userDoc.getString("fullName"),
                    userDoc.getString("location"), userDoc.getString("gender"), (List<String>) userDoc.get("preferredGender"),
                    userDoc.getDate("dateOfBirth"), (Map<String, Integer>) userDoc.get("preferredAge"), userDoc.getString("bio"),
                    (Map<String, Boolean>) userDoc.get("preferences"), (List<String>) userDoc.get("tags"), (List<String>) userDoc.get("matched"));
        }
        return null;  // User is NOT found
    }

    @Override
    public void setCurrentUser(String name) {

    }

    @Override
    public String getCurrentUser() {
        return "";
    }

    @Override
    public boolean existsByName(String username) {
//        final OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        final Request request = new Request.Builder()
//                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/checkIfUserExists?username=%s", username))
//                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
//                .build();
//        try {
//            final Response response = client.newCall(request).execute();
//
//            final JSONObject responseBody = new JSONObject(response.body().string());
//
//            //                throw new RuntimeException(responseBody.getString("message"));
//            return responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE;
//        }
//        catch (IOException | JSONException ex) {
//            throw new RuntimeException(ex);
//        }
        Document query = new Document("username", username);
        return userCollection.find(query).first() != null;
    }

    @Override
    public void save(User user) {
//        final OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//
//        // POST METHOD
//        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
//        final JSONObject requestBody = new JSONObject();
//        requestBody.put(USERNAME, user.getFullName());
//        requestBody.put(PASSWORD, user.getPassword());
//        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
//        final Request request = new Request.Builder()
//                .url("http://vm003.teach.cs.toronto.edu:20112/user")
//                .method("POST", body)
//                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
//                .build();
//        try {
//            final Response response = client.newCall(request).execute();
//
//            final JSONObject responseBody = new JSONObject(response.body().string());
//
//            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
//                // success!
//            }
//            else {
//                throw new RuntimeException(responseBody.getString(MESSAGE));
//            }
//        }
//        catch (IOException | JSONException ex) {
//            throw new RuntimeException(ex);
//        }
        Document userDoc = new Document("username", user.getUsername())
                .append(PASSWORD, user.getPassword())
                .append("image", user.getImage())
                .append("fullName", user.getUsername())
                .append("location", user.getLocation())
                .append("gender", user.getGender())
                .append("preferredGender", user.getPreferredGender())
                .append("dateOfBirth", user.getDateOfBirth())
                .append("preferredAge", user.getPreferredAge())
                .append("bio", user.getBio())
                .append("preferences", user.getPreferences())
                .append("tags", user.getTags())
                .append("matched", user.getMatched());
        userCollection.insertOne(userDoc);

    }

    @Override
    public void changePassword(User user) {
//        final OkHttpClient client = new OkHttpClient().newBuilder()
//                                        .build();
//
//        // POST METHOD
//        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
//        final JSONObject requestBody = new JSONObject();
//        requestBody.put(USERNAME, user.getFullName());
//        requestBody.put(PASSWORD, user.getPassword());
//        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
//        final Request request = new Request.Builder()
//                                    .url("http://vm003.teach.cs.toronto.edu:20112/user")
//                                    .method("PUT", body)
//                                    .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
//                                    .build();
//        try {
//            final Response response = client.newCall(request).execute();
//
//            final JSONObject responseBody = new JSONObject(response.body().string());
//
//            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
//                // success!
//            }
//            else {
//                throw new RuntimeException(responseBody.getString(MESSAGE));
//            }
//        }
//        catch (IOException | JSONException ex) {
//            throw new RuntimeException(ex);
//        }
        Document query = new Document("username", user.getUsername());
        Document update = new Document("$set", new Document("password", user.getPassword()));
        userCollection.updateOne(query, update);

    }
}
