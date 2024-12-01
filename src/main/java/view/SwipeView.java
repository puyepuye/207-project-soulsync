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

import data_access.DBUserDataAccessObject;
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

        // Get the current profile
        Document currentProfile = swipesList.get(currentProfileIndex);
        String swipedOnUsername = currentProfile.getString("username");
        System.out.println(swipedOnUsername);

        // Perform the swipe action
        swipeController.execute(isLike, swipeViewModel.getState().getUsername(), swipedOnUsername);

        if (isLike) {
            // Check if the current user's swipedRightOn list contains the swiped-on user's username
            SwipesListDataAccessObject dao = new SwipesListDataAccessObject();
            List<String> swipedRightOnList = dao.getSwipedRightOn(swipeViewModel.getState().getUsername());

            if (swipedRightOnList.contains(swipedOnUsername)) {
                swipeController.saveMatch(isLike, swipeViewModel.getState().getUsername(), swipedOnUsername);
                // Show a popup message for a match
                JOptionPane.showMessageDialog(this, "It's a Match!", "Match Found", JOptionPane.INFORMATION_MESSAGE);
            }
        }

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
