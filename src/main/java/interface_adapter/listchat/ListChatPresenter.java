package interface_adapter.listchat;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatViewModel;
import use_case.listchat.ListChatOutputBoundary;

public class ListChatPresenter implements ListChatOutputBoundary {

    private final ListChatViewModel listChatViewModel;
    private final ChatViewModel chatViewModel;
    private final ViewManagerModel viewManagerModel;

    public ListChatPresenter(ListChatViewModel listChatViewModel, ChatViewModel chatViewModel, ViewManagerModel viewManagerModel) {
        this.listChatViewModel = listChatViewModel;
        this.chatViewModel = chatViewModel;
        this.viewManagerModel = viewManagerModel;
    }



    @Override
    public void prepareSuccessView() {
        listChatViewModel.firePropertyChanged("username");
    }

    @Override
    public void enterChat(String chatURL) {
        viewManagerModel.setState(chatViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
