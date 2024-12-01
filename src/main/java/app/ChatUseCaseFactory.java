package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatController;
import interface_adapter.chat.ChatPresenter;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.listchat.ListChatViewModel;
import use_case.chat.ChatDataAccessInterface;
import use_case.chat.ChatInputBoundary;
import use_case.chat.ChatInteractor;
import use_case.chat.ChatOutputBoundary;
import view.ChatView;

/**
 * This class contains the static factory function for creating the Chat.
 */
public final class ChatUseCaseFactory {

    private ChatUseCaseFactory() {

    }

    /**
     * Creates a new instance of {@link ChatView}.
     *
     * @param viewManagerModel       the model managing the overall view state and transitions
     * @param chatViewModel          the model containing data and logic specific to the chat view
     * @param listChatViewModel      the model managing the list of chats
     * @param chatDataAccessObject   the data access interface used to interact with chat data
     * @return                       a new {@link ChatView} instance
     */
    public static ChatView create(
            ViewManagerModel viewManagerModel,
            ChatViewModel chatViewModel,
            ListChatViewModel listChatViewModel,
            ChatDataAccessInterface chatDataAccessObject) {

        final ChatController chatController = createChatUseCase(viewManagerModel, chatViewModel, listChatViewModel,
                chatDataAccessObject);
        return new ChatView(chatController, chatViewModel);

    }

    private static ChatController createChatUseCase(
            ViewManagerModel viewManagerModel,
            ChatViewModel chatViewModel,
            ListChatViewModel listChatViewModel,
            ChatDataAccessInterface chatDataAccessObject) {
        final ChatOutputBoundary chatPresenter = new ChatPresenter(viewManagerModel, chatViewModel, listChatViewModel);
        final ChatInputBoundary chatInteractor = new ChatInteractor(chatDataAccessObject, chatPresenter);
        return new ChatController(chatInteractor);
    }
}
