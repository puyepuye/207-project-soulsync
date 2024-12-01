package interface_adapter.chat;

import interface_adapter.ViewModel;

/**
 * View model for the chat use case.
 */
public class ChatViewModel extends ViewModel<ChatState> {
    public ChatViewModel() {
        super("chat");
        setState(new ChatState());
    }
}
