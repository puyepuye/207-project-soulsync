package view;

import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfileViewModel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;

public class ProfileView extends JPanel {
    private final String viewName = "profile";
    private ProfileViewModel profileViewModel;
    private ProfileController profileController;

    public ProfileView(ProfileViewModel profileViewModel, ProfileController profileController) {
        this.profileViewModel = profileViewModel;
        this.profileController = profileController;

        // Create a label with the provided text
        JLabel nameLabel = new JLabel("Name: ");

        // Set layout and add the label to the panel
        setLayout(new BorderLayout());
        add(nameLabel, BorderLayout.NORTH);
    }

    public String getViewName() {return viewName;}

    public static void main(String[] args) {
        // Create an instance of ProfileView to display the frame
        System.out.println("Profile View");
    }

}
