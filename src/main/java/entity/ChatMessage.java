package entity;

public class ChatMessage {
    private final String sender;
    private final String message;
    private final String dateTime;
    private String chatURL;

    public ChatMessage(String sender, String message, String dateTime) {
        this.sender = sender;
        this.message = message;
        this.dateTime = dateTime;
    }

    public ChatMessage(String sender, String message, String dateTime, String chatURL) {
        this.sender = sender;
        this.message = message;
        this.dateTime = dateTime;
        this.chatURL = chatURL;
    }
    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getChatURL() {
        return chatURL;
    }

    public void setChatURL(String chatURL) {
        this.chatURL = chatURL;
    }

}
