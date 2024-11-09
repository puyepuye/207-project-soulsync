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

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logIn;
    private final JButton toSignUp;
    private final LoginController loginController;

    private final Color topColor = Color.decode("#FFEFF1");
    private final Color bottomColor = Color.decode("#FFA2B0");
    private final Color logoColor = Color.decode("#E75F5F");
    private final Color fontColor = Color.decode("#393939");

    public LoginView(LoginViewModel loginViewModel, LoginController controller) {

        this.loginController = controller;
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        // Set padding around the JPanel components
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel logoLabel = new JLabel("♡"); // This is the heart emoji
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 50)); // Set font size
        logoLabel.setForeground(logoColor); // Set the font color
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel title = new JLabel("SoulSync");
        title.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size
        title.setForeground(fontColor);
        title.setBorder(new EmptyBorder(10, 20, 10, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInputField);
        usernameInfo.setBackground(new Color(255, 251, 251)); // Light pink background
        usernameInfo.setOpaque(true); // Make sure the background color is shown

        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInputField);
        passwordInfo.setBackground(new Color(255, 251, 251)); // Light pink background
        passwordInfo.setOpaque(true);

        final JPanel buttons = new JPanel();
        buttons.setBackground(new Color(255, 251, 251)); // Light pink background
        logIn = new JButton("log in");
        buttons.add(logIn);

        // Adding the new "Sign Up" button
        toSignUp = new JButton("Don't have an account? Sign up");
        toSignUp.setForeground(fontColor); // Optional: set the color of the text to blue
        toSignUp.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the sign up button
        toSignUp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        loginController.switchToSignupView();  // Assuming the switch method is in controller
                    }
                }
        );

        logIn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {
                            final LoginState currentState = loginViewModel.getState();

                            loginController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword()
                            );
                        }
                    }
                }
        );

        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        this.add(logoLabel);
        this.add(title);
        this.add(usernameInfo);
        this.add(usernameErrorField);
        this.add(passwordInfo);
        this.add(buttons);

        // Add vertical spacing between buttons and the sign-up link
        this.add(Box.createVerticalStrut(10));

        this.add(toSignUp); // Sign-up button should be at the bottom
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Create a gradient paint
        GradientPaint gradient = new GradientPaint(0, 0, topColor, width, height, bottomColor);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
    }

    public String getViewName() {
        return viewName;
    }
}
