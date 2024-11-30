package interface_adapter.chat;

import entity.ChatMessage;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import use_case.chat.ChatInputBoundary;
import use_case.chat.ChatInputData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatController {

    private final ChatInputBoundary chatUseCaseInteractor;

    public ChatController(ChatInputBoundary chatUseCaseInteractor) {
        this.chatUseCaseInteractor = chatUseCaseInteractor;
    }

    // helper method to convert dates from the post request

    public void sendMessage(ChatInputData chatInputData) {
        chatUseCaseInteractor.sendMessage(chatInputData);
    }



    public void switchToChatList() {
        chatUseCaseInteractor.switchToChatList();
    }

    public List<ChatMessage> getAllMessages(String chatURL) {
        return chatUseCaseInteractor.getAllMessages(chatURL);
    }



}

