package view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;

/**
 * A panel containing a label and a text field.
 */
class LabelDropdownPanel extends JPanel {
    LabelDropdownPanel(JLabel label, JComboBox<String> combo) {
        this.add(label);
        this.add(combo);
        combo.setMaximumSize(combo.getPreferredSize());
    }
}
