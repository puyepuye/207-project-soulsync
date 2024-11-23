package use_case.chat;

import entity.ChatMessage;

import java.util.List;

public class ChatOutputData {
    private final String chatURL;
    private final String currentUser;
    private final String pfpURL;

    private final List<ChatMessage> chatMessages;

    public ChatOutputData(String chatURL, String currentUser, String pfpURL, List<ChatMessage> chatMessages) {
        this.chatURL = chatURL;
        this.currentUser = currentUser;
        this.pfpURL = pfpURL;
        this.chatMessages = chatMessages;
    }

    public String getPfpURL() {
        return pfpURL;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getChatURL() {
        return chatURL;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

}
