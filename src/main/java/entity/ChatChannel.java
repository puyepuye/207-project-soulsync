package entity;

public class ChatChannel {



    private String channelURL;
    private String user1Id;
    private String user2Id;
    private String lastMessage;

    public ChatChannel(String channelURL, String lastMessage, String user1Id, String user2Id) {
        this.channelURL = channelURL;
        this.lastMessage = lastMessage;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public String getChannelURL() {
        return channelURL;
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
