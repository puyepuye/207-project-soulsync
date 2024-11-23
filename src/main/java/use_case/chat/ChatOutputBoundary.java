package use_case.chat;

import entity.ChatMessage;

import java.util.List;

public interface ChatOutputBoundary {

    void presentMessages(List<ChatMessage> messages);
    void presentMessagesAfter();
    void switchToChatList();
}
