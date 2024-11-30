package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import java.util.*;
import java.util.List;

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

    private String profileImageUrl = null; // Variable to store the uploaded image URL

    // Replacing JTextField with JDatePickerImpl
    private final JDatePickerImpl dobDatePicker;
    private final JButton signUp;
    private final JButton cancel;
    private final JButton toLogin;
    private final JButton uploadProfileButton;
    private final JPanel preferredGenderPanel;
    private final JComboBox<String> preferredAgeComboBox;

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

        // Create a JPanel for holding the checkboxes
        preferredGenderPanel = new JPanel();
        preferredGenderPanel.setLayout(new GridLayout(0, 1)); // One checkbox per row

        // Create checkboxes
        JCheckBox option1 = new JCheckBox("Female");
        JCheckBox option2 = new JCheckBox("Male");
        JCheckBox option3 = new JCheckBox("Non-Binary");
        JCheckBox option4 = new JCheckBox("Other");

        // Add checkboxes to the panel
        preferredGenderPanel.add(option1);
        preferredGenderPanel.add(option2);
        preferredGenderPanel.add(option3);
        preferredGenderPanel.add(option4);

        // Create a JComboBox with the age ranges
        preferredAgeComboBox = new JComboBox<>(SignupViewModel.preferredAgeRanges);
        preferredAgeComboBox.setSelectedIndex(0); // Default selection

        final JPanel buttons = new JPanel();
        toLogin = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        buttons.add(toLogin);
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signUp);
        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        // Profile Upload Button
        uploadProfileButton = new JButton("Upload Profile Photo");
        uploadProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadProfileButton.addActionListener(e -> openProfileUploadView());

        signUp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            if (profileImageUrl == null || profileImageUrl.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Please upload a profile photo before signing up.",
                                        "Missing Photo", JOptionPane.WARNING_MESSAGE);
                                return;
                            }

                            final SignupState currentState = signupViewModel.getState();

                            currentState.setImage(profileImageUrl);
                            signupViewModel.setState(currentState);

                            signupController.execute(
                                    currentState.getFullname(),
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword(),
                                    currentState.getImage(), // Pass the uploaded image URL
                                    currentState.getLocation(),
                                    currentState.getGender(),
                                    currentState.getDateOfBirth(),
                                    currentState.getPreferredGender(),
                                    currentState.getPreferredAge()
                            );

                            JOptionPane.showMessageDialog(null, "Signup successful!\nProfile Image URL: " + profileImageUrl);
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
        addPreferredGenderListener();
        addPreferredAgeListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(fullNameInfo);
        this.add(dobDatePicker);
        this.add(genderDropdown);
        this.add(countryDropdown);
        this.add(preferredGenderPanel);
        this.add(preferredAgeComboBox);
        this.add(cityDropDown);
        this.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        this.add(uploadProfileButton);
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

    private void addPreferredGenderListener() {
        for (Component component : preferredGenderPanel.getComponents()) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;

                // Add a listener to detect state changes
                checkBox.addItemListener(e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED || e.getStateChange() == ItemEvent.DESELECTED) {
                        // Get all selected checkboxes and print them
                        List<String> selectedCheckBoxes = getSelectedCheckBoxes(preferredGenderPanel);
                        System.out.println("Selected checkboxes: " + selectedCheckBoxes);

                        // Optionally update the state with the selected checkboxes
                        final SignupState currentState = signupViewModel.getState();
                        currentState.setPreferredGender(selectedCheckBoxes);
                        System.out.println(currentState.getPreferredGender());
                        signupViewModel.setState(currentState);
                    }
                });
            }
        }
    }

    private List<String> getSelectedCheckBoxes(JPanel panel) {
        List<String> selectedItems = new ArrayList<>();

        // Iterate through all components in the panel
        for (Component component : panel.getComponents()) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    selectedItems.add(checkBox.getText()); // Add the text of the selected checkbox
                }
            }
        }

        return selectedItems;
    }



    private void addPreferredAgeListener() {
        preferredAgeComboBox.addActionListener(e -> {
            String selectedRange = (String) preferredAgeComboBox.getSelectedItem();
            System.out.println("Selected Age Range: " + selectedRange);

            // Parse the range into a HashMap if needed
            HashMap<String, Integer> preferredAgeRange = parseAgeRange(selectedRange);
            if (preferredAgeRange != null) {
                System.out.println("Parsed Range: " + preferredAgeRange);
                final SignupState currentState = signupViewModel.getState();
                currentState.setPreferredAge(preferredAgeRange);
                System.out.println(currentState.getPreferredAge());
                signupViewModel.setState(currentState);
            }
        });
        // Update state with selected values (removing trailing comma and space)
    }

    /**
     * Parses the selected age range string into a HashMap with "min" and "max".
     * Returns null for "70+" since it doesn't have an upper limit.
     *
     * @param ageRange The age range string (e.g., "18-30" or "70+").
     * @return A HashMap with "min" and "max" values for the range.
     */
    private HashMap<String, Integer> parseAgeRange(String ageRange) {
        HashMap<String, Integer> rangeMap = new HashMap<>();
        if (ageRange.contains("-")) {
            String[] parts = ageRange.split("-");
            rangeMap.put("min", Integer.parseInt(parts[0]));
            rangeMap.put("max", Integer.parseInt(parts[1]));
        } else if (ageRange.endsWith("+")) {
            rangeMap.put("min", Integer.parseInt(ageRange.replace("+", "")));
            rangeMap.put("max", Integer.MAX_VALUE); // Represent no upper limit
        }
        return rangeMap;
    }

    private void openProfileUploadView() {
        SwingUtilities.invokeLater(() -> {
            ProfileUploadView uploadView = new ProfileUploadView();
            uploadView.setVisible(true);

            // Retrieve the uploaded image URL
            uploadView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    String uploadedUrl = uploadView.getUploadedImageUrl();
                    if (uploadedUrl != null) {
                        profileImageUrl = uploadedUrl;
                    } else {
                        JOptionPane.showMessageDialog(null, "No image uploaded.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
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
