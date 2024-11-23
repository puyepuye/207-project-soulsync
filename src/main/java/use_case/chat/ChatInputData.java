package use_case.chat;

import entity.ChatMessage;

public class ChatInputData {
    private final String chatURL;
    private final String currentUser;
    private final String pfpURL;
    private final ChatMessage chatMessage;

    public ChatInputData(String chatURL, String currentUser, ChatMessage chatMessage) {
        this.chatURL = chatURL;
        this.currentUser = currentUser;
        this.pfpURL = pfpURL;
        this.chatMessage = chatMessage;
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

    public ChatMessage getChatMessage() {
        return chatMessage;
    }



}
