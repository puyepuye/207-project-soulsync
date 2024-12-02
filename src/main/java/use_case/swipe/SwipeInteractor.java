package use_case.swipe;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.User;
import entity.UserFactory;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import use_case.chat.ChatDataAccessInterface;

import java.util.ArrayList;

/**
 * The Swipe Password Interactor.
 */
public class SwipeInteractor implements SwipeInputBoundary {
    private final SwipeUserDataAccessInterface userDataAccessObject;
    private final SwipeOutputBoundary userPresenter;
    private final UserFactory userFactory;
    private final ChatDataAccessInterface chatDataAccessObject;

    public SwipeInteractor(SwipeUserDataAccessInterface swipeDataAccessInterface,
                           SwipeOutputBoundary swipeOutputBoundary,
                           UserFactory userFactory,
                           ChatDataAccessInterface chatDataAccessObject) {
        this.userDataAccessObject = swipeDataAccessInterface;
        this.userPresenter = swipeOutputBoundary;
        this.userFactory = userFactory;
        this.chatDataAccessObject = chatDataAccessObject;
    }


    @SuppressWarnings("checkstyle:FinalLocalVariable")
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

        final User user = userDataAccessObject.get(username);
        final User profileUser = userDataAccessObject.get(profileUsername);
        userDataAccessObject.updateLike(user, profileUser, liked);

        final SwipeOutputData swipeOutputData = new SwipeOutputData(liked, profileUsername, "", new ArrayList<>(), false);
        userPresenter.prepareSuccessView(swipeOutputData);
    }

    @Override
    public void saveMatch(SwipeInputData swipeInputData) {

        final String username = swipeInputData.getUsername();
        final String profileUsername = swipeInputData.getProfileUsername();
        System.out.println("saving match between: " + username + profileUsername);
        userDataAccessObject.saveMatch(username, profileUsername);
        chatDataAccessObject.createChat(username, profileUsername);
    }
}
