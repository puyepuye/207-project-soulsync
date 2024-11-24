package interface_adapter.listchat;

import entity.ChatChannel;
import use_case.chat.ChatDataAccessInterface;
import use_case.listchat.ListChatDataAccessInterface;
import use_case.listchat.ListChatInputBoundary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListChatController {

    private final ListChatInputBoundary listChatInputInteractor;
    private final ListChatDataAccessInterface chatDataAccessObject;

    public ListChatController(ListChatInputBoundary listChatInputInteractor, ListChatDataAccessInterface chatDataAccessObject) {
        this.listChatInputInteractor = listChatInputInteractor;
        this.chatDataAccessObject = chatDataAccessObject;
    }

    public ArrayList<ChatChannel> getAllChats(String username) throws IOException, InterruptedException {
        return listChatInputInteractor.getAllChats(username);
    }
}
