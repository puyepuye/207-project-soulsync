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

import interface_adapter.edit_profile.EditProfileController;
import interface_adapter.edit_profile.EditProfileState;
import interface_adapter.edit_profile.EditProfileViewModel;

import java.util.*;
import java.util.List;

/**
 * The View for the Signup Use Case.
 */
public class EditProfileView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "edit profile";

    private final EditProfileViewModel editProfileViewModel;
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private final JTextField fullnameInputField = new JTextField(15);
    private final EditProfileController editProfileController;
    private final JComboBox<String> genderComboBox = new JComboBox<>(EditProfileViewModel.GENDERS);
    private final JComboBox<String> countryComboBox = new JComboBox<>(EditProfileViewModel.COUNTRIES);
    private final JComboBox<String> cityComboBox = new JComboBox<>(EditProfileViewModel.CITIES);

    private String profileImageUrl = null; // Variable to store the uploaded image URL

    // Replacing JTextField with JDatePickerImpl
    private final JDatePickerImpl dobDatePicker;
    private final JButton updateProfile;
    private final JButton cancel;
    private final JButton uploadProfileButton;
    private final JPanel preferredGenderPanel;
    private final JComboBox<String> preferredAgeComboBox;

    // Gradient Colors
    private final Color topColor = Color.decode("#FFEFF1"); // Light pink
    private final Color bottomColor = Color.decode("#FFC2C9");

    public EditProfileView(EditProfileController controller, EditProfileViewModel editProfileViewModel) {
        this.editProfileController = controller;
        this.editProfileViewModel = editProfileViewModel;
        editProfileViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(EditProfileViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel fullNameInfo = new LabelTextPanel(
                new JLabel(EditProfileViewModel.FULLNAME_LABEL), fullnameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(EditProfileViewModel.PASSWORD_LABEL), passwordInputField);
        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel(EditProfileViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField);

        // Initialize JDatePicker
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        dobDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        dobDatePicker.setPreferredSize(new Dimension(200, 30));

        JPanel dateOfBirthPanel = new JPanel();
        dateOfBirthPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        dateOfBirthPanel.add(new JLabel("Date of Birth:"));
        dateOfBirthPanel.add(dobDatePicker);

        final LabelDropdownPanel genderDropdown = new LabelDropdownPanel(
                new JLabel(EditProfileViewModel.GENDER_LABEL), genderComboBox);
        final LabelDropdownPanel countryDropdown = new LabelDropdownPanel(
                new JLabel(EditProfileViewModel.COUNTRY_LABEL), countryComboBox);
        final LabelDropdownPanel cityDropDown = new LabelDropdownPanel(
                new JLabel(EditProfileViewModel.CITY_LABEL), cityComboBox);

        JPanel preferredGenderContainer = new JPanel();
        preferredGenderContainer.setPreferredSize(new Dimension(300, 90));
        preferredGenderContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel preferredGenderLabel = new JLabel("Preferred Gender:");
        preferredGenderLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
        preferredGenderContainer.add(preferredGenderLabel);
        preferredGenderContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        // Create a JPanel for holding the checkboxes
        preferredGenderPanel = new JPanel(new GridBagLayout());

        // Create checkboxes
        JCheckBox option1 = new JCheckBox("Female");
        JCheckBox option2 = new JCheckBox("Male");
        JCheckBox option3 = new JCheckBox("Non-Binary");
        JCheckBox option4 = new JCheckBox("Other");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Single column
        gbc.anchor = GridBagConstraints.CENTER; // Align content to the center
        gbc.insets = new Insets(0, 10, 0, 10);

        // Add checkboxes to the panel
        preferredGenderPanel.add(option1);
        preferredGenderPanel.add(option2);
        preferredGenderPanel.add(option3);
        preferredGenderPanel.add(option4);

        preferredGenderContainer.add(preferredGenderPanel);

        // Create a JComboBox with the age ranges
        preferredAgeComboBox = new JComboBox<>(EditProfileViewModel.preferredAgeRanges);
        preferredAgeComboBox.setSelectedIndex(0); // Default selection

        // Create a new LabelDropdownPanel for Preferred Age
        final LabelDropdownPanel preferredAgeDropdown = new LabelDropdownPanel(
                new JLabel("Preferred Age:"), preferredAgeComboBox);

        preferredGenderContainer.add(preferredAgeDropdown);

        final JPanel buttons = new JPanel();
        updateProfile = new JButton("Update Profile");
        buttons.add(updateProfile);
        cancel = new JButton(EditProfileViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        // Profile Upload Button
        uploadProfileButton = new JButton("Upload Profile Photo");
        uploadProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadProfileButton.addActionListener(e -> openProfileUploadView());

        updateProfile.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(updateProfile)) {

                            final EditProfileState currentState = editProfileViewModel.getState();
                            if (profileImageUrl != null) {
                                currentState.setImage(profileImageUrl);
                            }
                            editProfileViewModel.setState(currentState);

                            editProfileController.execute(
                                    currentState.getFullName(),
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

                        }
                    }
                }
        );

        cancel.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        editProfileController.switchToSwipeView();

                    }
                }

        );

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
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(fullNameInfo);
        this.add(dateOfBirthPanel);
        this.add(genderDropdown);
        this.add(countryDropdown);
        this.add(cityDropDown);
        this.add(preferredGenderContainer);
        this.add(uploadProfileButton);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(buttons);
    }

    private void addDOBListener() {
        dobDatePicker.addActionListener(e -> {
            final EditProfileState currentState = editProfileViewModel.getState();
            Date selectedDate = (Date) dobDatePicker.getModel().getValue();
            currentState.setDateOfBirth(selectedDate);
            editProfileViewModel.setState(currentState);
        });
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final EditProfileState currentState = editProfileViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                editProfileViewModel.setState(currentState);
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
                final EditProfileState currentState = editProfileViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                editProfileViewModel.setState(currentState);
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
                final EditProfileState currentState = editProfileViewModel.getState();
                currentState.setFullName(fullnameInputField.getText());
                editProfileViewModel.setState(currentState);
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
                final EditProfileState currentState = editProfileViewModel.getState();
                currentState.setGender((String) genderComboBox.getSelectedItem());
                editProfileViewModel.setState(currentState);
            }
        });
    }

    private void addLocationListener() {
        countryComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                final EditProfileState currentState = editProfileViewModel.getState();

                String location = (String) countryComboBox.getSelectedItem();
                String city = (String) cityComboBox.getSelectedItem();

                if (city != null) {
                    location += ", " + city;
                }

                currentState.setLocation(location);
                editProfileViewModel.setState(currentState);
            }
        });

        cityComboBox.addItemListener(e -> {

            if (e.getStateChange() == ItemEvent.SELECTED) {
                final EditProfileState currentState = editProfileViewModel.getState();

                String location = (String) countryComboBox.getSelectedItem();
                String city = (String) cityComboBox.getSelectedItem();

                if (city != null) {
                    location += ", " + city;
                }

                currentState.setLocation(location);
                editProfileViewModel.setState(currentState);
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
                        final EditProfileState currentState = editProfileViewModel.getState();
                        currentState.setPreferredGender(selectedCheckBoxes);
                        System.out.println(currentState.getPreferredGender());
                        editProfileViewModel.setState(currentState);
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
                final EditProfileState currentState = editProfileViewModel.getState();
                currentState.setPreferredAge(preferredAgeRange);
                System.out.println(currentState.getPreferredAge());
                editProfileViewModel.setState(currentState);
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
        final EditProfileState state = (EditProfileState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

}
