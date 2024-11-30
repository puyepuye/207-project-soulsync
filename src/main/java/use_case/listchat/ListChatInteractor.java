package use_case.listchat;

import entity.ChatChannel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListChatInteractor implements ListChatInputBoundary {
    private final ListChatOutputBoundary listChatPresenter;
    private final ListChatDataAccessInterface listChatDataAccessObject;

    public ListChatInteractor(ListChatOutputBoundary listChatPresenter, ListChatDataAccessInterface listChatDataAccessObject) {
        this.listChatPresenter = listChatPresenter;
        this.listChatDataAccessObject = listChatDataAccessObject;
    }


    @Override
    public ArrayList<ChatChannel> getAllChats(String username) throws IOException, InterruptedException {
        return listChatDataAccessObject.getAllChats(username);
    }

    @Override
    public void enterChat(String chatURL) {
        listChatPresenter.enterChat(chatURL);
    }
}
