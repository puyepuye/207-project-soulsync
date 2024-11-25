package view;

import data_access.ChatDataAccessObject;
import entity.ChatChannel;
import entity.ChatMessage;
import interface_adapter.chat.ChatController;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.chat.MessageEventManager;
import interface_adapter.listchat.ListChatState;
import use_case.chat.ChatInputData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatView extends JPanel implements ActionListener, PropertyChangeListener {
    private JPanel chat;
    private JTextField textField;
    private final ChatController chatController;
    private ChatViewModel chatViewModel;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private final String name = "chat";
    private String currentUser;
    private String chatURL;
    public ChatView(ChatController chatController, ChatViewModel chatViewModel) {
        this.chatController = chatController;
        this.chatViewModel = chatViewModel;
        this.chatViewModel.addPropertyChangeListener(this);
        this.setBackground(new Color(255, 162, 176));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Profile part
        JPanel profile = new JPanel();
        JButton backButton = createNavButton("⬅");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);
        profile.add(backButton);
        profile.add(new JLabel("Name:"));
        profile.setMaximumSize(new Dimension(400, 100));

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
        sendButton.setActionCommand("send");
        sendButton.addActionListener(this);
        bottom.add(textField);
        bottom.add(sendButton);
        bottom.setMaximumSize(new Dimension(400, 80));

        // Add components to frame
        this.add(profile);
        this.add(scrollPane);  // Add scroll pane containing chat area
        this.add(bottom);

//         Add a message listener
        MessageEventManager.getInstance().addPropertyChangeListener(evt -> {
            if ("message".equals(evt.getPropertyName())) {
                SwingUtilities.invokeLater(() -> {
                    ChatMessage newMessage = (ChatMessage) evt.getNewValue();
                    if (newMessage.getChatURL().equals(chatURL)){
                        if (!newMessage.getSender().equals(currentUser)) {
                            newMessage(newMessage.getMessage(), "receive");
                        }
                    }


                    chat.revalidate();
                    chat.repaint();
                });
            }
        });

        this.setSize(400, 600);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("send")) {
        String message = textField.getText();
            if (!message.trim().isEmpty()) {
                ChatState chatState = chatViewModel.getState();
                newMessage(message, "send");
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

        if (e.getActionCommand().equals("back")) {
            chatController.switchToChatList();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("propertyChange: " + evt.getPropertyName());
        if (evt.getPropertyName().equals("state")) {
            final ChatState state = (ChatState) evt.getNewValue();
            chat.removeAll();
            System.out.println("chat url " + state.getChatURL());
            chatURL = state.getChatURL();
            String currUser = state.getCurrUser();
            System.out.println("currUser " + currUser);
            currentUser = currUser;

            ArrayList<ChatMessage> chatMessages = (ArrayList<ChatMessage>) chatController.getAllMessages(chatURL);

            for (ChatMessage chatMessage : chatMessages) {
                System.out.println(chatMessage.getSender());
                if (chatMessage.getSender().equals(currUser)) {
                    newMessage(chatMessage.getMessage(), "send");
                }
                else{
                    newMessage(chatMessage.getMessage(), "receive");
                }
            }


        }
    }

    public void newMessage(String message, String state) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 220, 227));

        // Add horizontal glue to push the message to the right
        if (!state.equals("receive")){
            panel.add(Box.createHorizontalGlue());
        }

        // Message box
        JPanel messageBox = new JPanel();
        messageBox.setLayout(new FlowLayout(FlowLayout.RIGHT));
        messageBox.setPreferredSize(new Dimension((int) (this.getWidth() * 0.3), 40)); // 30% of width
        messageBox.setMaximumSize(new Dimension((int) (this.getWidth() * 0.3), 40));
        messageBox.setBackground(new Color(255, 162, 176));

        JLabel text = new JLabel(message);
        messageBox.add(text);
        panel.add(messageBox);
        if (state.equals("receive")){
            panel.add(Box.createHorizontalGlue());
        }
        chat.add(panel);
//        chat.add(timestamp);
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.decode("#393939"));
        button.setBackground(Color.decode("#FFFBFB"));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    @Override
    public String getName(){
        return name;
    }
    public static void main(String[] args) {

    }
}