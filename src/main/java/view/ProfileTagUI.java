package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProfileTagUI extends JPanel {

    // Default tags
    private final String[] defaultTags = {"Night Owl", "Travel", "Romantic", "Workaholic", "Entrepreneur"};
    private final ArrayList<String> selectedTags = new ArrayList<>();
    private final JPanel selectedTagPanel = new JPanel();
    private final JTextArea bioTextArea = new JTextArea(5, 20); // Bio TextArea for user input
    private final JButton createProfileButton;

    public ProfileTagUI() {
        // Set layout for the main panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Section
        final JLabel titleLabel = new JLabel("Your Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tag Section
        final JLabel tagLabel = new JLabel("Add Profile Tags");
        tagLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tagLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel tagPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tagPanel.setBackground(Color.WHITE);

        // Adding default tags as buttons with rounded corners and background colors
        for (String tag : defaultTags) {
            JButton tagButton = new JButton(tag + " ✕");
            tagButton.setBackground(getTagColor(tag));
            tagButton.setForeground(Color.WHITE);
            tagButton.setFocusPainted(false);
            tagButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            tagButton.setFont(new Font("Arial", Font.PLAIN, 12));
            tagButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Make the button rounded
            tagButton.setContentAreaFilled(false);
            tagButton.setOpaque(true);
            tagButton.setBorderPainted(false);
            tagButton.addActionListener(new TagButtonListener(tagButton, tag));
            tagPanel.add(tagButton);
        }

        // Selected Tags Panel
        selectedTagPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        selectedTagPanel.setBackground(new Color(255, 245, 247));
        selectedTagPanel.setBorder(BorderFactory.createTitledBorder("Selected Tags"));

        // Bio Section
        final JLabel bioLabel = new JLabel("Bio");
        bioLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set background color for the bio text area
        bioTextArea.setLineWrap(true);
        bioTextArea.setWrapStyleWord(true);
        bioTextArea.setBackground(new Color(255, 245, 247)); // Set the background color to light pink
        bioTextArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Optional: add a border

        // Button Section
        createProfileButton = new JButton("CREATE PROFILE");
        createProfileButton.setFont(new Font("Arial", Font.BOLD, 14));
        createProfileButton.setForeground(Color.WHITE);
        createProfileButton.setBackground(new Color(215, 87, 78));
        createProfileButton.setFocusPainted(false);
        createProfileButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Make the button rounded
        createProfileButton.setContentAreaFilled(false);
        createProfileButton.setOpaque(true);
        createProfileButton.setBorderPainted(false);

        // Add action listener for the button
        createProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProfile();
            }
        });

        // Add components to the main panel
        this.add(titleLabel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(tagLabel);
        this.add(tagPanel);
        this.add(selectedTagPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(bioLabel);
        this.add(bioTextArea);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(createProfileButton);
    }

    // Method to get tag color based on tag name
    private Color getTagColor(String tag) {
        switch (tag) {
            case "Night Owl":
                return new Color(255, 149, 120);
            case "Travel":
                return new Color(255, 193, 7);
            case "Romantic":
                return new Color(240, 98, 146);
            case "Workaholic":
                return new Color(41, 128, 185);
            case "Entrepreneur":
                return new Color(76, 175, 80);
            default:
                return Color.GRAY;
        }
    }

    // Listener for tag buttons
    private class TagButtonListener implements ActionListener {
        private final JButton button;
        private final String tag;

        public TagButtonListener(JButton button, String tag) {
            this.button = button;
            this.tag = tag;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedTags.contains(tag)) {
                selectedTags.remove(tag);
                button.setText(tag + " ✕");
                removeTagFromSelectedPanel(tag);
            } else {
                selectedTags.add(tag);
                button.setText(tag + " ✕");
                addTagToSelectedPanel(tag);
            }
            System.out.println("Selected Tags: " + selectedTags);
        }
    }

    private void addTagToSelectedPanel(String tag) {
        JLabel tagLabel = new JLabel(tag);
        tagLabel.setOpaque(true);
        tagLabel.setBackground(getTagColor(tag));
        tagLabel.setForeground(Color.WHITE);
        tagLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        selectedTagPanel.add(tagLabel);
        selectedTagPanel.revalidate();
        selectedTagPanel.repaint();
    }

    private void removeTagFromSelectedPanel(String tag) {
        for (Component comp : selectedTagPanel.getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().equals(tag)) {
                selectedTagPanel.remove(comp);
                selectedTagPanel.revalidate();
                selectedTagPanel.repaint();
                break;
            }
        }
    }

    // Method to save the profile information
    private void saveProfile() {
        String bio = bioTextArea.getText();
        System.out.println("Selected Tags: " + selectedTags);
        System.out.println("Bio: " + bio);
        JOptionPane.showMessageDialog(this, "Profile saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Profile Tag Selector");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 550);
            frame.setLocationRelativeTo(null);
            frame.add(new ProfileTagUI());
            frame.setVisible(true);
        });
    }
}
