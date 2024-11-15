package data_access;

import data_access.repository.UserRepo;
import entity.CommonUser;
import entity.User;
import entity.mackeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.compatibility.CompatibilityUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.preferences.PreferenceUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.swipe.SwipeUserDataAccessInterface;

@SpringBootApplication
@EnableMongoRepositories
public class SpringUserDAO  {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    UserRepo userRepo;

    //@Override
    public void changePassword(User user) {
        CommonUser optionalUser = userRepo.findByusername(user.getUsername());

        if (optionalUser != null) {

            // Create a new CommonUser instance with the updated password
            CommonUser updatedUser = new CommonUser(
                    optionalUser.getUsername(),
                    user.getPassword(),
                    optionalUser.getImage(),
                    optionalUser.getFullname(),
                    optionalUser.getLocation(),
                    optionalUser.getGender(),
                    optionalUser.getPreferredGender(),
                    optionalUser.getDateOfBirth(),
                    optionalUser.getPreferredAge(),
                    optionalUser.getBio(),
                    optionalUser.getPreferences(),
                    optionalUser.getTags(),
                    optionalUser.getMatched(),
                    optionalUser.getSwipedRight(),
                    optionalUser.getSwipedLeft(),
                    optionalUser.getSwipedRightOn()
            );

            userRepo.save((CommonUser) updatedUser);  // Save the updated user
        }

    }

    //@Override
    public User get(String username) {
        return (User) userRepo.findByusername(username);
    }

    //@Override
    public void updatePreference(User user) {

    }

    //@Override
    public void setCurrentUser(String name) {

    }

    //@Override
    public String getCurrentUser() {
        return "";
    }

    //@Override
    public boolean existsByName(String username) {
        return userRepo.findByusername(username) != null;
    }


    //@Override
    public void updateLike(User user, User userSwipedOn, boolean like) {

    }

    public static void main(String[] args) {
        SpringApplication.run(SpringUserDAO.class, args);


    }



    public void run(String... args) throws Exception {
        System.out.println(userRepo.findAll());
    }

}
