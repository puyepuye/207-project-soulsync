package view;

import data_access.SwipesListDataAccessObject;
import interface_adapter.navbar.NavbarController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavBarView extends JPanel {
    private final String viewName = "navbar";
    private final JButton swipeButton;
    private final JButton fengshuiButton;
    private final JButton chatButton;
    private final JButton profileButton;
    private final NavbarController navbarController;

    public NavBarView(NavbarController navbarController) {
        this.navbarController = navbarController;
        // Set background color and layout for the NavBar
        this.setBackground(Color.decode("#FFFBFB"));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Align buttons to the left with spacing

        // Initialize buttons with text
        swipeButton = createNavButton("♡");
        fengshuiButton = createNavButton("✧");
        chatButton = createNavButton("\uD83D\uDCAC");
        profileButton = createNavButton("\uD83D\uDE4E\uD83C\uDFFB\u200D");

        // Add buttons to the NavBar
        this.add(swipeButton);
        this.add(fengshuiButton);
        this.add(chatButton);
        this.add(profileButton);

        // Optional: add ActionListener to handle button clicks
        addNavButtonListeners();
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.decode("#393939"));
        button.setBackground(Color.decode("#FFFBFB"));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private void addNavButtonListeners() {
        swipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Swipe button clicked");
                navbarController.switchToSwipeView();
            }
        });

        fengshuiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Fengshui button clicked");
                navbarController.switchToCompatibilityView();
            }
        });

        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Chat button clicked");
                navbarController.switchToListChatView();
                // Add logic to switch to the Settings view
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Profile button clicked");
                // Add logic to handle logout
            }
        });
    }

    public String getViewName() {
        return viewName;
    }

}
