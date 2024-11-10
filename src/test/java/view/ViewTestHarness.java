package view;

import javax.swing.*;
import view.LabelTextPanel;

public class ViewTestHarness {
    public static void main(String[] args) {
        // Create a frame to display individual components
        JFrame frame = new JFrame("Component Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }
}
