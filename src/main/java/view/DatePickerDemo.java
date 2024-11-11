package view;

import javax.swing.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Date;

public class DatePickerDemo {

    public static void main(String[] args) {
        // Set up the frame
        JFrame frame = new JFrame("JDatePicker Test");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Set up the date model and picker
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Position the date picker
        datePicker.setBounds(50, 50, 200, 30);
        frame.add(datePicker);

        // Create and add a button to print the date
        JButton button = new JButton("Print Date");
        button.setBounds(50, 100, 100, 30);
        frame.add(button);

        // Add action listener to button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date selectedDate = (Date) datePicker.getModel().getValue();
                if (selectedDate != null) {
                    System.out.println("Selected date: " + selectedDate);
                } else {
                    System.out.println("No date selected.");
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}

// Optional formatter class to format the date
class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private String datePattern = "yyyy-MM-dd";
    private java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws java.text.ParseException {
        return dateFormatter.parse(text);
    }

    @Override
    public String valueToString(Object value) {
        if (value != null) {
            java.util.Calendar cal = (java.util.Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}
