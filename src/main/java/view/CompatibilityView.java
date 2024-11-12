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

import interface_adapter.navbar.NavbarController;
import interface_adapter.compatibility.CompatibilityController;
import interface_adapter.compatibility.CompatibilityViewModel;

import javax.swing.*;

public class CompatibilityView extends JPanel implements PropertyChangeListener {
    private final String viewName = "compatibility";

    public CompatibilityView(CompatibilityViewModel compatibilityViewModel, CompatibilityController controller, NavbarController navbarController) {
        JLabel label = new JLabel("Fengshui Calculator", SwingConstants.CENTER);
        NavBarView navBarView = new NavBarView(navbarController);

        this.setLayout(new BorderLayout());
        this.add(label, BorderLayout.CENTER);
        this.add(navBarView, BorderLayout.SOUTH);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public String getViewName() {
        return viewName;
    }

}
