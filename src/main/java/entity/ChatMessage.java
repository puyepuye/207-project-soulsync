package entity;

/**
 * Entity used to represent a chat message.
 */
public class ChatMessage {
    private final String sender;
    private final String message;
    private final String dateTime;
    private String chatUrl;

    public ChatMessage(String sender, String message, String dateTime) {
        this.sender = sender;
        this.message = message;
        this.dateTime = dateTime;
    }

    public ChatMessage(String sender, String message, String dateTime, String chatUrl) {
        this.sender = sender;
        this.message = message;
        this.dateTime = dateTime;
        this.chatUrl = chatUrl;
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

    public String getChatUrl() {
        return chatUrl;
    }

    public void setChatUrl(String chatUrl) {
        this.chatUrl = chatUrl;
    }

}
