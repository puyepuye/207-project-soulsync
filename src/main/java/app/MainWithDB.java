package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import data_access.ChatDataAccessObject;
import data_access.DBUserDataAccessObject;
import entity.CommonUserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.edit_profile.EditProfileViewModel;
import interface_adapter.listchat.ListChatViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.navbar.NavbarViewModel;
import interface_adapter.preferences.PreferencesViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.swipe.SwipeViewModel;
import view.ChatView;
import view.CompatibilityView;
import view.EditProfileView;
import view.ListChatView;
import view.LoggedInView;
import view.LoginView;
import view.NavBarView;
import view.PreferenceView;
import view.SignupView;
import view.SwipeView;
import view.ViewManager;

/**
 * The version of Main that uses an external database to persist user data.
 * This class sets up the main program window and initializes various views
 * and their corresponding view models to create a multi-view application.
 */

@ComponentScan(basePackages = {"interface_adapter.chat"})
@SpringBootApplication
public class MainWithDB implements CommandLineRunner {

    /**
     * The main entry point of the application.
     * Configures the application to run as a non-headless Spring Boot application.
     *
     * @param args input arguments to the main method
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(MainWithDB.class)
                .web(WebApplicationType.SERVLET)
                .headless(false)
                .run(args);
    }

    /**
     * Sets up the main program window and initializes the application views,
     * view models, and their relationships.
     * This method is invoked after the Spring Boot application context is loaded.
     *
     * @param args arguments passed from the command line
     */
    @Override
    public void run(String... args) {
        // Build the main program window, main panel, and card layout.
        final JFrame application = new JFrame("SoulSync 2024");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final CardLayout cardLayout = new CardLayout();
        final JPanel views = new JPanel(cardLayout);
        application.add(views);

        // Initialize the ViewManagerModel to manage state transitions between views.
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // Initialize all required view models and data access objects.
        final LoginViewModel loginViewModel = new LoginViewModel();
        final LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        final SignupViewModel signupViewModel = new SignupViewModel();
        final PreferencesViewModel preferencesViewModel = new PreferencesViewModel();
        final SwipeViewModel swipeViewModel = new SwipeViewModel();
        final NavbarViewModel navbarViewModel = new NavbarViewModel();
        final CompatibilityViewModel compatibilityViewModel = new CompatibilityViewModel();
        final EditProfileViewModel editProfileViewModel = new EditProfileViewModel();
        final ListChatViewModel listChatViewModel = new ListChatViewModel();
        final ChatViewModel chatViewModel = new ChatViewModel();

        final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(new CommonUserFactory());
        final ChatDataAccessObject chatDataAccessObject = new ChatDataAccessObject();

        // Initialize and add views to the card layout container.
        final SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel,
                signupViewModel, preferencesViewModel, swipeViewModel, compatibilityViewModel,
                editProfileViewModel, userDataAccessObject);
        views.add(signupView, signupView.getViewName());

        final LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel,
                swipeViewModel, signupViewModel, compatibilityViewModel, editProfileViewModel,
                userDataAccessObject, listChatViewModel);
        views.add(loginView, loginView.getViewName());

        final LoggedInView loggedInView = ChangePasswordUseCaseFactory.create(viewManagerModel,
                loggedInViewModel, userDataAccessObject);
        views.add(loggedInView, loggedInView.getViewName());

        final PreferenceView preferenceView = PreferenceUseCaseFactory.create(viewManagerModel,
                preferencesViewModel, swipeViewModel, userDataAccessObject);
        views.add(preferenceView, preferenceView.getViewName());

        final NavBarView navBarView = NavbarUseCaseFactory.create(viewManagerModel, swipeViewModel,
                navbarViewModel, compatibilityViewModel, editProfileViewModel, listChatViewModel);
        views.add(navBarView, navBarView.getViewName());

        final SwipeView swipeView = SwipeUseCaseFactory.create(viewManagerModel,
                swipeViewModel, navbarViewModel, compatibilityViewModel, editProfileViewModel,
                userDataAccessObject, listChatViewModel, chatDataAccessObject);

        views.add(swipeView, swipeView.getViewName());

        final CompatibilityView compatibilityView = CompatibilityUseCaseFactory.create(viewManagerModel,
                compatibilityViewModel, navbarViewModel, swipeViewModel, editProfileViewModel,
                userDataAccessObject, listChatViewModel);
        views.add(compatibilityView, compatibilityView.getViewName());

        final EditProfileView editProfileView = EditProfileUseCaseFactory.create(viewManagerModel,
                loginViewModel, editProfileViewModel, preferencesViewModel, swipeViewModel, userDataAccessObject);
        views.add(editProfileView, editProfileView.getViewName());

        final ListChatView listChatView = ListChatUseCaseFactory.create(viewManagerModel, listChatViewModel,
                chatViewModel, chatDataAccessObject, navbarViewModel, swipeViewModel, compatibilityViewModel,
                editProfileViewModel);
        views.add(listChatView, listChatView.getViewName());

        final ChatView chatView = ChatUseCaseFactory.create(viewManagerModel, chatViewModel,
                listChatViewModel, chatDataAccessObject);
        views.add(chatView, chatView.getName());

        // Set the initial view and display the application window.
        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        application.pack();
        final int height = 600;
        final int width = 400;
        application.setSize(width, height);
        application.setVisible(true);

    }
}
