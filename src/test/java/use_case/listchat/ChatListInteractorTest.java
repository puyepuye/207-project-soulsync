package use_case.listchat;

import data_access.ChatDataAccessObject;
import entity.ChatChannel;
import interface_adapter.listchat.ListChatPresenter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ChatListInteractorTest {
    private ChatDataAccessObject dao;
    private ListChatInteractor interactor;
    private ListChatPresenter presenter;
    private final String username = "bob";
    private final String chatUrl = "bob_alice_chat";

    @Before
    public void setUp() {
        this.dao = mock(ChatDataAccessObject.class);
        this.presenter = mock(ListChatPresenter.class);
        this.interactor = new ListChatInteractor(presenter, dao);
    }

    @Test // Test to make sure the interactor properly calls the data access object.
    public void getAllChatsTest() throws IOException, InterruptedException {

        ArrayList<ChatChannel> expectedValues = new ArrayList<>();
        expectedValues.add(
                new ChatChannel("bob_alice_chat", "Hi bob", "bob", "alice"));
        when(dao.getAllChats(username)).thenReturn(expectedValues);
        ArrayList<ChatChannel> actualValues = interactor.getAllChats(username);
        assertEquals(expectedValues, actualValues);
    }

    @Test
    public void enterChatTest() throws IOException, InterruptedException {
        interactor.enterChat(chatUrl);
        verify(presenter, times(1)).enterChat(chatUrl);
    }
}
