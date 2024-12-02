package interface_adapter.chat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public void receiveMessages(@RequestBody String body) throws InterruptedException {
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

    private void relayMessage(String requestBody) throws InterruptedException {
        try {
            final List<String> allLines = Files.readAllLines(Paths.get("src/main/resources/clientURLs.txt"));
            for (String url : allLines) {
                final HttpRequest postRequest = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json; charset=utf8")
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();
                System.out.println("sent message");
                final HttpClient client = HttpClient.newHttpClient();
                final HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
                System.out.println(response);
            }
        }
        catch (IOException error) {
            System.out.println("Could not find list of clients to relay message to.");
        }
    }

    private String convertMillisToDate(long millis) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        final Date date = new Date(millis);
        return sdf.format(date);
    }
}
