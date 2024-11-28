package use_case.chat;

import data_access.ChatDataAccessObject;
import entity.ChatMessage;
import interface_adapter.chat.ChatPresenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class ChatInteractorTest {

    private ChatDataAccessObject dao;
    private ChatInteractor interactor;
    private ChatPresenter presenter;
    private ChatMessage message = new ChatMessage("bob", "Hi alice",
            "dd-MM-yyyy HH:mm", "bob_alice_chat");

    @Before
    public void setUp() {
        this.dao = mock(ChatDataAccessObject.class);
        this.presenter = mock(ChatPresenter.class);
        this.interactor = new ChatInteractor(dao, presenter);
    }


    @Test   // Checks that this will call the appropriate methods from the interface
    public void sendMessageTest() {
        ChatInputData inputData = new ChatInputData(message.getChatURL(), message.getSender(), message);
        interactor.sendMessage(inputData);
        verify(dao, times(1)).sendMessage(inputData.getChatURL(), inputData.getChatMessage());
    }


    @Test  //Checks that this will correctly return a list of all messages in a channel
    public void getChatMessagesTest() {

    }

    @Test
    public void switchToChatListTest() {

    }
}
