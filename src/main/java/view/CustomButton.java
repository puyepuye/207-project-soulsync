package view;

import javax.swing.*;
import java.awt.*;

/**
 * A custom button component with a predefined style.
 */
public class CustomButton extends JButton {

    public CustomButton(String text) {
        super(text);

        // Set button styles to match the design
        this.setBackground(new Color(239, 83, 80)); // Red color for background
        this.setForeground(Color.WHITE); // White text
        this.setFont(new Font("SansSerif", Font.BOLD, 16));
        this.setFocusPainted(false);
        this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Force the button to respect the background color
        this.setOpaque(true);
        this.setBorderPainted(false); // Remove default border to match custom styling
        this.setBorder(new RoundedBorder(20));
    }


    public static void main(String[] args) {
        // Create an instance of SignupTempView to display the frame
        new CustomButton("hello");
    }
}
