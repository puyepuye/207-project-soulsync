package use_case.chat;

import entity.ChatMessage;

import java.util.List;

public class ChatInteractor implements ChatInputBoundary{
    private final ChatDataAccessInterface chatDataAccessObject;
    private final ChatOutputBoundary chatPresenter;

    public ChatInteractor(ChatDataAccessInterface chatDataAccessObject, ChatOutputBoundary chatOutputBoundary) {
        this.chatDataAccessObject = chatDataAccessObject;
        this.chatPresenter = chatOutputBoundary;
    }

    @Override
    public List<ChatMessage> getAllMessages(String chatUrl) {
        return chatDataAccessObject.getAllMessages(chatUrl);
    }

    @Override
    public void sendMessage(ChatInputData chatInputData) {
        chatDataAccessObject.sendMessage(chatInputData.getChatURL(), chatInputData.getChatMessage());
    }

    @Override
    public void switchToChatList() {
        chatPresenter.switchToChatList();
    }
}
