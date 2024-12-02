package use_case.chat;

import entity.ChatMessage;

/**
 * The input data for the chat use case.
 */
public class ChatInputData {
    private final String chatUrl;
    private final String currentUser;
    private final ChatMessage chatMessage;

    public ChatInputData(String chatUrl, String currentUser, ChatMessage chatMessage) {
        this.chatUrl = chatUrl;
        this.currentUser = currentUser;
        this.chatMessage = chatMessage;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getChatUrl() {
        return chatUrl;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }
}
