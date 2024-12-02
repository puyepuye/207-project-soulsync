package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.ChatDataAccessObject;
import data_access.DBUserDataAccessObject;
import entity.CommonUserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.listchat.ListChatViewModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import interface_adapter.signup.SignupViewModel;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.edit_profile.EditProfileViewModel;

import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.navbar.NavbarViewModel;
import interface_adapter.preferences.PreferencesViewModel;
import interface_adapter.swipe.SwipeViewModel;
import view.*;

/**
 * The version of Main with an external database used to persist user data.
 */

@SuppressWarnings({"checkstyle:ClassDataAbstractionCoupling", "checkstyle:ClassFanOutComplexity", "checkstyle:AnnotationUseStyle"})
@ComponentScan(basePackages = {"interface_adapter.chat"})
@SpringBootApplication
public class MainWithDB implements CommandLineRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MainWithDB.class)
                .web(WebApplicationType.SERVLET)
                .headless(false)
                .run(args);
    }
    /**
     * The main method for starting the program with an external database used to persist user data.
     * @param args input to main
     */
    public void run(String... args) {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        final JFrame application = new JFrame("SoulSync 2024");
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
        final ChatViewModel chatViewModel = new ChatViewModel();

        final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(new CommonUserFactory());
        final ChatDataAccessObject chatDataAccessObject = new ChatDataAccessObject();

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
                loginViewModel, editProfileViewModel, preferencesViewModel, swipeViewModel, userDataAccessObject);

        views.add(editProfileView, editProfileView.getViewName());

        final ListChatView listChatView = ListChatUseCaseFactory.create(viewManagerModel,
                listChatViewModel, chatViewModel, chatDataAccessObject,
                navbarViewModel, swipeViewModel, compatibilityViewModel, editProfileViewModel);
        views.add(listChatView, listChatView.getViewName());

        final ChatView chatView = ChatUseCaseFactory.create(viewManagerModel, chatViewModel,
                listChatViewModel, chatDataAccessObject);
        System.out.println(chatView.getName());
        views.add(chatView, chatView.getName());

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        application.pack();
        final int height = 600;
        final int width = 400;
        application.setSize(width, height);
        application.setVisible(true);

    }
}
