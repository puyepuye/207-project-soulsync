package data_access.repository;

import com.mongodb.client.result.UpdateResult;
import entity.CommonMatches;
import entity.CommonUser;
import entity.User;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.compatibility.CompatibilityUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.preferences.PreferenceUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.swipe.SwipeUserDataAccessInterface;

@Component
public class CustomUserRepositoryImpl implements CustomUserRepository,
                                            SignupUserDataAccessInterface,
                                            LoginUserDataAccessInterface,
                                            ChangePasswordUserDataAccessInterface,
                                            PreferenceUserDataAccessInterface,
                                            SwipeUserDataAccessInterface,
                                            CompatibilityUserDataAccessInterface {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean existsByName(String username) {
        return false;
    }

    @Override
    public void saveUser(User user) {
        mongoTemplate.save(user);
    }

    @Override
    public User get(String username) {
        System.out.println("Gesundheit!");
        Query query = new Query(Criteria.where("userName").is(username));
        return mongoTemplate.findOne(query, CommonUser.class);
    }

    @Override
    public void updatePreference(User user) {
        Query query = new Query(Criteria.where("userName").is(user.getUsername()));
        Update update = new Update();
        // get information from the user object.
        update.set("bio", user.getBio());
        update.set("preferences", user.getPreferences());
        update.set("preferredAge", user.getPreferredGender());
        update.set("preferredGender", user.getPreferredGender());
        UpdateResult result = mongoTemplate.updateFirst(query, update, CommonUser.class);

        System.out.println(result.getModifiedCount() + " document(s) updated..");
    }

    @Override
    public void setCurrentUser(String name) {

    }

    @Override
    public String getCurrentUser() {
        return "";
    }

    @Override
    public void changePassword(User user) {
        Query query = new Query(Criteria.where("userName").is(user.getUsername()));
        Update update = new Update();
        update.set("password", user.getPassword());
        UpdateResult result = mongoTemplate.updateFirst(query, update, CommonUser.class);

        System.out.println(result.getModifiedCount() + " document(s) updated..");
    }

    @Override
    public void updateLike(User user, User userSwipedOn, boolean like) {
        if (like) {
            Query swipedRightQuery = new Query(Criteria.where("userName").is(user.getUsername()));
            Query swipedOnQuery = new Query(Criteria.where("userName").is(userSwipedOn.getUsername()));

            Update swipedRightUpdate = new Update();
            swipedRightUpdate.set("swipedRight", userSwipedOn.getUsername());
            Update swipedOnUpdate = new Update();
            swipedOnUpdate.set("swipedRight", userSwipedOn.getUsername());

            mongoTemplate.updateFirst(swipedRightQuery, swipedRightUpdate, CommonUser.class);
            mongoTemplate.updateFirst(swipedOnQuery, swipedOnUpdate, CommonUser.class);
        }
        else {
            Query swipedLeftQuery = new Query(Criteria.where("userName").is(user.getUsername()));
            Update swipedLeftUpdate = new Update();
            swipedLeftUpdate.set("swipedLeft", userSwipedOn.getUsername());
            mongoTemplate.updateFirst(swipedLeftQuery, swipedLeftUpdate, CommonUser.class);
        }
    }

}
