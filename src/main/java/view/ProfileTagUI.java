package view;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProfileTagUI extends JPanel {

    private final ArrayList<String> selectedTags = new ArrayList<>();
    private final JPanel selectedTagPanel = new JPanel();
    private final JTextArea bioTextArea = new JTextArea(5, 20); // Bio TextArea for user input

    // Gradient Colors
    private final Color topColor = Color.decode("#FFEFF1");
    private final Color bottomColor = Color.decode("#FFA2B0");

    public ProfileTagUI() {
        // Set layout for the main panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Section
        final JLabel titleLabel = new JLabel("Your Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tag Section
        final JLabel tagLabel = new JLabel("Add Profile Tags");
        tagLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tagLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel to hold the tags
        final JPanel tagPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tagPanel.setBackground(new Color(255, 245, 247));

        // Adding default tags as buttons with rounded corners and background colors
        // Default tags
        String[] defaultTags = {"Night Owl", "Travel", "Romantic", "Workaholic", "Entrepreneur", "Foodie", "Adventurer", "Bookworm", "Creative", "Sporty", "Music Lover", "Nature Lover", "Tech Enthusiast", "Gamer", "Fitness Buff"};
        for (String tag : defaultTags) {
            JButton tagButton = new JButton(tag);
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

        // Wrap tagPanel inside a JScrollPane to allow scrolling if needed
        JScrollPane tagScrollPane = new JScrollPane(tagPanel);
        tagScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tagScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        tagScrollPane.setBorder(null);
        tagScrollPane.setPreferredSize(new Dimension(350, 50)); // Set fixed height for scroll area

        // Selected Tags Panel
        selectedTagPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        selectedTagPanel.setBackground(new Color(255, 245, 247));
        selectedTagPanel.setBorder(BorderFactory.createTitledBorder("Selected Tags"));
        selectedTagPanel.setPreferredSize(new Dimension(140, 60));

        // Bio Section
        final JLabel bioLabel = new JLabel("Bio (max 100 characters)");
        bioLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set background color for the bio text area
        bioTextArea.setLineWrap(true);
        bioTextArea.setWrapStyleWord(true);
        bioTextArea.setBackground(new Color(255, 245, 247)); // Set the background color to light pink
        bioTextArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Optional: add a border

        // Limit bioTextArea to 100 characters
        bioTextArea.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, javax.swing.text.AttributeSet attr)
                    throws BadLocationException {
                if (str == null) return;

                // Only insert the string if the resulting text length is within the limit
                if ((getLength() + str.length()) <= 100) {
                    super.insertString(offset, str, attr);
                }
            }
        });

        // Button Section
        JButton createProfileButton = new JButton("CREATE PROFILE");
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
        createProfileButton.addActionListener(e -> saveProfile());

        // Add components to the main panel
        this.add(titleLabel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(tagLabel);
        this.add(tagScrollPane); // Add the JScrollPane containing the tagPanel
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
            case "Foodie":
                return new Color(244, 164, 96); // Sandy brown
            case "Adventurer":
                return new Color(72, 209, 204); // Medium turquoise
            case "Bookworm":
                return new Color(138, 43, 226); // Blue violet
            case "Creative":
                return new Color(255, 165, 0); // Orange
            case "Sporty":
                return new Color(34, 139, 34); // Forest green
            case "Music Lover":
                return new Color(123, 104, 238); // Medium slate blue
            case "Nature Lover":
                return new Color(60, 179, 113); // Medium sea green
            case "Tech Enthusiast":
                return new Color(0, 191, 255); // Deep sky blue
            case "Gamer":
                return new Color(148, 0, 211); // Dark violet
            case "Fitness Buff":
                return new Color(255, 99, 71); // Tomato
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

        private void addTagToSelectedPanel(String tag) {
            JButton tagButton = new JButton(tag);
            tagButton.setOpaque(true);
            tagButton.setBackground(getTagColor(tag));
            tagButton.setForeground(Color.WHITE);
            tagButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            tagButton.setFocusPainted(false);
            tagButton.setContentAreaFilled(false);
            tagButton.setOpaque(true);
            tagButton.setBorderPainted(false);

            // Add action listener to remove the tag from selected tags
            tagButton.addActionListener(e -> {
                selectedTags.remove(tag);
                selectedTagPanel.remove(tagButton);
                selectedTagPanel.revalidate();
                selectedTagPanel.repaint();

                // Update the button text in the main tag panel to remove the "selected" indicator (e.g., if any)
                updateMainTagPanel(tag);
            });

            selectedTagPanel.add(tagButton);
            selectedTagPanel.revalidate();
            selectedTagPanel.repaint();
        }

        // Method to update the main tag panel button text when tag is removed
        private void updateMainTagPanel(String tag) {
            for (Component comp : ((JPanel)((JScrollPane)selectedTagPanel.getComponent(3)).getViewport()
                    .getView()).getComponents()) {
                if (comp instanceof JButton) {
                    JButton mainTagButton = (JButton) comp;
                    if (mainTagButton.getText().equals(tag)) {
                        mainTagButton.setText(tag);
                        break;
                    }
                }
            }
        }

        private void removeTagFromSelectedPanel(String tag) {
            for (Component comp : selectedTagPanel.getComponents()) {
                if (comp instanceof JButton) {
                    JButton tagButton = (JButton) comp;
                    if (tagButton.getText().equals(tag)) {
                        selectedTagPanel.remove(tagButton);
                        selectedTagPanel.revalidate();
                        selectedTagPanel.repaint();
                        break;
                    }
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedTags.contains(tag)) {
                // Remove the tag if it's already selected
                selectedTags.remove(tag);
                button.setText(tag);
                removeTagFromSelectedPanel(tag);
            } else {
                // Only add the tag if there are less than 3 tags selected
                if (selectedTags.size() < 3) {
                    selectedTags.add(tag);
                    button.setText(tag);
                    addTagToSelectedPanel(tag);
                } else {
                    // Show message if the limit is reached
                    JOptionPane.showMessageDialog(ProfileTagUI.this, "You can only select up to 3 tags.", "Limit Reached", JOptionPane.WARNING_MESSAGE);
                }
            }
            System.out.println("Selected Tags: " + selectedTags);
        }
    }

    // Method to save the profile information
    private void saveProfile() {
        String bio = bioTextArea.getText();
        System.out.println("Selected Tags: " + selectedTags);
        System.out.println("Bio: " + bio);
        JOptionPane.showMessageDialog(this, "Profile saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        GradientPaint gradient = new GradientPaint(0, 0, topColor, 0, height, bottomColor);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Profile Tag Selector");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Set frame size to a 2:3 aspect ratio (e.g., 400x600)
            int width = 400;
            int height = (int) (width * 1.5);  // Calculate height for a 2:3 ratio

            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);
            frame.add(new ProfileTagUI());
            frame.setVisible(true);
        });
    }
}
