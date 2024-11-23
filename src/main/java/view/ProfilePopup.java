package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

import java.net.URL;

public class ProfilePopup {

    // Gradient Colors (similar to PreferenceView)
    private static final Color TOP_COLOR = Color.decode("#FFEFF1");
    private static final Color BOTTOM_COLOR = Color.decode("#FFA2B0");

    public static void showProfileDialog(String image, String fullName, String location, String gender,
                                         Date dateOfBirth, String bio, List<String> tags) {
        // Define the width for the 4:5 ratio
        int width = 310;
        int height = (width * 5) / 4;

        // Create JDialog
        JDialog profileDialog = new JDialog((Frame) null, "Profile", true);
        profileDialog.setSize(width, height); // Set the 4:5 aspect ratio
        profileDialog.setLayout(new BorderLayout());

        profileDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0); // Terminate the program
            }
        });

        // Panel for main profile information with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                GradientPaint gradient = new GradientPaint(0, 0, TOP_COLOR, 0, height, BOTTOM_COLOR);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, width, height);
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Image panel
        ImagePanel imagePanel = new ImagePanel(image);  // Pass the image string (path or URL) to ImagePanel
        imagePanel.setPreferredSize(new Dimension(330, 200)); // Set image panel size
        mainPanel.add(imagePanel);  // Add the image panel to the main panel

        // Panel for name, age, and location with left alignment
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false); // Make the panel transparent to match the background

        // Name and Age
        int age = calculateAge(dateOfBirth);
        JLabel nameLabel = new JLabel(fullName + ", " + age);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        infoPanel.add(nameLabel);

        // Location
        JLabel locationLabel = new JLabel(location);
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(locationLabel);

        // "Tag-like" label for gender
        JLabel genderLabel = new JLabel(gender);
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        genderLabel.setOpaque(true);
        genderLabel.setBackground(new Color(220, 220, 220)); // Light gray background for tag effect
        genderLabel.setForeground(Color.BLACK);
        genderLabel.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8)); // Padding inside the label
        genderLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align left within the box
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Small spacing between name and tag
        infoPanel.add(genderLabel);

        // Add the infoPanel to the main panel
        mainPanel.add(infoPanel);


        // Bio
        JTextArea bioArea = new JTextArea(bio);
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        bioArea.setEditable(false);
        bioArea.setOpaque(false);
        bioArea.setFont(new Font("Arial", Font.PLAIN, 12));
        bioArea.setBorder(BorderFactory.createTitledBorder("About Me"));
        bioArea.setOpaque(true);
        bioArea.setBackground(new Color(255, 245, 247));
        mainPanel.add(bioArea);

        // Panel to hold the tags
        final JPanel tagPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tagPanel.setBackground(new Color(255, 245, 247)); // Background color for the tag panel itself

        // Adding default tags as labels with background colors
        String[] defaultTags = {"Night Owl", "Travel", "Romantic", "Workaholic", "Entrepreneur", "Foodie",
                "Adventurer", "Bookworm", "Creative", "Sporty", "Music Lover",
                "Nature Lover", "Tech Enthusiast", "Gamer", "Fitness Buff"};

        for (String tag : defaultTags) {
            JLabel tagLabel = new JLabel(tag);
            tagLabel.setOpaque(true);
            tagLabel.setBackground(PreferenceView.getTagColor(tag)); // Set background color based on tag name
            tagLabel.setForeground(Color.WHITE); // Set text color for contrast
            tagLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            tagLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside the label for better appearance
            tagLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1), // Optional border around each label
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)));

            tagPanel.add(tagLabel); // Add each label to the tag panel
        }

        mainPanel.add(tagPanel);

        profileDialog.add(mainPanel, BorderLayout.CENTER);
        profileDialog.setLocationRelativeTo(null); // Center the dialog
        profileDialog.setVisible(true);
    }

    // Method to calculate age
    private static int calculateAge(Date dateOfBirth) {
        Date currentDate = new Date();
        int age = currentDate.getYear() - dateOfBirth.getYear();
        if (currentDate.getMonth() < dateOfBirth.getMonth() ||
                (currentDate.getMonth() == dateOfBirth.getMonth() && currentDate.getDate() < dateOfBirth.getDate())) {
            age--;
        }
        return age;
    }

    // Main function to test the profile dialog
//    public static void main(String[] args) {
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
//        showProfileDialog(image, fullName, location, gender, dateOfBirth, bio, tags);
//    }
}
