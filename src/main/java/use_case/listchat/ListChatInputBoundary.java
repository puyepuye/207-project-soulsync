package use_case.listchat;

import entity.ChatChannel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ListChatInputBoundary {

    /**
     * Based on which chat is clicked on, enter the correct chat screen
     * @param chatURL the URL of the chat that the user has clicked on.
     */
    void enterChat(String chatURL);
    ArrayList<ChatChannel> getAllChats(String username) throws IOException, InterruptedException;
}
