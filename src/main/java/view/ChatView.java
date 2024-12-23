package view;

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
import java.util.ArrayList;
import java.util.Calendar;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

/**
 * The view model for the chat view use case.
 */
public class ChatView extends JPanel implements ActionListener, PropertyChangeListener {
    private JPanel chat;
    private JTextField textField;
    private final ChatController chatController;
    private ChatViewModel chatViewModel;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private final String name = "chat";
    private String currentUser;
    private String chatURL;
    private JScrollPane scrollPane;
    private final JLabel usernameLabel = new JLabel();

    @SuppressWarnings({"checkstyle:ExecutableStatementCount", "checkstyle:SuppressWarnings"})
    public ChatView(ChatController chatController, ChatViewModel chatViewModel) {
        this.chatController = chatController;
        this.chatViewModel = chatViewModel;
        this.chatViewModel.addPropertyChangeListener(this);
        this.setBackground(new Color(255, 162, 176));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Profile part
        final JPanel profile = new JPanel();
        final JButton backButton = createNavButton("⬅");

        backButton.setActionCommand("back");
        backButton.addActionListener(this);
        profile.add(backButton);
        profile.add(usernameLabel);
        profile.setMaximumSize(new Dimension(400, 100));

        // Middle chat part
        chat = new JPanel();
        chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
        chat.setBorder(new EmptyBorder(10, 20, 10, 20));
        chat.setBackground(new Color(255, 220, 227));

        scrollPane = new JScrollPane(chat);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);

        // Text field + send button
        final JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        bottom.setBackground(Color.WHITE);
        textField = new JTextField(24);
        textField.setBackground(new Color(255, 220, 227));
        textField.addActionListener(this);
        final JButton sendButton = new JButton("send");
        sendButton.setMaximumSize(new Dimension(400, 40));
        sendButton.setActionCommand("send");
        sendButton.addActionListener(this);
        bottom.add(textField);
        bottom.add(sendButton);
        bottom.setMaximumSize(new Dimension(400, 80));

        // Add components to frame
        this.add(profile);
        this.add(scrollPane);
        this.add(bottom);
        // Add message listener
        MessageEventManager.getInstance().addPropertyChangeListener(evt -> {
            if ("message".equals(evt.getPropertyName())) {
                SwingUtilities.invokeLater(() -> {
                    ChatMessage newMessage = (ChatMessage) evt.getNewValue();
                    if (newMessage.getChatURL().equals(chatURL)) {
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
            final String message = textField.getText();
            if (!message.trim().isEmpty()) {
                final ChatState chatState = chatViewModel.getState();
                newMessage(message, "send");
                chat.revalidate();
                chat.repaint();
                textField.setText("");
                chatController.sendMessage(
                        new ChatInputData(chatState.getChatUrl(), chatState.getCurrUser(),
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
            System.out.println("chat url " + state.getChatUrl());
            chatURL = state.getChatUrl();
            final String currUser = state.getCurrUser();
            System.out.println("currUser " + currUser);
            currentUser = currUser;
            for (String username: chatURL.split("_")) {
                if (!username.equals(currUser) && !"chat".equals(username)) {
                    usernameLabel.setText(username);
                }
            }
            final ArrayList<ChatMessage> chatMessages = (ArrayList<ChatMessage>) chatController.getAllMessages(chatURL);
            for (ChatMessage chatMessage : chatMessages) {
                if (chatMessage.getSender().equals(currUser)) {
                    newMessage(chatMessage.getMessage(), "send");
                }
                else {
                    newMessage(chatMessage.getMessage(), "receive");
                }
            }
        }
    }

    /**
     * Adds a new message to the screen.
     * @param message the message to be added.
     * @param state used to get the current user's name.
     */
    public void newMessage(String message, String state) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 220, 227));
        panel.setMaximumSize(new Dimension(400, 80));

        // Add horizontal glue to push the message to the right
        if (!state.equals("receive")) {
            panel.add(Box.createHorizontalGlue());
        }

        // Message box
        final JPanel messageBox = new JPanel();
        messageBox.setLayout(new FlowLayout(FlowLayout.LEFT));
        messageBox.setBackground(new Color(255, 162, 176));

        final JTextArea text = new JTextArea(message);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        text.setOpaque(false);
        text.setBorder(BorderFactory.createEmptyBorder());

        // Calculate dimensions based on message length
        final int maxWidth = (int) (this.getWidth() * 0.3);
        text.setSize(new Dimension(maxWidth, Short.MAX_VALUE));
        text.setFont(new Font("Arial", Font.PLAIN, 14));
        text.setMaximumSize(new Dimension(maxWidth, 60));
        // Use preferred size for dynamic height
        final Dimension preferredSize = text.getPreferredSize();
        text.setPreferredSize(new Dimension(Math.min(maxWidth, preferredSize.width), preferredSize.height));

        messageBox.add(text);
        panel.add(messageBox);

        if (state.equals("receive")) {
            panel.add(Box.createHorizontalGlue());
        }

        final JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());
        chat.add(panel);
        chat.revalidate();
        chat.repaint();
    }

    private JButton createNavButton(String text) {
        final JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.decode("#393939"));
        button.setBackground(Color.decode("#FFFBFB"));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    @Override
    public String getName() {
        return name;
    }
}