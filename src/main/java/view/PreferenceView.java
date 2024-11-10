package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.preferences.PreferencesController;
import interface_adapter.preferences.PreferencesViewModel;

import javax.swing.*;

public class PreferenceView extends JFrame {

    public PreferenceView(String message) {
        // Set the title of the window
        super("Preference View");

        // Create a label with the provided message
        JLabel label = new JLabel("Hello, " + message, SwingConstants.CENTER);

        // Add the label to the frame
        add(label);

        // Set the frame properties
        setSize(300, 200); // Width and height of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when closed
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true); // Make the frame visible
    }

    public PreferenceView(PreferencesController preferencesController, PreferencesViewModel preferencesViewModel) {
    }

    public static void main(String[] args) {
        // Create an instance of PreferenceView to display the frame
        new PreferenceView("something");
    }
}
