package data_access;

import app.*;
import entity.CommonUserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.edit_profile.EditProfileViewModel;
import interface_adapter.listchat.ListChatViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.navbar.NavbarViewModel;
import interface_adapter.preferences.PreferencesViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.swipe.SwipeViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableMongoRepositories
public class SpringUserDAO implements CommandLineRunner {


    //@Override

    public static void main(String[] args) {
    new SpringApplicationBuilder(SpringUserDAO.class)
                .web(WebApplicationType.SERVLET)
                .headless(false)
                .run(args);
        //SpringApplication.run(SpringUserDAO.class, args); System.out.println("Himom");
    }



    public void run(String... args) {
        // Claire's stuff
        final JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        final JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // The data for the views, such as username and password, are in the ViewModels.
        // This information will be changed by a presenter object that is reporting the
        // results from the use case. The ViewModels are "observable", and will
        // be "observed" by the Views.
        final LoginViewModel loginViewModel = new LoginViewModel();
        final LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        final SignupViewModel signupViewModel = new SignupViewModel();
        final PreferencesViewModel preferencesViewModel = new PreferencesViewModel();
        final SwipeViewModel swipeViewModel = new SwipeViewModel();
        final NavbarViewModel navbarViewModel = new NavbarViewModel();
        final CompatibilityViewModel compatibilityViewModel = new CompatibilityViewModel();
        final EditProfileViewModel editProfileViewModel = new EditProfileViewModel();
        final ListChatViewModel listChatViewModel = new ListChatViewModel();

        final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(new CommonUserFactory());

        final SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel,
                signupViewModel, preferencesViewModel, swipeViewModel, compatibilityViewModel, editProfileViewModel, userDataAccessObject);
        views.add(signupView, signupView.getViewName());

        final LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, swipeViewModel,
                signupViewModel, compatibilityViewModel, editProfileViewModel, userDataAccessObject, listChatViewModel);
        views.add(loginView, loginView.getViewName());

        final LoggedInView loggedInView = ChangePasswordUseCaseFactory.create(viewManagerModel,
                loggedInViewModel, userDataAccessObject);
        views.add(loggedInView, loggedInView.getViewName());

        final PreferenceView preferenceView = PreferenceUseCaseFactory.create(viewManagerModel,
                preferencesViewModel, swipeViewModel, userDataAccessObject);

        views.add(preferenceView, preferenceView.getViewName());

        final NavBarView navBarView = NavbarUseCaseFactory.create(viewManagerModel,
                swipeViewModel, navbarViewModel, compatibilityViewModel, editProfileViewModel, listChatViewModel);

        views.add(navBarView, navBarView.getViewName());

        final SwipeView swipeView = SwipeUseCaseFactory.create(viewManagerModel,
                swipeViewModel, navbarViewModel, compatibilityViewModel, editProfileViewModel, userDataAccessObject, listChatViewModel);

        views.add(swipeView, swipeView.getViewName());

        final CompatibilityView compatibilityView = CompatibilityUseCaseFactory.create(viewManagerModel,
                compatibilityViewModel, navbarViewModel, swipeViewModel, editProfileViewModel, userDataAccessObject, listChatViewModel);

        views.add(compatibilityView, compatibilityView.getViewName());

        final EditProfileView editProfileView = EditProfileUseCaseFactory.create(viewManagerModel,
                loginViewModel, editProfileViewModel, preferencesViewModel, userDataAccessObject);

        views.add(editProfileView, editProfileView.getViewName());

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setSize(400, 600);
        application.setVisible(true);
    }

}

@RestController
class Controller {
    public String convertMillisToDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = new Date(millis);
        return sdf.format(date);
    }

    @PostMapping("/")
    public void receiveMessages(@RequestBody String body) throws JSONException {
        JSONObject json = new JSONObject(body);

        // Access the "payload" object
        JSONObject payload = json.getJSONObject("payload");

        // Retrieve "message" and "created_at" values
        String message = payload.getString("message");
        String createdAt = convertMillisToDate(payload.getLong("created_at"));
        String senderUsername = json.getJSONObject("sender").getString("user_id");

        System.out.println("message : " + message);
        System.out.println("createdAt : " + createdAt);
        System.out.println("senderUsername : " + senderUsername);


        //notificationService.processNotification(message);
    }
}
