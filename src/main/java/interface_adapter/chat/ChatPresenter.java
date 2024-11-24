package interface_adapter.chat;

import entity.ChatMessage;
import interface_adapter.ViewManagerModel;
import interface_adapter.listchat.ListChatViewModel;
import use_case.chat.ChatOutputBoundary;

import java.util.List;

public class ChatPresenter implements ChatOutputBoundary {

    private final ChatViewModel chatViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ListChatViewModel chatListViewModel;


    public ChatPresenter(ViewManagerModel viewManagerModel,
                         ChatViewModel chatViewModel,
                         ListChatViewModel listChatViewModel) {
        this.chatViewModel = chatViewModel;
        this.viewManagerModel = viewManagerModel;
        this.chatListViewModel = listChatViewModel;
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
        viewManagerModel.setState(chatListViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
