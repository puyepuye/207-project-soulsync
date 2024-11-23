package use_case.chat;

import entity.ChatMessage;

import java.util.List;

public interface ChatInputBoundary {

    List<ChatMessage> getAllMessages(ChatInputData chatInputData);

    void sendMessage(ChatInputData chatInputData);

    void switchToChatList();
}
