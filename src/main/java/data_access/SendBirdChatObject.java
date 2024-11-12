package data_access;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SendBirdChatObject {
    private final String apiKey;
    private final String appID;
    public static final String USERID = "user_id";
    public static final String MESSAGE = "message";
    public static final String API_TOKEN = "Api-token";
    public SendBirdChatObject() {
        Dotenv dotenv = Dotenv.load();
        apiKey = dotenv.get("SEND_BIRD_API_KEY");
        appID = dotenv.get("SEND_BIRD_APP_ID");
    }

    /**
     * Creates a new user in SendBird's database
     */
    public void createSendBirdUser(String uniqueID, String username, String profilePicture) throws InterruptedException, IOException {
        JSONObject requestBody = new JSONObject();
        requestBody.put(USERID, uniqueID);
        requestBody.put("nickname", username);
        requestBody.put("profile_url", profilePicture);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://api-%s.sendbird.com/v3/users", appID)))
                .header(API_TOKEN, apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        JSONObject responseJSON = new JSONObject(response.body());
        if (responseJSON.has("error")) {
            System.out.println("Something went wrong, server threw an error");
        }

    }


    /**
     * @param userID1 user_id
     * @param userID2
     *
     * Creates a SendBird with url being "{user_id1}_{user_id2}_chat"
     */
    public void createSendBirdChat(String userID1, String userID2) throws InterruptedException, IOException {

        List<String> chatUsers = new ArrayList<>();
        chatUsers.add(userID1);
        chatUsers.add(userID2);
        JSONObject requestBody = new JSONObject();

        requestBody.put("name", userID1 + "_" + userID2 + "_chat"); // name of chat
        requestBody.put("channel_url", userID1 + "_" + userID2 + "_chat"); // url of chat
        requestBody.put("operator_ids", chatUsers); // the 2 users involved in a chat

        // build request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://api-%s.sendbird.com/v3/open_channels", appID)))
                .header(API_TOKEN, apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        JSONObject responseJSON = new JSONObject(response.body());
        if (responseJSON.has("error")) {
            System.out.println("Something went wrong, likely, the user doesn't exist");
        }

    }

    public void sendMessage(String channelURL, String senderID, String message) throws InterruptedException, IOException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("message_type", "MESG");
        requestBody.put(USERID, senderID);
        requestBody.put(MESSAGE, message);
        // build request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api-" + appID + ".sendbird.com/v3/open_channels/" + channelURL + "/messages"))
                .header(API_TOKEN, apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        JSONObject responseJSON = new JSONObject(response.body());
        if (responseJSON.has("error")) {
            System.out.println("A server side error occured");
        }

    }

    /**
     * @param channelURL The URL of the channel you're trying to send a message through to.
     * @return a JSON object containing a list of messages, its sender, time stamp, read-status
     */
    public List<JSONObject> getChatMessages(String channelURL) throws InterruptedException, IOException {
        // build request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api-" + appID + ".sendbird.com/v3/open_channels/" + channelURL
                        + "/messages?message_ts=" + System.currentTimeMillis()))
                .header(API_TOKEN, apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        JSONObject responseJSON = new JSONObject(response.body());

        if (responseJSON.has("error")) {
                System.out.println("Couldn't get the chat messages, URL is likely wrong");
                return new ArrayList<>();

        }

        // for each message
        return extractMessagesFromJSON(responseJSON);

    }

    public List<JSONObject> extractMessagesFromJSON(JSONObject inputJSON) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);
        JSONArray messages = inputJSON.getJSONArray("messages");
        List<JSONObject> result = new ArrayList<>();

        for (int i = 0; i < messages.length(); i++) {
            JSONObject messageSummary = new JSONObject();
            JSONObject message = messages.getJSONObject(i);
            messageSummary.put(MESSAGE, message.getString(MESSAGE));
            String timestamp = formatter.format(Instant.ofEpochMilli(message.getLong("created_at")));
            messageSummary.put("timestamp", timestamp);
            messageSummary.put(USERID, message.getJSONObject("user").getString(USERID));
            messageSummary.put("user_name", message.getJSONObject("user").getString("nickname"));
            result.add(messageSummary);

        }
        return result;
}

    public static void test() throws IOException, InterruptedException {
        SendBirdChatObject so = new SendBirdChatObject();
        String sampleObject = "{\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"type\": \"MESG\",\n" +
                "            \"message_id\": 99211369,\n" +
                "            \"message\": \"hi, this is a test message!\",\n" +
                "            \"data\": \"\",\n" +
                "            \"custom_type\": \"\",\n" +
                "            \"file\": {},\n" +
                "            \"created_at\": 1731172973394,\n" +
                "            \"user\": {\n" +
                "                \"user_id\": \"69\",\n" +
                "                \"profile_url\": \"https://i.scdn.co/image/ab67616d0000b273baf89eb11ec7c657805d2da0\",\n" +
                "                \"require_auth_for_profile_image\": false,\n" +
                "                \"nickname\": \"Mac\",\n" +
                "                \"metadata\": {},\n" +
                "                \"role\": \"operator\",\n" +
                "                \"is_active\": true\n" +
                "            },\n" +
                "            \"channel_url\": \"1234\",\n" +
                "            \"updated_at\": 0,\n" +
                "            \"message_survival_seconds\": -1,\n" +
                "            \"mentioned_users\": [],\n" +
                "            \"mention_type\": \"users\",\n" +
                "            \"silent\": false,\n" +
                "            \"message_retention_hour\": -1,\n" +
                "            \"channel_type\": \"open\",\n" +
                "            \"translations\": {},\n" +
                "            \"is_removed\": false,\n" +
                "            \"is_op_msg\": true,\n" +
                "            \"message_events\": {\n" +
                "                \"send_push_notification\": \"receivers\",\n" +
                "                \"update_unread_count\": true,\n" +
                "                \"update_mention_count\": true,\n" +
                "                \"update_last_message\": true\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        JSONObject sample_response = new JSONObject(sampleObject);
        List<JSONObject> jo = so.extractMessagesFromJSON(sample_response);
        System.out.println(jo.toString());
        try {
            so.sendMessage("42069_69_chat", "42069",
                    "Hello, this is mac sending a test message via Java");
            so.sendMessage("42069_69_chat", "69",
                    "Hello Yole");
        }
        catch (Exception e) {
            System.out.println("womp womp....");
        }
        System.out.println(so.getChatMessages("42069_69_chat"));
    }


//    public static void main(String[] args) throws IOException, InterruptedException {
//        SendBirdChatObject.test();
//    }
}


@SpringBootApplication
class app {
    public static void main(String[] args) {
        SpringApplication.run(app.class, args);
        System.out.println("Hello World");

    }
}


@RestController
class Controller {
    @PostMapping("/")
    public Message receiveMessages(@RequestBody String body) throws JSONException {
        JSONObject json = new JSONObject(body);

        // Access the "payload" object
        JSONObject payload = json.getJSONObject("payload");

        // Retrieve "message" and "created_at" values
        String message = payload.getString("message");
        long createdAt = payload.getLong("created_at");

        // Print the values
        System.out.println("Message: " + message);
        System.out.println("Created At: " + createdAt);
        return new Message(body);
    }
}
class Message {
    public String msg;
    public Message(String content) {
        this.msg = content;
    }
}




