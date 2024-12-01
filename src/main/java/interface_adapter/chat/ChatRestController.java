package interface_adapter.chat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import entity.ChatMessage;

/**
 * Post request mapping for receiving messages.
 */
@RestController
class ChatRestController {
    private final MessageEventManager eventManager = MessageEventManager.getInstance();
    private ChatMessage previousMessage = new ChatMessage(null, null, null);

    @PostMapping("/")
    public void receiveMessages(@RequestBody String body) throws IOException, InterruptedException {
        final JSONObject json = new JSONObject(body);

        // Access the "payload" object
        final JSONObject payload = json.getJSONObject("payload");

        // Retrieve "message" and "created_at" values
        final String message = payload.getString("message");
        final String createdAt = convertMillisToDate(payload.getLong("created_at"));
        final String user = json.getJSONObject("sender").getString("user_id");
        final String channelUrl = json.getJSONObject("channel").getString("channel_url");
        // Print the values
        System.out.println("Message: " + message);
        System.out.println("Created at: " + createdAt);
        System.out.println("Sent by: " + user);

        final ChatMessage newMessage = new ChatMessage(user, message, createdAt, channelUrl);
        eventManager.setNewMessage(previousMessage, newMessage);
        previousMessage = newMessage;

        relayMessage(body);
    }

    private void relayMessage(String requestBody) throws IOException, InterruptedException {
        final HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://70a4-138-51-79-15.ngrok-free.app/"))    // TODO: add endpoint
                .header("Content-Type", "application/json; charset=utf8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        System.out.println("sent message");
        final HttpClient client = HttpClient.newHttpClient();
        final HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);
    }

    private String convertMillisToDate(long millis) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        final Date date = new Date(millis);
        return sdf.format(date);
    }
}
