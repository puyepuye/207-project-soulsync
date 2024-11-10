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

/**
 * The View for when the user signs up and moves to choosing dating preferences within the program.
 */
public class PreferenceView extends JPanel  {
    private final String viewName = "preferences";
    private PreferencesViewModel preferencesViewModel;
    private PreferencesController preferencesController;

    public PreferenceView(PreferencesViewModel preferencesViewModel, PreferencesController controller) {
        this.preferencesController = controller;
        this.preferencesViewModel = preferencesViewModel;

        // Create a label with the provided text
        JLabel label = new JLabel("Hello, kimi", SwingConstants.CENTER);

        // Set layout and add the label to the panel
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
    }

    public String getViewName() {
        return viewName;
    }
    public static void main(String[] args) {
        // Create an instance of PreferenceView to display the frame
        System.out.println("Preference View");
    }
}
