package interface_adapter.chat;

import java.util.List;

import entity.ChatMessage;
import use_case.chat.ChatInputBoundary;
import use_case.chat.ChatInputData;

/**
 * The controller class for the chat use case.
 */
public class ChatController {

    private final ChatInputBoundary chatUseCaseInteractor;

    public ChatController(ChatInputBoundary chatUseCaseInteractor) {
        this.chatUseCaseInteractor = chatUseCaseInteractor;
    }

    /**
     * Sends a message using the chat interactor.
     * @param chatInputData what the user typed into the chat.
      */
    public void sendMessage(ChatInputData chatInputData) {
        chatUseCaseInteractor.sendMessage(chatInputData);
    }

    /**
     * Method to switch back to the chat list screen.
     */
    public void switchToChatList() {
        chatUseCaseInteractor.switchToChatList();
    }

    /**
     * Method that returns all the messages in a channel, used on initial load.
     * @param chatUrl the url to the chat you want messages to.
     * @return a list of ChatMessage objects
     */
    public List<ChatMessage> getAllMessages(String chatUrl) {
        return chatUseCaseInteractor.getAllMessages(chatUrl);
    }

}

