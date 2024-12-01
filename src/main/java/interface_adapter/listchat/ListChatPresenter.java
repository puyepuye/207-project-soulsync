package interface_adapter.listchat;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatState;
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
    public void enterChat(String chatURL) {

        final String currUser = listChatViewModel.getState().getUsername();
        System.out.println("current user" + currUser    );
        final ChatState chatState = chatViewModel.getState();
        System.out.println("chat url " + chatURL);
        chatState.setChatUrl(chatURL);
        chatState.setCurrUser(currUser);
        this.chatViewModel.setState(chatState);

        viewManagerModel.setState(chatViewModel.getViewName());
        viewManagerModel.firePropertyChanged();


        this.chatViewModel.firePropertyChanged();
    }


}
