package interface_adapter.chat;

import interface_adapter.ViewManagerModel;
import interface_adapter.listchat.ListChatState;
import interface_adapter.listchat.ListChatViewModel;
import use_case.chat.ChatOutputBoundary;

/**
 * Presenter class for chat use case.
 */
public class ChatPresenter implements ChatOutputBoundary {

    private final ChatViewModel chatViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ListChatViewModel listChatViewModel;

    public ChatPresenter(ViewManagerModel viewManagerModel,
                         ChatViewModel chatViewModel,
                         ListChatViewModel listChatViewModel) {
        this.chatViewModel = chatViewModel;
        this.viewManagerModel = viewManagerModel;
        this.listChatViewModel = listChatViewModel;
    }

    @Override
    public void switchToChatList() {
        final ListChatState listChatState = listChatViewModel.getState();
        this.listChatViewModel.setState(listChatState);
        this.listChatViewModel.firePropertyChanged();
        viewManagerModel.setState(listChatViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
