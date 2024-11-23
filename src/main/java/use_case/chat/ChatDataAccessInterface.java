package use_case.chat;

import entity.ChatMessage;

import java.util.List;

public interface ChatDataAccessInterface {

    void sendMessage(String chatURL, ChatMessage chatMessage);

    void createChatUser(String uniqueID, String username, String pfpURL);

    List<ChatMessage> getAllMessages(String chatURL);

}
