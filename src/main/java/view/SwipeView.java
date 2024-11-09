package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.swipe.SwipeController;
import interface_adapter.swipe.SwipeState;
import interface_adapter.swipe.SwipeViewModel;
import use_case.swipe.SwipeInputData;

/**
 * The View for the Signup Use Case.
 */
public class SwipeView extends JPanel implements PropertyChangeListener {
    private final String viewName = "swipe";

    private final SwipeViewModel swipeViewModel;
    private final SwipeController swipeController;

    private final JLabel profileName;
    private final JLabel profileBio;
    private final JLabel profileTag;

    private final JButton like;
    private final JButton dislike;

    public SwipeView(SwipeController controller, SwipeViewModel swipeViewModel) {

        this.swipeController = controller;
        this.swipeViewModel = swipeViewModel;
        swipeViewModel.addPropertyChangeListener(this);

        profileName = new JLabel();
        profileBio = new JLabel();
        profileTag = new JLabel();

        final JPanel buttons = new JPanel();
        dislike = new JButton(SwipeViewModel.DISLIKE_BUTTON_LABEL);
        buttons.add(dislike);
        like = new JButton(SwipeViewModel.LIKE_BUTTON_LABEL);
        buttons.add(like);

        dislike.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(dislike)) {
                            SwipeState currentState = swipeViewModel.getState();
                            currentState.setLiked(false);  // Set liked to false for dislike action
                            swipeController.execute(currentState.getLiked(),
                                    currentState.getUsername(),
                                    currentState.getProfileName());
                        }
                    }
                }
        );

        like.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(like)) {
                            SwipeState currentState = swipeViewModel.getState();
                            currentState.setLiked(true);  // Set liked to true for like action
                            swipeController.execute(currentState.getLiked(),
                                    currentState.getUsername(),
                                    currentState.getProfileName());
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(profileName);
        this.add(profileBio);
        this.add(buttons);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SwipeState state = (SwipeState) evt.getNewValue();

        if (evt.getPropertyName().equals("state")) {
            profileName.setText(state.getProfileName());
            profileBio.setText(state.getProfileBio());
            profileTag.setText(state.getProfileTags());
        }

        if (state.getLikedError() != null) {
            JOptionPane.showMessageDialog(this, state.getLikedError());
        }
    }

    public String getViewName() {
        return viewName;
    }
}
