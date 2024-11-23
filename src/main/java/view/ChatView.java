package view;

import data_access.ChatDataAccessObject;
import entity.ChatMessage;
import interface_adapter.chat.ChatController;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.chat.MessageEventManager;
import use_case.chat.ChatInputData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChatView extends JFrame implements ActionListener, PropertyChangeListener {
    private JPanel chat;
    private JTextField textField;
    private final ChatController chatController;
    private final ChatViewModel chatViewModel;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public ChatView(ChatController chatController, ChatViewModel chatViewModel) {
        this.chatController = chatController;
        this.chatViewModel = chatViewModel;

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

        // Add a message listener
        MessageEventManager.getInstance().addPropertyChangeListener(evt -> {
            if ("message".equals(evt.getPropertyName())) {
                SwingUtilities.invokeLater(() -> {
                    String newMessage = (String) evt.getNewValue();
                    chat.add(newMessage(newMessage));
                    chat.revalidate();
                    chat.repaint();
                });
            }
        });

        this.setSize(400, 600);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = textField.getText();
        if (!message.trim().isEmpty()) {
            ChatState chatState = chatViewModel.getState();
            chat.add(newMessage(message));
            chat.revalidate();
            chat.repaint();
            textField.setText("");
            chatController.sendMessage(
                    new ChatInputData(chatState.getChatURL(), chatState.getCurrUser(),
                            new ChatMessage(
                                    chatState.getCurrUser(),
                                    message,
                                    sdf.format(Calendar.getInstance().getTime())
                            )
                    )
            );
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Handle property changes if necessary
    }

    public JPanel newMessage(String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 220, 227));

        // Add horizontal glue to push the message to the right
        panel.add(Box.createHorizontalGlue());

        // Message box
        JPanel messageBox = new JPanel();
        messageBox.setLayout(new FlowLayout(FlowLayout.LEFT));
        messageBox.setPreferredSize(new Dimension((int) (this.getWidth() * 0.3), 40)); // 30% of width
        messageBox.setMaximumSize(new Dimension((int) (this.getWidth() * 0.3), 40));
        messageBox.setBackground(new Color(255, 162, 176));

        JLabel text = new JLabel(message);
        messageBox.add(text);

        panel.add(messageBox);
        return panel;
    }

    public static void main(String[] args) {

    }
}