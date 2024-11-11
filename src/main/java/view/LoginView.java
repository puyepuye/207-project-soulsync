package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel logoLabel = new JLabel("♡");
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        logoLabel.setForeground(logoColor);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("SoulSync");
        title.setFont(new Font("Arial", Font.PLAIN, 16));
        title.setForeground(fontColor);
        title.setBorder(new EmptyBorder(10, 20, 10, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Configure username error field
        usernameErrorField.setBackground(new Color(255, 251, 251));
        usernameErrorField.setForeground(Color.RED);
        usernameErrorField.setOpaque(true);

        // Using GridBagLayout to control the alignment more precisely
        JPanel usernamePanel = new JPanel(new GridBagLayout());
        JPanel passwordPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Username label and input field constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        usernamePanel.add(new JLabel("Username"), gbc);
        passwordPanel.add(new JLabel("Password"), gbc);

        gbc.gridx = 1;
        usernamePanel.add(usernameInputField, gbc);
        passwordPanel.add(passwordInputField, gbc);

        // Error field constraints
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, -15, 0, 0);  // Move error label further to the left
        usernamePanel.add(usernameErrorField, gbc);
        passwordPanel.add(passwordErrorField, gbc);

        usernamePanel.setBackground(new Color(255, 251, 251));
        usernamePanel.setOpaque(true);
        passwordPanel.setBackground(new Color(255, 251, 251));
        passwordPanel.setOpaque(true);

        JPanel buttons = new JPanel();
        buttons.setBackground(new Color(255, 251, 251));
        logIn = new JButton("log in");
        buttons.add(logIn);

        toSignUp = new JButton("Don't have an account? Sign up");
        toSignUp.setForeground(fontColor);
        toSignUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        toSignUp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        loginController.switchToSignupView();
                    }
                }
        );

        logIn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {
                            LoginState currentState = loginViewModel.getState();
                            loginController.execute(currentState.getUsername(), currentState.getPassword());
                        }
                    }
                }
        );

        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }
            @Override public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }
            @Override public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });

        // Add components to layout
        this.add(logoLabel);
        this.add(title);
        this.add(usernamePanel);  // Add username panel instead of directly adding components
        this.add(passwordPanel);
        this.add(passwordErrorField);
        this.add(buttons);
        this.add(Box.createVerticalStrut(10));
        this.add(toSignUp);
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        GradientPaint gradient = new GradientPaint(0, 0, topColor, width, height, bottomColor);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
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
