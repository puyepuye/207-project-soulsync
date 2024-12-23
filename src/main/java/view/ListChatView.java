package view;

import entity.ChatChannel;
import interface_adapter.listchat.ListChatController;
import interface_adapter.listchat.ListChatState;
import interface_adapter.listchat.ListChatViewModel;
import interface_adapter.navbar.NavbarController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ListChatView extends JPanel implements ActionListener, PropertyChangeListener {
    private JPanel chatLists;
    private final ListChatViewModel listChatViewModel;
    private final ListChatController listChatController;
    private final NavbarController navbarController;

    public ListChatView(ListChatViewModel listChatViewModel, ListChatController listChatController, NavbarController navbarController) {
        this.listChatViewModel = listChatViewModel;
        this.listChatController = listChatController;
        this.navbarController = navbarController;
        this.listChatViewModel.addPropertyChangeListener(this);

        // Use a consistent background color for the frame
        this.setBackground(new Color(255, 162, 176));
        this.setLayout(new BorderLayout()); // Better layout for edge-to-edge alignment

        // Create the chatLists panel
        chatLists = new JPanel();
        chatLists.setLayout(new BoxLayout(chatLists, BoxLayout.Y_AXIS));
        chatLists.setBackground(new Color(255, 220, 227)); // Pink background
        chatLists.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Remove default padding
        chatLists.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Wrap chatLists in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(chatLists);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove the border of JScrollPane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add the scrollPane to the frame
        this.add(scrollPane, BorderLayout.CENTER);

        // Configure frame size and add sample chats
        this.setSize(new Dimension(400, 600));
        // Navbar at the bottom
        NavBarView navBarView = new NavBarView(navbarController);
        this.add(navBarView, BorderLayout.PAGE_END);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public void addChatToList(String chatURL, String pfpURL, String username, String lastMessage) throws IOException {
        // Create the panel for the chat
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 0));
        panel.setPreferredSize(new Dimension(400, 70));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        panel.setBackground(new Color(255, 240, 245));
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Add profile picture (image from URL)
        final JLabel profilePicture = new JLabel();
        profilePicture.setPreferredSize(new Dimension(60, 60));
        profilePicture.setMaximumSize(new Dimension(60, 60));
        profilePicture.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Navbar at the bottom
        final NavBarView navBarView = new NavBarView(navbarController);
        this.add(navBarView, BorderLayout.PAGE_END);
        // Load image from URL
        if (pfpURL.isEmpty()) {
            pfpURL = "https://static.wikia.nocookie.net/vgost/images/5/56/Rick_Astley.jpg/revision/latest?cb=20220705002743";
        }
        final URL imgUrl = new URL(pfpURL);
        final Image image = ImageIO.read(imgUrl).getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Scale image
        profilePicture.setIcon(new ImageIcon(image));
        // Add the profile picture to the panel
        panel.add(profilePicture);
        // Message box inside the chat panel
        final JLabel lastMsgSection = new JLabel(lastMessage);
        final JLabel userNameSection = new JLabel(username);
        userNameSection.setFont(new Font("Arial", Font.BOLD, 14));
        lastMsgSection.setFont(new Font("Arial", Font.PLAIN, 12));

        final JPanel messageBox = new JPanel();
        messageBox.setLayout(new BoxLayout(messageBox, BoxLayout.Y_AXIS));
        messageBox.setBorder(new EmptyBorder(10, 10, 10, 10));
        messageBox.setPreferredSize(new Dimension(350, 60));
        messageBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // Stretch message box width
        messageBox.setBackground(new Color(255, 240, 245)); // Green for testing
        messageBox.putClientProperty("URL", chatURL);
        // Add a MouseListener to handle clicks
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Retrieve the channel URL from the panel
                final String channelUrl = (String) messageBox.getClientProperty("URL");
                System.out.println("Navigating to channel: " + channelUrl);
                listChatController.enterChat(channelUrl);

            }
        });
        messageBox.add(userNameSection);
        // Add a 5-pixel vertical gap
        messageBox.add(Box.createRigidArea(new Dimension(0, 5)));
        messageBox.add(lastMsgSection);

        // Add the message box to the panel
        panel.add(messageBox);

        // Add the panel to the chatLists container
        chatLists.add(panel);
        // Prevent gaps between components
        chatLists.add(Box.createRigidArea(new Dimension(0, 0)));
        chatLists.revalidate();
        chatLists.repaint();
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final ListChatState state = (ListChatState) evt.getNewValue();
            System.out.println("current user " + state.getUsername());
            try {
                final ArrayList<ChatChannel> channels =  listChatController.getAllChats(state.getUsername());
                chatLists.removeAll();
                for (ChatChannel channel: channels) {
                    String channelUser = "";
                    if (channel.getUser1Id().equals(state.getUsername())) {
                        channelUser = channel.getUser2Id();
                    }
                    else {
                        channelUser = channel.getUser1Id();
                    }
                    System.out.println(channel.getChannelURL());
                    System.out.println(channel.getLastMessage());
                    addChatToList(channel.getChannelURL(),
                            "https://static.sendbird.com/sample/cover/cover_11.jpg",
                            channelUser, channel.getLastMessage());
                }
            }
            catch (IOException | InterruptedException e) {
                System.out.println("Could not get messages");
            }
            }
    }

    public String getViewName() {
        return "listchat";
    }
}


