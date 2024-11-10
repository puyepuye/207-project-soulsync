package view;

import javax.swing.*;
import java.awt.*;

public class SignupTempView extends JFrame {

    public SignupTempView() {
        // Set up the frame properties
        this.setTitle("Signup Temp View");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // LabelTextPanels for Email and Password
        LabelTextPanel emailPanel = new LabelTextPanel(new JLabel("Email:"), new JTextField(15));
        LabelTextPanel passwordPanel = new LabelTextPanel(new JLabel("Password:"), new JTextField(15));

        // CustomButton for login
        CustomButton loginButton = new CustomButton("LOGIN");

        // Add components to the frame
        this.add(emailPanel);
        this.add(passwordPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10))); // Space between fields and button
        this.add(loginButton);

        // Set up and show the frame
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // Create an instance of SignupTempView to display the frame
        new SignupTempView();
    }
}
