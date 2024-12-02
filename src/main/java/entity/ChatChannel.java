package entity;

/**
 * Entity for a chat channel.
 */
public class ChatChannel {

    private String channelUrl;
    private String user1Id;
    private String user2Id;
    private String lastMessage;

    public ChatChannel(String channelUrl, String lastMessage, String user1Id, String user2Id) {
        this.channelUrl = channelUrl;
        this.lastMessage = lastMessage;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getUser1Id() {
        return user1Id;
    }

    public String getUser2Id() {
        return user2Id;
    }
}
