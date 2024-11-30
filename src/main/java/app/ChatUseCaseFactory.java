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

public class ChatUseCaseFactory {

    // Prevent instantiation
    private ChatUseCaseFactory(){

    }

    public static ChatView create(
            ViewManagerModel viewManagerModel,
            ChatViewModel chatViewModel,
            ListChatViewModel listChatViewModel,
            ChatDataAccessInterface chatDataAccessObject){
        final ChatController chatController = createChatUseCase(viewManagerModel,
                chatViewModel, listChatViewModel, chatDataAccessObject);
        return new ChatView(chatController, chatViewModel);

    }

    private static ChatController createChatUseCase(
            ViewManagerModel viewManagerModel,
            ChatViewModel chatViewModel,
            ListChatViewModel listChatViewModel,
            ChatDataAccessInterface chatDataAccessObject){
        final ChatOutputBoundary chatPresenter = new ChatPresenter(viewManagerModel, chatViewModel, listChatViewModel);
        final ChatInputBoundary chatInteractor = new ChatInteractor(chatDataAccessObject, chatPresenter);
        return new ChatController(chatInteractor);
    }
}
