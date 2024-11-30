package interface_adapter.listchat;

import interface_adapter.ViewModel;

public class ListChatViewModel extends ViewModel<ListChatState> {
    public ListChatViewModel() {
        super("listchat");
        setState(new ListChatState());
    }
}