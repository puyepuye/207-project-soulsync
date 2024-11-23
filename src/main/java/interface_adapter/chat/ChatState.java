package interface_adapter.chat;

import entity.ChatMessage;

import java.util.List;

public class ChatState {

    private String chatURL;
    private String currUser;
    private List<ChatMessage> messages;
    private String pfp;

    public String getChatURL() {return chatURL;}

    public void setChatURL(String chatURL) {this.chatURL = chatURL;}

    public String getCurrUser() {return currUser;}

    public void setCurrUser(String currUser) {this.currUser = currUser;}

    public List<ChatMessage> getMessages() {return messages;}

    public void setMessages(List<ChatMessage> messages) {this.messages = messages;}

    public String getPfp() {return pfp;}

    public void setPfp(String pfp) {this.pfp = pfp;}
}
