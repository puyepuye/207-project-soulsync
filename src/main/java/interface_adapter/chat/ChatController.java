package interface_adapter.chat;

import entity.ChatMessage;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import use_case.chat.ChatInputBoundary;
import use_case.chat.ChatInputData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatController {

    private final ChatInputBoundary chatUseCaseInteractor;

    public ChatController(ChatInputBoundary chatUseCaseInteractor) {
        this.chatUseCaseInteractor = chatUseCaseInteractor;
    }

    // helper method to convert dates from the post request
    private String convertMillisToDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = new Date(millis);
        return sdf.format(date);
    }
    @RestController
    class Controller {
        private final MessageEventManager eventManager = MessageEventManager.getInstance();
        private ChatMessage previousMessage = new ChatMessage(null, null, null);
        @PostMapping("/")
        public void receiveMessages(@RequestBody String body) throws JSONException {
            JSONObject json = new JSONObject(body);

            // Access the "payload" object
            JSONObject payload = json.getJSONObject("payload");

            // Retrieve "message" and "created_at" values
            String message = payload.getString("message");
            String createdAt = convertMillisToDate(payload.getLong("created_at"));
            String user = payload.getJSONObject("sender").getString("nickname");
            // Print the values
            System.out.println("Message: " + message);
            System.out.println("Created at: " + createdAt);
            System.out.println("Sent by: " + user);

            ChatMessage newMessage = new ChatMessage(user, message, createdAt);
            eventManager.setNewMessage(previousMessage, newMessage);
            previousMessage = newMessage;
        }
    }
    public void sendMessage(ChatInputData chatInputData) {
        chatUseCaseInteractor.sendMessage(chatInputData);
    }


    // TODO: add a button to switch to leave to all messages page

    public void switchToChatList() {
        chatUseCaseInteractor.switchToChatList();
    }




}
