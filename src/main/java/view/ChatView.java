package view;

import data_access.NotificationService;
import data_access.SendBirdChatObject;
import entity.ChatMessage;
import use_case.chat.MessageListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
public class ChatView extends JFrame implements ActionListener, PropertyChangeListener, MessageListener {
    private JPanel chat;
    private JTextField textField;

    public ChatView() {
        SendBirdChatObject chatObject = new SendBirdChatObject();

        this.setTitle("Chat");
        this.setBackground(new Color(255, 162, 176));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // Profile part
        JPanel profile = new JPanel();
        profile.add(new JLabel("Name:"));
        profile.setMaximumSize(new Dimension(400, 50));

        // Middle chat part
        chat = new JPanel();
        chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
        chat.setBorder(new EmptyBorder(10, 20, 10, 20));
        chat.setBackground(new Color(255, 220, 227));

        JScrollPane scrollPane = new JScrollPane(chat);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Example messages
        chat.add(newMessage("Hello world"));

        // Text field + send button
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        bottom.setBackground(Color.WHITE);
        textField = new JTextField(24);
        textField.setBackground(new Color(255, 162, 176));
        textField.addActionListener(this);
        JButton sendButton = new JButton("send");
        sendButton.setMaximumSize(new Dimension(400, 40));
        sendButton.addActionListener(this);
        bottom.add(textField);
        bottom.add(sendButton);
        bottom.setMaximumSize(new Dimension(400, 80));

        // Add components to frame
        this.add(profile);
        this.add(scrollPane);  // Add scroll pane containing chat area
        this.add(bottom);

        this.setSize(400, 600);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = textField.getText();
        if (!message.trim().isEmpty()) {
            chat.add(newMessage(message));
            chat.revalidate();
            chat.repaint();
            textField.setText("");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Handle property changes if necessary
    }

    public JPanel newMessage(String message) {
        JPanel panel = new JPanel();
        JLabel text = new JLabel(message);
        chat.add(Box.createVerticalStrut(10));
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setMaximumSize(new Dimension(100, 40));
        panel.setBackground(new Color(255, 162, 176));
        panel.add(text);
        chat.add(Box.createVerticalStrut(20));
        return panel;
    }

    public static void main(String[] args) {
        NotificationService notificationService = new NotificationService();
        notificationService.registerMessageListener(new ChatView());
    }

    @Override
    public void onMessageReceived(ChatMessage message) {
        JPanel panel = new JPanel();
        JLabel text = new JLabel(message.getMessage());
        chat.add(Box.createVerticalStrut(20));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setMaximumSize(new Dimension(400, 40));
        panel.setBackground(new Color(255, 162, 176));
        panel.add(text);
        chat.add(Box.createVerticalStrut(20));
        chat.add(panel);
    }
}