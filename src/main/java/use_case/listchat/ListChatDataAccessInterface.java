package use_case.listchat;

import entity.ChatChannel;
import entity.ChatMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ListChatDataAccessInterface {
    public ArrayList<ChatChannel> getAllChats(String username) throws IOException, InterruptedException;
}
