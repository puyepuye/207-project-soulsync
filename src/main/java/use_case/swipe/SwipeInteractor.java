package use_case.swipe;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.User;
import entity.UserFactory;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

import java.util.ArrayList;

/**
 * The Swipe Password Interactor.
 */
public class SwipeInteractor implements SwipeInputBoundary {
    private final SwipeUserDataAccessInterface userDataAccessObject;
    private final SwipeOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SwipeInteractor(SwipeUserDataAccessInterface swipeDataAccessInterface,
                                    SwipeOutputBoundary swipeOutputBoundary,
                                    UserFactory userFactory) {
        this.userDataAccessObject = swipeDataAccessInterface;
        this.userPresenter = swipeOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SwipeInputData swipeInputData) {
        Dotenv dotenv = Dotenv.load();
        String mongoUri = dotenv.get("MONGODB_URI");

        MongoClient client = MongoClients.create(mongoUri);
        MongoDatabase db = client.getDatabase("sampleDB");
        MongoCollection<Document> col = db.getCollection("sampleCollection");

        final String username = swipeInputData.getUsername();
        final String password = swipeInputData.getPassword();
        final String profileUsername = swipeInputData.getProfileUsername();
        final boolean liked = swipeInputData.getLiked();

        Document query = new Document("username", profileUsername);
        Document userDoc = col.find(query).first();
        String profilePassword = userDoc.getString("password");

        final User user = userFactory.create(username, password);
        final User profileUser = userFactory.create(profileUsername, profilePassword);

        userDataAccessObject.updateLike(user, profileUser, liked);

        final SwipeOutputData swipeOutputData = new SwipeOutputData(liked, profileUsername, "", new ArrayList<>(), false);
        userPresenter.prepareSuccessView(swipeOutputData);
    }
}
