package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import entity.User;
import interface_adapter.compatibility.CompatibilityState;
import interface_adapter.navbar.NavbarController;
import interface_adapter.compatibility.CompatibilityController;
import interface_adapter.compatibility.CompatibilityViewModel;
import use_case.compatibility.CompatibilityCalculator;
import use_case.compatibility.FengshuiCalculator;

public class CompatibilityView extends JPanel implements PropertyChangeListener {
    private final String viewName = "compatibility";
    private final CompatibilityViewModel compatibilityViewModel;
    private final CompatibilityController compatibilityController;
    private final JComboBox<String> fengshuiDropdown;
    private final JComboBox<String> compatibilityDropdown;
    private final JLabel fengshuiResult;
    private final JLabel compatibilityResult;
    private final JTextArea fengshuiText;
    private final JLabel compatibilityPercentage;

    public CompatibilityView(CompatibilityViewModel compatibilityViewModel, CompatibilityController controller,
                             NavbarController navbarController) {
        this.compatibilityViewModel = compatibilityViewModel;
        this.compatibilityViewModel.addPropertyChangeListener(this);
        this.compatibilityController = controller;

        // Set the main layout and background color
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(255, 240, 245)); // Light pink background

        // Main Content Panel for Fengshui and Compatibility sections
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(new Color(255, 240, 245));

        // Fengshui Section
        JPanel fengshuiPanel = new JPanel();
        fengshuiPanel.setLayout(new GridLayout(6, 1, 10, 10));
        fengshuiPanel.setBackground(new Color(255, 240, 245));
        fengshuiPanel.setBorder(new EmptyBorder(20, 20, 20, 40));

        JLabel fengshuiLabel = new JLabel("Fengshui Calculator", SwingConstants.LEFT);
        fengshuiLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        // Example output for debugging
        fengshuiPanel.add(fengshuiLabel);

        JLabel fengshuiDescription = new JLabel("Have someone in mind? Let’s check if your Fengshui aligns!", SwingConstants.LEFT);
        fengshuiDescription.setFont(new Font("SansSerif", Font.PLAIN, 12));
        fengshuiPanel.add(fengshuiDescription);

        fengshuiDropdown = new JComboBox<>();
        fengshuiDropdown.setFont(new Font("SansSerif", Font.PLAIN, 14));
        fengshuiDropdown.setBackground(new Color(204, 153, 255));
        fengshuiPanel.add(fengshuiDropdown);

        fengshuiResult = new JLabel("You❤", SwingConstants.CENTER);
        fengshuiResult.setFont(new Font("SansSerif", Font.PLAIN, 18));
        fengshuiPanel.add(fengshuiResult);

        fengshuiText = createWrappingTextArea("");
        fengshuiText.setFont(new Font("SansSerif", Font.BOLD, 12));
        fengshuiPanel.add(fengshuiText);


        // Compatibility Section
        JPanel compatibilityPanel = new JPanel();
        compatibilityPanel.setLayout(new GridLayout(6, 1, 10, 10));
        compatibilityPanel.setBackground(new Color(255, 240, 245));
        compatibilityPanel.setBorder(new EmptyBorder(0, 20, 20, 40));

        JLabel compatibilityLabel = new JLabel("Compatibility Calculator", SwingConstants.LEFT);
        compatibilityLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        compatibilityPanel.add(compatibilityLabel);

        JLabel compatibilityDescription = new JLabel("Does your interest align? Let's check your compatibility!", SwingConstants.LEFT);
        compatibilityDescription.setFont(new Font("SansSerif", Font.PLAIN, 12));
        compatibilityPanel.add(compatibilityDescription);

        compatibilityDropdown = new JComboBox<>();
        compatibilityDropdown.setFont(new Font("SansSerif", Font.PLAIN, 14));
        compatibilityDropdown.setBackground(new Color(255, 153, 153));
        compatibilityPanel.add(compatibilityDropdown);

        compatibilityResult = new JLabel("You❤", SwingConstants.CENTER);
        compatibilityResult.setFont(new Font("SansSerif", Font.PLAIN, 18));
        compatibilityPanel.add(compatibilityResult);

        compatibilityPercentage = new JLabel("", SwingConstants.CENTER);
        compatibilityPercentage.setFont(new Font("SansSerif", Font.BOLD, 24));
        compatibilityPanel.add(compatibilityPercentage);

        // Add sections to main content panel
        mainContentPanel.add(fengshuiPanel);
        mainContentPanel.add(compatibilityPanel);

        // Add title and main content panel to the main view
        this.add(mainContentPanel, BorderLayout.CENTER);

        // Navbar at the bottom
        NavBarView navBarView = new NavBarView(navbarController);
        this.add(navBarView, BorderLayout.PAGE_END);

        // Add listeners to the dropdowns
        fengshuiDropdown.addActionListener(e -> updateCompatibilityResult(fengshuiDropdown, fengshuiResult));
        fengshuiDropdown.addActionListener(e -> updateFengshuiScore(fengshuiDropdown, fengshuiText));
        compatibilityDropdown.addActionListener(e -> updateCompatibilityResult(compatibilityDropdown, compatibilityResult));
        compatibilityDropdown.addActionListener(e -> updateCompatibilityScore(compatibilityDropdown, compatibilityPercentage));
    }

    private void updateCompatibilityResult(JComboBox<String> dropdown, JLabel resultLabel) {
        String selectedUser = (String) dropdown.getSelectedItem();
        resultLabel.setText("You ❤ " + selectedUser); // Update result label with selected user
    }

    private void updateFengshuiScore(JComboBox<String> dropdown, JTextArea fengshuiText) {

        final CompatibilityState state = compatibilityViewModel.getState();
        String currentUser = state.getUsername();
        String selectedUser = (String) dropdown.getSelectedItem();

        Date currentUserDate = compatibilityController.getUserDOB(currentUser);
        Date selectedUserDate = compatibilityController.getUserDOB(selectedUser);

        FengshuiCalculator fengshuiCalculator = new FengshuiCalculator(currentUserDate, selectedUserDate);
        System.out.println(currentUserDate);
        System.out.println(selectedUserDate);
        System.out.println("View" + fengshuiCalculator.calculateScore());
        fengshuiText.setText(fengshuiCalculator.calculateScore());
    }

    private void updateCompatibilityScore(JComboBox<String> dropdown, JLabel compatibilityPercentage) {

        final CompatibilityState state = compatibilityViewModel.getState();
        String currentUser = state.getUsername();
        String selectedUser = (String) dropdown.getSelectedItem();

        Map<String, Boolean> currentUserPrefereces = compatibilityController.getUserPreferences(currentUser);
        Map<String, Boolean> selectedUserPreferences = compatibilityController.getUserPreferences(selectedUser);

        CompatibilityCalculator compatibilityCalculator = new CompatibilityCalculator(currentUserPrefereces,
                selectedUserPreferences);
        System.out.println(currentUserPrefereces);
        System.out.println(selectedUserPreferences);
        System.out.println("View" + compatibilityCalculator.calculate());
        compatibilityPercentage.setText(String.format("%d%%", compatibilityCalculator.calculate()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final CompatibilityState state = (CompatibilityState) evt.getNewValue();

            // Get the list of matched users based on the new username
            String[] userList = compatibilityController.getMatchedUsers(state.getUsername());

            // Update dropdown models with the new user list
            fengshuiDropdown.setModel(new DefaultComboBoxModel<>(userList));
            compatibilityDropdown.setModel(new DefaultComboBoxModel<>(userList));

            // Optionally, set a default value or preselect a user
            if (userList.length > 0) {
                fengshuiDropdown.setSelectedIndex(0); // Default to the first item
                compatibilityDropdown.setSelectedIndex(0); // Default to the first item
            }
        }
    }


    // Helper method to create a JTextArea that wraps text
    private JTextArea createWrappingTextArea(String text) {
        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);  // Makes it look like a JLabel
        textArea.setFocusable(false);
        textArea.setBackground(new Color(0, 0, 0, 0)); // Transparent background
        return textArea;
    }

    public String getViewName() {
        return viewName;
    }
}
