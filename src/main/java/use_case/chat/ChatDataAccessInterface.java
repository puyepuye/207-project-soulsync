package use_case.chat;

import java.util.List;

import entity.ChatMessage;

/**
 * An interface for the DAO for the chat use case.
 */
public interface ChatDataAccessInterface {

    /**
     * Sends a message.
     * @param chatUrl the desired chat channel.
     * @param chatMessage the message you want to send.
     */
    void sendMessage(String chatUrl, ChatMessage chatMessage);

    /**
     * Create a new user on Sendbird's end.
     * @param username the username of the user
     * @param fullName the user's full name
     * @param pfpUrl the user's profile picture link
     */
    void createChatUser(String username, String fullName, String pfpUrl);

    /**
     * A method to create a new private chat.
     * @param userId1 first user
     * @param userId2 second user
     */
    void createChat(String userId1, String userId2);

    /**
     * Gets all the messages in a channel.
     * @param chatUrl the desired chat
     * @return a list of messages in the form of the ChatMessage object
     */
    List<ChatMessage> getAllMessages(String chatUrl);

    /**
     * Updates the user's profile picture on sendbird.
     * @param username the user we want to update.
     * @param image the link to the new profile picture.
     */
    void updateProfilePicture(String username, String image);

    /**
     * Update the user's full name on sendbird.
     * @param username the user we want to update
     * @param fullname the new username
     */
    void updateFullName(String username, String fullname);
}
