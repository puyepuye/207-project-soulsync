package use_case.chat;

import entity.ChatMessage;

import java.util.List;

public class ChatOutputData {
    private final String chatUrl;
    private final String currentUser;
    private final String pfpUrl;

    private final List<ChatMessage> chatMessages;

    public ChatOutputData(String chatUrl, String currentUser, String pfpUrl, List<ChatMessage> chatMessages) {
        this.chatUrl = chatUrl;
        this.currentUser = currentUser;
        this.pfpUrl = pfpUrl;
        this.chatMessages = chatMessages;
    }

    public String getPfpUrl() {
        return pfpUrl;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getChatUrl() {
        return chatUrl;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

}
