package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import java.util.Properties;
import java.util.Date;

/**
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private final JTextField fullnameInputField = new JTextField(15);
    private final SignupController signupController;
    private final JComboBox<String> genderComboBox = new JComboBox<>(SignupViewModel.GENDERS);
    private final JComboBox<String> countryComboBox = new JComboBox<>(SignupViewModel.COUNTRIES);
    private final JComboBox<String> cityComboBox = new JComboBox<>(SignupViewModel.CITIES);

    // Replacing JTextField with JDatePickerImpl
    private final JDatePickerImpl dobDatePicker;
    private final JButton signUp;
    private final JButton cancel;
    private final JButton toLogin;

    public SignupView(SignupController controller, SignupViewModel signupViewModel) {

        this.signupController = controller;
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel fullNameInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.FULLNAME_LABEL), fullnameInputField);
        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.USERNAME_LABEL), usernameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.PASSWORD_LABEL), passwordInputField);
        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField);
        // Initialize JDatePicker
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        dobDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        dobDatePicker.setPreferredSize(new java.awt.Dimension(140, 30));

        final LabelDropdownPanel genderDropdown = new LabelDropdownPanel(
                new JLabel(SignupViewModel.GENDER_LABEL), genderComboBox);

        final LabelDropdownPanel countryDropdown = new LabelDropdownPanel(
                new JLabel(SignupViewModel.COUNTRY_LABEL), countryComboBox);

        final LabelDropdownPanel cityDropDown = new LabelDropdownPanel(
                new JLabel(SignupViewModel.CITY_LABEL), cityComboBox);

        final JPanel buttons = new JPanel();
        toLogin = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        buttons.add(toLogin);
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signUp);
        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        signUp.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            final SignupState currentState = signupViewModel.getState();

                            signupController.execute(
                                    currentState.getFullname(),
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword(),
                                    currentState.getImage(),
                                    currentState.getLocation(),
                                    currentState.getGender(),
                                    currentState.getDateOfBirth()
                            );

                        }
                    }
                }
        );

        toLogin.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        signupController.switchToLoginView();

                    }
                }

        );

//        cancel.addActionListener(this);

        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();
        addDOBListener();
        addGenderListener();
        addLocationListener();
        addFullNameListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(fullNameInfo);
        this.add(dobDatePicker);
        this.add(genderDropdown);
        this.add(countryDropdown);
        this.add(cityDropDown);
        this.add(buttons);
    }

    private void addDOBListener() {
        dobDatePicker.addActionListener(e -> {
            final SignupState currentState = signupViewModel.getState();
            Date selectedDate = (Date) dobDatePicker.getModel().getValue();
            currentState.setDateOfBirth(selectedDate);
            signupViewModel.setState(currentState);
        });
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
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
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    private void addFullNameListener() {
        fullnameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setFullname(fullnameInputField.getText());
                signupViewModel.setState(currentState);
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
    }


    private void addGenderListener() {
        genderComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                final SignupState currentState = signupViewModel.getState();
                currentState.setGender((String) genderComboBox.getSelectedItem());
                signupViewModel.setState(currentState);
            }
        });
    }

    private void addLocationListener() {
        countryComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                final SignupState currentState = signupViewModel.getState();

                String location = (String) countryComboBox.getSelectedItem();
                String city = (String) cityComboBox.getSelectedItem();

                if (city != null) {
                    location += ", " + city;
                }

                currentState.setLocation(location);
                signupViewModel.setState(currentState);
            }
        });

        cityComboBox.addItemListener(e -> {

            if (e.getStateChange() == ItemEvent.SELECTED) {
                final SignupState currentState = signupViewModel.getState();

                String location = (String) countryComboBox.getSelectedItem();
                String city = (String) cityComboBox.getSelectedItem();

                if (city != null) {
                    location += ", " + city;
                }

                currentState.setLocation(location);
                signupViewModel.setState(currentState);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

}
