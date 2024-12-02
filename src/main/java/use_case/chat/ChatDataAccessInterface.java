package use_case.chat;

import entity.ChatMessage;

import java.util.List;

public interface ChatDataAccessInterface {

    void sendMessage(String chatURL, ChatMessage chatMessage);

    void createChatUser(String uniqueID, String username, String pfpURL);

    void createChat(String userId1, String userId2);

    List<ChatMessage> getAllMessages(String chatURL);

    void updateProfilePicture(String username, String image);

    void updateFullName(String username, String fullname);
}
