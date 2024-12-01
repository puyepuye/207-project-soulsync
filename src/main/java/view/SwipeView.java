package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data_access.SwipesListDataAccessObject;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.navbar.NavbarController;
import interface_adapter.swipe.SwipeController;
import interface_adapter.swipe.SwipeState;
import interface_adapter.swipe.SwipeViewModel;
import org.bson.Document;

import static view.ProfilePopup.createProfilePanel;
public class SwipeView extends JPanel implements PropertyChangeListener {
    private final String viewName = "swipe";

    private final SwipeViewModel swipeViewModel;
    private final SwipeController swipeController;

    private final JButton like;
    private final JButton dislike;

    private List<Document> swipesList; // Store the list of profiles
    private int currentProfileIndex; // Track the current profile index

    private JPanel profilePopupPanel; // Modular profile panel

    public SwipeView(SwipeController controller, SwipeViewModel swipeViewModel, NavbarController navbarController) {
        this.swipeController = controller;
        this.swipeViewModel = swipeViewModel;
        swipeViewModel.addPropertyChangeListener(this);

        // Initialize components
        currentProfileIndex = 0; // Start at the first profile
        like = new JButton(SwipeViewModel.LIKE_BUTTON_LABEL);
        dislike = new JButton(SwipeViewModel.DISLIKE_BUTTON_LABEL);

        // Initialize layout and navbar
        this.setLayout(new BorderLayout());
        NavBarView navBarView = new NavBarView(navbarController);

        // Buttons Panel
        JPanel buttons = new JPanel();
        buttons.add(dislike);
        buttons.add(like);

        // Add button listeners
        dislike.addActionListener(evt -> handleSwipe(false));
        like.addActionListener(evt -> handleSwipe(true));

        // Add components to the main layout
        this.add(buttons, BorderLayout.SOUTH);
        this.add(navBarView, BorderLayout.NORTH);
    }

    /**
     * Handle swipe actions (like/dislike).
     * @param isLike True if the user likes the profile, false otherwise.
     */
    private void handleSwipe(boolean isLike) {
        if (swipesList == null || swipesList.isEmpty() || currentProfileIndex >= swipesList.size()) {
            JOptionPane.showMessageDialog(this, "No more profiles to swipe.");
            return;
        }

        // Get the current profile and process the swipe action
        Document currentProfile = swipesList.get(currentProfileIndex);
        swipeController.execute(isLike, swipeViewModel.getState().getUsername(), currentProfile.getString("username"));

        // Move to the next profile
        currentProfileIndex++;
        if (currentProfileIndex < swipesList.size()) {
            updateProfileView(swipesList.get(currentProfileIndex));
        } else {
            JOptionPane.showMessageDialog(this, "No more profiles to display.");
            this.remove(profilePopupPanel);
            this.revalidate();
            this.repaint();
        }
    }

    /**
     * Update the profile view with the given profile data.
     * @param profile The profile data to display.
     */
    private void updateProfileView(Document profile) {
        if (profilePopupPanel != null) {
            this.remove(profilePopupPanel); // Remove the old profile panel
        }

        // Extract profile data from the document
        String image = profile.getString("image");
        String fullName = profile.getString("fullName");
        String location = profile.getString("location");
        String gender = profile.getString("gender");
        Date dateOfBirth = profile.getDate("dateOfBirth");
        String bio = profile.getString("bio");
        List<String> tags = profile.getList("tags", String.class);

        // Create a new profile panel with the updated data
        profilePopupPanel = createProfilePanel(image, fullName, location, gender, dateOfBirth, bio, tags);
        this.add(profilePopupPanel, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            SwipeState state = (SwipeState) evt.getNewValue();

            // Fetch the swipe list when state changes
            SwipesListDataAccessObject dao = new SwipesListDataAccessObject();
            swipesList = dao.generateSwipes(state.getUsername());

            // Reset the index and load the first profile
            currentProfileIndex = 0;
            if (!swipesList.isEmpty()) {
                updateProfileView(swipesList.get(currentProfileIndex));
            } else {
                JOptionPane.showMessageDialog(this, "No profiles available to display.");
            }
        }
    }

    public String getViewName() {
        return viewName;
    }
}

//
///**
// * The View for the Swipe Use Case.
// */
//public class SwipeView extends JPanel implements PropertyChangeListener {
//    private final String viewName = "swipe";
//
//    private final SwipeViewModel swipeViewModel;
//    private final SwipeController swipeController;
//
//    private final JLabel profileName;
//    private final JLabel profileBio;
//    private final JLabel profileTag;
//
//    private final JButton like;
//    private final JButton dislike;
//
//    public SwipeView(SwipeController controller, SwipeViewModel swipeViewModel, NavbarController navbarController) {
//        this.swipeController = controller;
//        this.swipeViewModel = swipeViewModel;
//        swipeViewModel.addPropertyChangeListener(this);
//
//        profileName = new JLabel();
//        profileBio = new JLabel();
//        profileTag = new JLabel();
//
//        final JPanel buttons = new JPanel();
//        dislike = new JButton(SwipeViewModel.DISLIKE_BUTTON_LABEL);
//        buttons.add(dislike);
//        like = new JButton(SwipeViewModel.LIKE_BUTTON_LABEL);
//        buttons.add(like);
//
//        //show profilepopup
//        // Sample data
//        String image = "http://res.cloudinary.com/dvf7ebgzz/image/upload/v1731963858/azdspzv2ttkqqj7y2yka.jpg";
//        // Replace with actual image path
//        String fullName = "John Doe";
//        String location = "New York, USA";
//        String gender = "Male";
//        Date dateOfBirth = new Date(90, 5, 15); // June 15, 1990
//        String bio = "I am a software developer who loves coding, traveling, and gaming.";
//        List<String> tags = List.of("Tech Enthusiast", "Gamer", "Travel");
//
//        // Show the profile dialog with sample data
//        //JPanel popupView = new ProfilePopup.createProfilePanel(image, fullName, location, gender, dateOfBirth, bio, tags);
//
//        JPanel profilePopupPanel = createProfilePanel(image, fullName, location, gender, dateOfBirth, bio, tags);
//        NavBarView navBarView = new NavBarView(navbarController);
//
//        dislike.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(dislike)) {
//                            SwipeState currentState = swipeViewModel.getState();
//                            currentState.setLiked(false);  // Set liked to false for dislike action
//                            swipeController.execute(currentState.getLiked(),
//                                    currentState.getUsername(),
//                                    currentState.getProfileName());
//                        }
//                    }
//                }
//        );
//
//        like.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(like)) {
//                            SwipeState currentState = swipeViewModel.getState();
//                            currentState.setLiked(true);  // Set liked to true for like action
//                            swipeController.execute(currentState.getLiked(),
//                                    currentState.getUsername(),
//                                    currentState.getProfileName());
//                        }
//                    }
//                }
//        );
//
//        // Use BorderLayout for layout management
//        this.setLayout(new BorderLayout());
//
//        // Add the profile info and buttons to the center of the screen
//        JPanel profilePanel = new JPanel();
//        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
//        profilePanel.add(profileName);
//        profilePanel.add(profileBio);
//        profilePanel.add(profileTag);  // Added tag label
//        profilePanel.add(buttons);
//
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        profilePopupPanel.setPreferredSize(new Dimension(310, 385));
//        this.add(profilePopupPanel);
//        this.add(profilePanel);
//        this.add(navBarView, BorderLayout.SOUTH);
//    }
//
//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//        final SwipeState state = (SwipeState) evt.getNewValue();
//
//        if (evt.getPropertyName().equals("state")) {
//            // Fetch profile data and update the UI
//            profileName.setText(state.getProfileName());
//            profileBio.setText(state.getProfileBio());
//            profileTag.setText(state.getProfileTags());
//
//            SwipesListDataAccessObject dao = new SwipesListDataAccessObject();
//            List<Document> swipesList = dao.generateSwipes(state.getUsername());
//
//            // Iterate through the list and print each object on a new line
//            for (Document swipe : swipesList) {
//                System.out.println(swipe.toJson()); // Use toJson() to format the MongoDB Document
//            }
//
//        }
//
//        if (state.getLikedError() != null) {
//            JOptionPane.showMessageDialog(this, state.getLikedError());
//        }
//    }
//
//    public String getViewName() {
//        return viewName;
//    }
//}
