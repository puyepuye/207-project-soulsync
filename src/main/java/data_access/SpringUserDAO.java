package data_access;

import app.*;
import data_access.repository.CustomMatchesRepository;
import data_access.repository.CustomUserRepository;
import entity.CommonUser;
import entity.CommonUserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.navbar.NavbarViewModel;
import interface_adapter.preferences.PreferencesViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.swipe.SwipeViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableMongoRepositories
public class SpringUserDAO implements CommandLineRunner {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    CustomUserRepository customUserRepository;

    @Autowired
    CustomMatchesRepository customMatchesRepository;

    //@Override

    public static void main(String[] args) {
        ApplicationContext contexto = new SpringApplicationBuilder(SpringUserDAO.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
        //SpringApplication.run(SpringUserDAO.class, args);
        System.out.println("Himom");
    }



    public void run(String... args) {

        // Unit test
        // System.out.println(customUserRepository.get("bob"));    // Tests getting
//        System.out.println(customUserRepository.get("hughjazz").getPassword());
//        ArrayList<String> preference = new ArrayList<>();
//        preference.add("bob");
//        Map<String, Integer> map = new HashMap<>();
//        Map<String, Boolean> pref = new HashMap<>();
//        ArrayList<String> tags = new ArrayList<>();
//        tags.add("blessed");
//        CommonUser newUser = new CommonUser("hughjazz","newPassword","...","Hugh Jass",
//                "Saskatchewan", "male", preference , new Date(2021, 12,2),
//                map, "I like turtles", pref, tags,null,null,null, null);
//
//        customUserRepository.changePassword(newUser);
//        System.out.println(customUserRepository.get("hughjazz").getPassword());
//        ObjectId userA = new ObjectId("60b8d6c2f123456789abcdef");
//        ObjectId userB = new ObjectId("60b8d6c2f123456789abcdee");
//        System.out.println(customMatchesRepository.getMatch(userA, userB));
//
//        ObjectId userC = new ObjectId("696969696blanlanla");
//        ObjectId userD = new ObjectId("420420240lololol");
//        CommonMatches cm = new CommonMatches(userC, userD, new Date(2021,12,11), true);
//        customMatchesRepository.saveMatch(cm);






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

        final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(new CommonUserFactory());

        final SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel,
                signupViewModel, preferencesViewModel, userDataAccessObject);
        views.add(signupView, signupView.getViewName());

        final LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, swipeViewModel,
                signupViewModel, compatibilityViewModel, userDataAccessObject);
        views.add(loginView, loginView.getViewName());

        final LoggedInView loggedInView = ChangePasswordUseCaseFactory.create(viewManagerModel,
                loggedInViewModel, userDataAccessObject);
        views.add(loggedInView, loggedInView.getViewName());

        final PreferenceView preferenceView = PreferenceUseCaseFactory.create(viewManagerModel,
                preferencesViewModel, swipeViewModel, userDataAccessObject);

        views.add(preferenceView, preferenceView.getViewName());

        final NavBarView navBarView = NavbarUseCaseFactory.create(viewManagerModel,
                swipeViewModel, navbarViewModel, compatibilityViewModel);

        views.add(navBarView, navBarView.getViewName());

        final SwipeView swipeView = SwipeUseCaseFactory.create(viewManagerModel,
                swipeViewModel, navbarViewModel, compatibilityViewModel, userDataAccessObject);

        views.add(swipeView, swipeView.getViewName());

        final CompatibilityView compatibilityView = CompatibilityUseCaseFactory.create(viewManagerModel,
                compatibilityViewModel, navbarViewModel, swipeViewModel, userDataAccessObject);

        views.add(compatibilityView, compatibilityView.getViewName());

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setSize(400, 600);
        application.setVisible(true);
    }

}
