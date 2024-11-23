package use_case.chat;

import entity.ChatMessage;

public interface MessageListener {
    void onMessageReceived(ChatMessage message);
}
