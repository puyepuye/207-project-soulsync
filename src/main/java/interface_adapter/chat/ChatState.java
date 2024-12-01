package interface_adapter.chat;

import java.util.List;

import entity.ChatMessage;

/**
 * State object used for chat use case.
 */
public class ChatState {

    private String chatUrl;
    private String currUser;
    private List<ChatMessage> messages;
    private String pfp;

    public String getChatUrl() {
        return chatUrl;
    }

    public void setChatUrl(String chatUrl) {
        this.chatUrl = chatUrl;
    }

    public String getCurrUser() {
        return currUser;
    }

    public void setCurrUser(String currUser) {
        this.currUser = currUser;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }
}
