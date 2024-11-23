package interface_adapter.chat;

import entity.ChatMessage;
import interface_adapter.ViewManagerModel;
import use_case.chat.ChatOutputBoundary;

import java.util.List;

public class ChatPresenter implements ChatOutputBoundary {

    private final ChatViewModel chatViewModel;
    private final ViewManagerModel viewManagerModel;


    public ChatPresenter(ChatViewModel chatViewModel, ViewManagerModel viewManagerModel) {
        this.chatViewModel = chatViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void messageSent(ChatMessage chatMessage) {

    }

    public void messageReceived(ChatMessage chatMessage) {

    }

    @Override
    public void presentMessages(List<ChatMessage> messages) {

    }

    @Override
    public void presentMessagesAfter() {

    }

    @Override
    public void switchToChatList() {
        // TODO: make it change to the chat list view
        viewManagerModel.setState("hi");
        viewManagerModel.firePropertyChanged();
    }
}
