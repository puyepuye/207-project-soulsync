package use_case.chat;

import data_access.ChatDataAccessObject;
import entity.ChatMessage;
import interface_adapter.chat.ChatPresenter;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ChatInteractorTest {

    private ChatDataAccessObject dao;
    private ChatInteractor interactor;
    private ChatPresenter presenter;
    private final ChatMessage message = new ChatMessage("bob", "Hi alice",
            "dd-MM-yyyy HH:mm", "bob_alice_chat");

    @Before
    public void setUp() {
        this.dao = mock(ChatDataAccessObject.class);
        this.presenter = mock(ChatPresenter.class);
        this.interactor = new ChatInteractor(dao, presenter);
    }


    @Test   // Checks that this will call the appropriate methods from the interface
    public void sendMessageTest() {
        ChatInputData inputData = new ChatInputData(message.getChatUrl(), message.getSender(), message);
        interactor.sendMessage(inputData);
        verify(dao, times(1)).sendMessage(inputData.getChatUrl(), inputData.getChatMessage());
    }


    @Test  //Checks that this will correctly return a list of all messages in a channel
    public void getChatMessagesTest() {
        String chatUrl = "bob_alice_chat";
        ArrayList<ChatMessage> expectedMessages = new ArrayList<>();
        expectedMessages.add(message);
        when(dao.getAllMessages(chatUrl)).thenReturn(expectedMessages);
        ArrayList<ChatMessage> actualMessages = (ArrayList<ChatMessage>) interactor.getAllMessages(chatUrl);
        assertEquals(expectedMessages, actualMessages);

    }

    @Test  // Checks that it calls the right view switch method.
    public void switchToChatListTest() {
        interactor.switchToChatList();
        verify(presenter, times(1)).switchToChatList();
    }
}
