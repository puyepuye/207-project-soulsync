package use_case.chat;

import java.util.List;

import entity.ChatMessage;

/**
*    Input boundary for the chat use case.
 *    */
public interface ChatInputBoundary {

    /**
     * Returns all the messages in a chat channel in the form of a list of message objects.
     * @param chatUrl the URL of the desired chat
     * @return a list of message objects
     */
    List<ChatMessage> getAllMessages(String chatUrl);

    /**
     * Sends a message.
     * @param chatInputData the message we want to send, in the form of the input data object.
     */
    void sendMessage(ChatInputData chatInputData);

    /**
     * Switch to the chat list page.
     */
    void switchToChatList();
}
