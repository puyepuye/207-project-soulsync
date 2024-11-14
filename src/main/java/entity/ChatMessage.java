package entity;

public class ChatMessage {
    private final String sender;
    private final String message;
    private final String dateTime;

    public ChatMessage(String sender, String message, String dateTime) {
        this.sender = sender;
        this.message = message;
        this.dateTime = dateTime;
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

}
